package org.jfl110.prender.impl.render;

import javax.servlet.ServletRequest;

import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMapService;

import com.google.common.base.Optional;

class DefaultRenderMapService implements RenderMapService {
	
	private final static String KEY = "RenderMapKey";

	@Override
	public Optional<RenderMap> get(ServletRequest request) {
		
		Object obj = request.getAttribute(KEY);
		
		if(obj == null){
			return Optional.absent();
		}
		
		if(!(obj instanceof RenderMap)){
			throw new RuntimeException("Non-RenderMap was bound. Type of ["+obj.getClass()+"] was bound instead.");
		}
		
		return Optional.of((RenderMap) obj);
	}

	
	@Override
	public void put(RenderMap renderMap, ServletRequest request) {
		if(renderMap == null){
			throw new RuntimeException("Cannot bind null RenderMap.");
		}
		
		request.setAttribute(KEY, renderMap);
	}

}
