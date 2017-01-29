package org.jfl110.prender.impl.parse;

import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.find;
import static org.jfl110.prender.impl.StringRenderNode.string;
import static org.jfl110.prender.impl.parse.RenderTags.copyOfAttributes;
import static org.jfl110.prender.impl.parse.RenderTags.isAttribute;

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

class JSInlineParseTransformation implements ParseTransformation {

	private static Logger logger = Logger.getLogger(JSInlineParseTransformation.class.getSimpleName());

	private static final String JS_SCRIPT_TAG_NAME = "script";
	private static final String SRC_ATTRIBUTE_NAME = "src";
	private static final String INLINE_ATTRIBUTE_NAME = "inline";

	private static final String CACHE_NAME = "JS_INLINE";

	private static final Predicate<RenderAttribute> ATTRIBUTES_TO_EXLUDE = new Predicate<RenderAttribute>() {
		@Override
		public boolean apply(RenderAttribute attribute) {
			return !(INLINE_ATTRIBUTE_NAME.equals(attribute.name().toLowerCase())
					|| SRC_ATTRIBUTE_NAME.equals(attribute.name().toLowerCase()));
		}
	};

	private final Provider<CacheService> cacheService;
	private final Provider<StreamToStringService> streamToStringService;

	@Inject
	JSInlineParseTransformation(Provider<CacheService> cacheService,
			Provider<StreamToStringService> streamToStringService) {
		this.cacheService = cacheService;
		this.streamToStringService = streamToStringService;
	}

	private boolean isInlineScriptTag(RenderTag input) {
		return input.tagName() != null && JS_SCRIPT_TAG_NAME.equals(input.tagName().toLowerCase())
				&& input.children().size() == 0 && any(input.attributes(), isAttribute(INLINE_ATTRIBUTE_NAME));
	}

	@Override
	public Optional<SerializableRenderNode> transform(RenderTag input,final ServletContext servletContext) {
		
		if(!isInlineScriptTag(input)){
			return Optional.absent();
		}
		
		final RenderAttribute src = find(input.attributes(), isAttribute(SRC_ATTRIBUTE_NAME), null);
		
		if(src == null){
			return Optional.absent();
		}

		String js = cacheService.get().get(CACHE_NAME, src.value(), new Callable<String>() {
			@Override
			public String call() throws Exception {
				return getJavascript(src.value(), servletContext);
			}
		});
		
		if(js == null){
			return Optional.absent();
		}
	
		return Optional.<SerializableRenderNode>of(
				RenderTags
				.tag("script")
				.addChild(string(js))
				.addAttributes(
					Iterables.filter(
							copyOfAttributes(input.attributes()),ATTRIBUTES_TO_EXLUDE)).build());
	}

	
	private String getJavascript(String path, ServletContext servletContext) {
		InputStream inputStream = null;
		try {
			inputStream = servletContext.getResourceAsStream(path);
		} catch (Exception e) {
			logger.info("Error getting stream [" + path + "]" + e);
			return null;
		}

		if (inputStream == null) {
			logger.info("Null input stream for [" + path + "]");
			return null;
		}

		String js = null;
		try {
			js = streamToStringService.get().convertStreamToString(inputStream);
		} catch (Exception e) {
			logger.info("Error reading stream [" + path + "] " + e);
			return null;
		}
		return js;
	}
}