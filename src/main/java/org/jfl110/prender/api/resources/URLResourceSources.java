package org.jfl110.prender.api.resources;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Container class for methods relating to URLResourceSources.
 *
 * @author JFL110
 */
public class URLResourceSources {

	/**
	 * Creates a new URLResourceSources.
	 * 
	 * @param path the path to the resource.
	 * @throws MalformedURLException 
	 */
	public static URLResourceSource urlResource(String path) throws MalformedURLException {
		return new URLResourceSource(path);
	}
	
	
	/**
	 * Creates a new URLResourceSources.
	 * 
	 * @param the URL of the resource.
	 * @throws MalformedURLException 
	 */
	public static URLResourceSource urlResource(URL url){
		return new URLResourceSource(url);
	}
}