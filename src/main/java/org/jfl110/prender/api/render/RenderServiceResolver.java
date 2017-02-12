package org.jfl110.prender.api.render;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;

/**
 * Looks up RenderServices that can render types of
 * RenderNodes.
 * 
 * @author JFL110
 */
public interface RenderServiceResolver {
	
	/**
	 * Gets the render service for the type of node 
	 */
	<T extends RenderNode> RenderService<T> get(Class<?> node);
	
	
	/**
	 * Convenience method to call render on the render service
	 */
	<T extends RenderNode> Collection<RenderNode> render(T node, RenderMap renderMap, HttpServletRequest requestData,ServletContext context) throws IOException;
}