package org.jfl110.prender.impl.parse;

import static com.google.common.collect.Iterables.any;
import static org.jfl110.prender.impl.parse.RenderTags.isAttribute;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.StringRenderNodes;
import org.jfl110.prender.api.parse.CssCompressor;
import org.jfl110.prender.api.parse.ParseTransformation;
import org.jfl110.prender.api.parse.RenderTag;
import org.jfl110.prender.api.render.RenderNodeSpace;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

class CssCompressionParseTransformation implements ParseTransformation{
	
	private static final int MAX_CHARS_PER_LINE = 1000;
	private static final String CSS_TAG_NAME = "style";
	private static final String HREF_ATTRIBUTE_NAME = "href";
	static final String COMPRESSED_MARKER = "/*C*/";

	private final Provider<CssCompressor> cssCompressor;
	
	@Inject
	CssCompressionParseTransformation(Provider<CssCompressor> cssCompressor){
		this.cssCompressor = cssCompressor;
	}
	
	private Optional<RenderStream> getContent(RenderTag input) {
		if(input.tagName()==null 
				|| !CSS_TAG_NAME.equals(input.tagName().toLowerCase())
				|| any(input.attributes(), isAttribute(HREF_ATTRIBUTE_NAME))
				|| input.children().size() != 1){
			return Optional.absent();
		}
		
		RenderNodeSpace onlyChild = Iterables.getFirst(input.children(), null);
		if(onlyChild == null){
			return Optional.absent();
		}
		
		RenderNode renderNode = onlyChild.get();
		if(!(renderNode instanceof RenderStream)){
			return Optional.absent();
		}
		
		return Optional.of((RenderStream) renderNode);
	}
	
	@Override
	public Optional<SerializableRenderNode> transform(RenderTag input, ServletContext servletContext) throws IOException {
		
		Optional<RenderStream> content = getContent(input);
		
		if(!content.isPresent()){
			return Optional.absent();
		}
		
		StringWriter unCompressedCssWriter = new StringWriter();
		content.get().write(unCompressedCssWriter);
		
		String uncompressedCss = unCompressedCssWriter.toString();
		if(uncompressedCss.startsWith(COMPRESSED_MARKER)){
			return Optional.absent();
		}
		
		StringWriter compressedCss = new StringWriter();
		compressedCss.write(COMPRESSED_MARKER);
		cssCompressor.get().compress(compressedCss,uncompressedCss, MAX_CHARS_PER_LINE);
		
		return Optional.<SerializableRenderNode>of(
				RenderTags.tag(CSS_TAG_NAME)
				.addAttributes(RenderTags.copyOfAttributes(input.attributes()))
				.addChild(StringRenderNodes.string(compressedCss.toString()))
				.build());
	}
}