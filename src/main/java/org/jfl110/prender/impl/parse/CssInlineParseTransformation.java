package org.jfl110.prender.impl.parse;

import static com.google.common.collect.Iterables.find;
import static org.jfl110.prender.impl.StringRenderNode.string;
import static org.jfl110.prender.impl.parse.RenderTags.copyOfAttributes;
import static org.jfl110.prender.impl.parse.RenderTags.isAttribute;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.cache.CacheService;
import org.jfl110.prender.api.parse.ParseTransformation;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

class CssInlineParseTransformation implements ParseTransformation{
	
	private static Logger logger = Logger.getLogger(CssInlineParseTransformation.class.getSimpleName());

	private static final String CSS_LINK_TAG_NAME = "link";
	private static final String INLINE_ATTRIBUTE_NAME = "inline";
	private static final String REL_ATTRIBUTE_NAME = "rel";
	private static final String HREF_ATTRIBUTE_NAME = "href";
	private static final String EXPECTED_REL_VALUE = "stylesheet";

	private static final String CACHE_NAME="CSS_INLINE";
	
	private static final Predicate<RenderAttribute> ATTRIBUTES_TO_EXLUDE = new Predicate<RenderAttribute>(){
		@Override
		public boolean apply(RenderAttribute attribute) {
			return !(INLINE_ATTRIBUTE_NAME.equals(attribute.name().toLowerCase())
					|| HREF_ATTRIBUTE_NAME.equals(attribute.name().toLowerCase())
					|| REL_ATTRIBUTE_NAME.equals(attribute.name().toLowerCase()));
		}
	};
	
	private final Provider<CacheService> cacheService;
	private final Provider<StreamToStringService> streamToStringService;
	
	@Inject
	CssInlineParseTransformation(Provider<CacheService> cacheService,
			Provider<StreamToStringService> streamToStringService){
		this.cacheService = cacheService;
		this.streamToStringService = streamToStringService;
	}

	private boolean isLinkTag(RenderTag input) {
		return input.tagName()!=null 
				&& CSS_LINK_TAG_NAME.equals(input.tagName().toLowerCase())
				&& input.children().size() == 0;
	}

	@Override
	public Optional<SerializableRenderNode> transform(RenderTag input,final ServletContext servletContext) {
		
		if(!isLinkTag(input)){
			return Optional.absent();
		}
		
		RenderAttribute rel = find(input.attributes(), isAttribute(REL_ATTRIBUTE_NAME), null);
		final RenderAttribute href = find(input.attributes(), isAttribute(HREF_ATTRIBUTE_NAME), null);
		
		if(!validateLinkTag(rel,href)){
			return Optional.absent();
		}
		
		String css = cacheService.get().get(CACHE_NAME, href.value(), new Callable<String>() {
			@Override
			public String call() throws Exception {
				return computeCss(href.value(),servletContext);
			}
		});
		
		if(css == null){
			return Optional.absent();
		}
		
		return Optional.<SerializableRenderNode>of(
				RenderTags
				.tag("style")
				.addAttributes(
					Iterables.filter(
							copyOfAttributes(input.attributes()),ATTRIBUTES_TO_EXLUDE))
				.addChild(string(css)).build());
	}
	
	private String computeCss(String path, ServletContext servletContext){
		
		InputStream inputStream=null;
		try{
			inputStream = servletContext.getResourceAsStream(path);
		}catch(Exception e){
			logger.info("Error getting stream ["+path+"]"+e);
			return null;
		}
		
		if(inputStream == null){
			logger.info("Null input stream for ["+path+"]");
			return null;
		}
		
		try {
			return streamToStringService.get().convertStreamToString(inputStream);
		} catch (IOException e) {
			logger.info("Error reading stream ["+path+"]"+e);
			return null;
		}
	}
	
	private boolean validateLinkTag(RenderAttribute rel, RenderAttribute href){
		return !(rel == null 
				|| rel.value() == null 
				|| !EXPECTED_REL_VALUE.equals(rel.value().toLowerCase())
				|| href == null
				|| href.value() == null);
	}
}