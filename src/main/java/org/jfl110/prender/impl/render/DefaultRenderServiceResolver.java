package org.jfl110.prender.impl.render;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.render.RenderServiceResolver;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

class DefaultRenderServiceResolver implements RenderServiceResolver {

	private final Set<? extends RenderService<? extends RenderNode>> renderServices;
	private final Map<Class<? extends RenderNode>, RenderService<? extends RenderNode>> serviceMap = Maps.newConcurrentMap();

	@Inject
	DefaultRenderServiceResolver(Set<RenderService<? extends RenderNode>> renderServices) {
		this.renderServices = renderServices;
		buildMap(); 
	}

	private void buildMap() {
		for (RenderService<? extends RenderNode> service : renderServices) {
			serviceMap.put(service.getRenderNodeType(), service);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends RenderNode> RenderService<T> get(Class<?> renderNodeType) {

		if (renderNodeType == null) {
			throw new UnsupportedOperationException("Cannot get render service for null node type!");
		}

		RenderService<? extends RenderNode> service = serviceMap.get(renderNodeType);

		if (service == null) {
			throw new UnsupportedOperationException("Could not find service for type [" + renderNodeType + "]");
		}
		try {
			return (RenderService<T>) service;
		} catch (ClassCastException e) {
			throw new RuntimeException("Mapping exception!", e);
		}
	}

	@Override
	public <T extends RenderNode> Collection<RenderNode> render(T node, RenderMap renderMap,HttpServletRequest requestData,ServletContext context) throws IOException {
		// TODO stackoverflow protection
		return get(node.getClass()).render(node, renderMap,requestData,context);
	}
}
