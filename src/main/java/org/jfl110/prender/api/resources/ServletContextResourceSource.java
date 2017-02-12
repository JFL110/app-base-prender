package org.jfl110.prender.api.resources;

/**
 * A ResourceSource that can be resolved from the ServletContext
 *
 * @author JFL110
 */
public class ServletContextResourceSource implements ResourceSource {

	private final String path;
	
	ServletContextResourceSource(String path){
		this.path = path;
	}
	
	@Override
	public String getPath() {
		return path;
	}
}
