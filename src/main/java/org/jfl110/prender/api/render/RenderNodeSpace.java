package org.jfl110.prender.api.render;

import org.jfl110.prender.api.RenderNode;

import com.google.common.base.Function;

/**
 * Space for a RenderNode to allow child swapping
 * 
 * @author JFL110
 */
public class RenderNodeSpace{

	private RenderNode node;
	
	public static RenderNodeSpace renderNodeSpace(RenderNode node){
		return new RenderNodeSpace(node);
	}
	
	private RenderNodeSpace(RenderNode node){
		this.node = node;
	}
	
	public RenderNode get() {
		return node;
	}

	public void set(RenderNode node) {
		this.node = node;
	}
	
	
	public static Function<RenderNodeSpace,RenderNode> getNodes(){
		return new Function<RenderNodeSpace,RenderNode>(){
			@Override
			public RenderNode apply(RenderNodeSpace space) {
				return space.get();
			}
		};
	}
}