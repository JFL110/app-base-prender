package org.jfl110.prender.impl.resource;

import static org.jfl110.prender.api.resources.Resource.resource;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.Resource;
import org.jfl110.prender.api.resources.ResourceSourceService;
import org.jfl110.prender.api.resources.ServletContextResourceSource;

class ServletContextResourceSourceService implements ResourceSourceService<ServletContextResourceSource>{

	@Override
	public Class<ServletContextResourceSource> getResourceSourceType() {
		return ServletContextResourceSource.class;
	}

	
	@Override
	public Resource toInputStream(ServletContextResourceSource resourceSource,ServletContext servletContext) {
		InputStream inputStream = servletContext.getResourceAsStream(resourceSource.getPath());
		String path = resourceSource.getPath();
		return resource(path, inputStream);
	}
}