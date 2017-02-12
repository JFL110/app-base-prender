package org.jfl110.prender.api.serve;

import javax.servlet.Filter;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;

import com.google.inject.Module;

public interface ServeBuilder<T extends ServeBuilder<?>> extends Module  {

	T with(RenderNode renderNode);
	
	
	T with(RenderMap renderMap);
	
	
	T withLocalPage(String pagePath);
	
	
	T through(Class<? extends Filter> filterClazz);
	
}