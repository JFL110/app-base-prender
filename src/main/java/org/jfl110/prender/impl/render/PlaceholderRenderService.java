package org.jfl110.prender.impl.render;


import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.PlaceholderRenderNode;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderService;

import com.google.common.base.Optional;

public class PlaceholderRenderService implements RenderService<PlaceholderRenderNode>{

	@Override
	public Class<PlaceholderRenderNode> getRenderNodeType() {
		return PlaceholderRenderNode.class;
	}

	@Override
	public Collection<RenderNode> render(PlaceholderRenderNode placeholderNode,RenderMap renderMap,  HttpServletRequest requestData,ServletContext context) throws IOException {
		Optional<RenderNode> replaceValue = renderMap.placeHolderValue(placeholderNode.key());
		return replaceValue.isPresent() ? Collections.singleton(replaceValue.get()) : Collections.<RenderNode>emptyList();
	}
}