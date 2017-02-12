package org.jfl110.prender.api.resources;

/**
 * Interface for resources which can be transformed.
 * to InputStreams by the corresponding ResourceSourceService.
 *
 * @author JFL110
 */
public interface ResourceSource {
	
	/**
	 * The originating path of the resource.
	 */
	String getPath();
}