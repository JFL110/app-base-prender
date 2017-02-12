package org.jfl110.prender.api.resources;

import java.io.InputStream;

/**
 * Tuple of an InputStream and a path.
 *
 * @author JFL110
 */
public class Resource {

	private final String path;
	private final InputStream inputStream;
	
	public static Resource resource(String path,InputStream inputStream){
		return new Resource(path,inputStream);
	}
	
	private Resource(String path,InputStream inputStream){
		this.path = path;
		this.inputStream = inputStream;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public String getPath() {
		return path;
	}
}