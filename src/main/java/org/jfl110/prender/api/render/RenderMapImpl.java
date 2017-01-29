package org.jfl110.prender.api.render;

import org.jfl110.prender.api.RenderNode;

/**
 * Simple implementation of RenderMap
 * 
 * @author JFL110
 */
class RenderMapImpl implements RenderMap {

	private final RenderNode rootNode;

	RenderMapImpl(RenderNode rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public RenderNode rootNode() {
		return rootNode;
	}
}
