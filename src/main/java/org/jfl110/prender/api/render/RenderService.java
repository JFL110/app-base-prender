package org.jfl110.prender.api.render;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;

/**
 * Renders a specific type of RenderNode, packaging the output
 * as a collection of RenderResults.
 * 
 * @author JFL110
 */
public interface RenderService<T extends RenderNode> {
	
	Class<T> getRenderNodeType();

	Collection<RenderNode> render(T node, HttpServletRequest requestData,ServletContext context) throws IOException;
}