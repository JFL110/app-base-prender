package org.jfl110.prender.api;

import java.util.Collection;

import org.jfl110.prender.api.render.RenderNodeSpace;

/**
 * A RenderNode with child nodes
 *
 * @author JFL110
 */
public interface RenderNodeWithChildren extends RenderNode {

	/**
	 * Gets an unmodifiable collection of the child nodes of this node
	 */
	Collection<RenderNodeSpace> children();
}
