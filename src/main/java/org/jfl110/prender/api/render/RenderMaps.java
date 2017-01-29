package org.jfl110.prender.api.render;

import org.jfl110.prender.api.RenderNode;

/**
 * Static method container for utility
 * methods to create RenderMaps.
 * 
 * @author JFL110
 */
public class RenderMaps {

	public static RenderMap renderMap(RenderNode rootNode){
		return new RenderMapImpl(rootNode);
	}
}
