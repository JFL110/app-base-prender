package org.jfl110.prender.api.resources;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A ResourceSource that can be resolved from a URL
 *
 * @author JFL110
 */
public class URLResourceSource implements ResourceSource {

	private final URL url;
	
	URLResourceSource(String path) throws MalformedURLException{
		this.url = new URL(path);
	}
	
	URLResourceSource(URL url){
		this.url = url;
	}
	
	@Override
	public String getPath() {
		return url.getPath();
	}
	
	
	public URL getUrl() {
		return url;
	}
}
