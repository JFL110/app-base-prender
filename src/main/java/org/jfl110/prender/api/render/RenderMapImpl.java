package org.jfl110.prender.api.render;

import java.util.Map;

import org.jfl110.prender.api.RenderNode;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

/**
 * Simple implementation of RenderMap
 * 
 * @author JFL110
 */
class RenderMapImpl implements RenderMap {

	private final RenderNode rootNode;
	private final Map<String, RenderNode> placeholderMap = Maps.newConcurrentMap();

	RenderMapImpl(RenderNode rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public RenderNode rootNode() {
		return rootNode;
	}

	@Override
	public Map<String, RenderNode> placeholderValues() {
		return placeholderMap;
	}

	@Override
	public Optional<RenderNode> placeHolderValue(String key) {
		return Optional.fromNullable(placeholderMap.get(key));
	}

	@Override
	public void putPlaceholderValue(String key, RenderNode renderNode) {
		placeholderMap.put(key, renderNode);
	}
}
