package org.jfl110.prender.api.resources;

/**
 * Container class for methods relating to ServletContextResourceSource.
 *
 * @author JFL110
 */
public class ServletContextResourceSources {

	/**
	 * Creates a new ServletContextResourceSource.
	 * 
	 * @param path the path to the resource.
	 */
	public static ServletContextResourceSource servletContextResource(String path){
		return new ServletContextResourceSource(path);
	}
}