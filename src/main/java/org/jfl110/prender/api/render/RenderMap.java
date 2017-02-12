package org.jfl110.prender.api.render;

import java.util.Map;

import org.jfl110.prender.api.RenderNode;

import com.google.common.base.Optional;

/**
 * Container for a RenderNode tree
 * 
 * @author JFL110
 */
public interface RenderMap {
	
	Map<String,RenderNode> placeholderValues();
	
	Optional<RenderNode> placeHolderValue(String key);
	
	void putPlaceholderValue(String key,RenderNode renderNode);
	
	RenderNode rootNode();
}
