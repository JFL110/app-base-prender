package org.jfl110.prender.api;

import static org.jfl110.prender.api.render.RenderNodeSpace.renderNodeSpace;

import java.util.Collection;
import java.util.List;

import org.jfl110.prender.api.render.RenderNodeSpace;

import com.google.common.collect.Lists;

public final class CollectionRenderNode implements RenderNodeWithChildren {

	private final List<RenderNodeSpace> children = Lists.newArrayList();

	private CollectionRenderNode() {
	}

	public static CollectionRenderNode collection() {
		return new CollectionRenderNode();
	}

	public CollectionRenderNode add(RenderNodeSpace child) {
		children.add(child);
		return this;
	}

	public CollectionRenderNode addSpaces(Collection<RenderNodeSpace> children) {
		this.children.addAll(children);
		return this;
	}
	
	public CollectionRenderNode add(RenderNode child) {
		children.add(renderNodeSpace(child));
		return this;
	}

	public CollectionRenderNode add(Collection<RenderNode> children) {
		for(RenderNode renderNode : children){
			this.children.add(renderNodeSpace(renderNode));
		}
		return this;
	}

	@Override
	public Collection<RenderNodeSpace> children() {
		return children;
	}
}
