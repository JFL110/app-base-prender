package org.jfl110.prender.api.serve;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;

import com.google.inject.Module;

/**
 * Builds a Serving
 * 
 * Note that filtering order may not be preserved if a call to filter("...") is used in the same 
 * module as the Serving is installed. See https://github.com/google/guice/issues/426
 *
 *
 * @author JFL110
 */
public interface ServeBuilder<T extends ServeBuilder<?>> extends Module  {
	
	T with(Class<? extends HttpServlet> servletClazz);
	
	
	T with(RenderNode renderNode);
	
	
	T with(RenderMap renderMap);
	
	
	T withLocalPage(String pagePath);
	
	
	T through(Class<? extends Filter> filterClazz);
	
}