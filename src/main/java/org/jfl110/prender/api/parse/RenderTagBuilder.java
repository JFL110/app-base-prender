package org.jfl110.prender.api.parse;

import java.util.Collection;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderNodeSpace;

/**
 * Builds RenderTags.
 *
 * @author JFL110
 */
public interface RenderTagBuilder {

	RenderTagBuilder addChild(RenderNodeSpace child);

	RenderTagBuilder addChildSpaces(Collection<RenderNodeSpace> children);
	
	RenderTagBuilder addChild(RenderNode child);

	RenderTagBuilder addChildren(Iterable<RenderNode> children);

	RenderTagBuilder addAttribute(RenderAttribute attribute);
	
	RenderTagBuilder addAttribute(String key, String value);
	
	RenderTagBuilder addAttribute(String key);

	RenderTagBuilder addAttributes(Iterable<RenderAttribute> attributes) ;

	RenderTagBuilder setTagName(String tagName);
	
	RenderTag build();
}