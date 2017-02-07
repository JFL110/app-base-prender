package org.jfl110.prender.api.resources;

import java.io.InputStream;

/**
 * Tuple of an InputStream and a path.
 *
 * @author JFL110
 */
public class InputStreamWithPath {

	private final String path;
	private final InputStream inputStream;
	
	public static InputStreamWithPath inputStreamWithPath(String path,InputStream inputStream){
		return new InputStreamWithPath(path,inputStream);
	}
	
	private InputStreamWithPath(String path,InputStream inputStream){
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