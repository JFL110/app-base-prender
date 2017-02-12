package org.jfl110.prender.impl.parse;

import static org.jfl110.prender.impl.parse.RenderTags.findFirstAttribute;
import static org.jfl110.prender.impl.parse.RenderTags.isAttribute;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.PlaceholderRenderNode;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.parse.ParseTransformation;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;

import com.google.common.base.Optional;

class DefaultPlaceholderParseTransformation implements ParseTransformation{
	
	private static final String TAG_NAME = "x:placeholder";
	private static final String KEY_ATTRIBUTE = "key";
	private static final String PLACEHOLDER_KEY_ATTRIBUTE_ONLY = "data-placeholder-key";
	
	@Override
	public Optional<SerializableRenderNode> transform(RenderTag input, ServletContext servletContext) throws IOException {

		Optional<RenderAttribute> keyOnlyAttribute = findFirstAttribute(input, isAttribute(PLACEHOLDER_KEY_ATTRIBUTE_ONLY));
		
		if(keyOnlyAttribute.isPresent() && keyOnlyAttribute.get().value() != null){
			return Optional.<SerializableRenderNode>of(PlaceholderRenderNode.placeholder(keyOnlyAttribute.get().value()));
		}
		
		if(input.tagName() == null){
			return Optional.absent();
		}
		
		if(!TAG_NAME.equals(input.tagName().toLowerCase())){
			return Optional.absent();
		}
		
		Optional<RenderAttribute> keyAttribute = findFirstAttribute(input, isAttribute(KEY_ATTRIBUTE));
		
		if(!keyAttribute.isPresent()){
			return Optional.absent();
		}
				
		String key = keyAttribute.get().value();
		
		if(key == null){
			return Optional.absent();
		}
				
		return Optional.<SerializableRenderNode>of(PlaceholderRenderNode.placeholder(key));
	}

}