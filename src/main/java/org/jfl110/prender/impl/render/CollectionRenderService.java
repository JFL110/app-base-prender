package org.jfl110.prender.impl.render;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderNodeSpace;
import org.jfl110.prender.api.render.RenderService;

import com.google.common.collect.Lists;

class CollectionRenderService implements RenderService<CollectionRenderNode> {

	@Override
	public Class<CollectionRenderNode> getRenderNodeType() {
		return CollectionRenderNode.class;
	}

	@Override
	public Collection<RenderNode> render(CollectionRenderNode node, HttpServletRequest requestData,ServletContext context) {
		return Lists.transform(Lists.newArrayList(node.children()), RenderNodeSpace.getNodes());
	}
}
