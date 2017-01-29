package org.jfl110.prender.api.render;

import javax.servlet.ServletRequest;

import com.google.common.base.Optional;

/**
 * Handles getting/setting of RenderMap on the request.
 * 
 * @author JFL110
 */
public interface RenderMapService {

	/**
	 * Get the RenderMap from the request
	 */
	Optional<RenderMap> get(ServletRequest request);

	/**
	 * Attach the RenderMap to the request
	 */
	void put(RenderMap renderMap, ServletRequest request);
}