package org.jfl110.prender.api.parse;

import java.util.Collection;

import org.jfl110.prender.api.RenderNodeWithChildren;
import org.jfl110.prender.api.SerializableRenderNode;

/**
 * A HTML tag.
 *
 * @author JFL110
 */
public interface RenderTag extends RenderNodeWithChildren, SerializableRenderNode{

	String tagName();

	/**
	 * Gets an unmodifiable collection of the attributes of this node
	 */
	Collection<RenderAttribute> attributes();
}