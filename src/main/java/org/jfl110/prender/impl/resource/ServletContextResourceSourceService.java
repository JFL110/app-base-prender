package org.jfl110.prender.impl.resource;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.ResourceSourceService;
import org.jfl110.prender.api.resources.ServletContextResourceSource;

class ServletContextResourceSourceService implements ResourceSourceService<ServletContextResourceSource>{

	@Override
	public Class<ServletContextResourceSource> getResourceSourceType() {
		return ServletContextResourceSource.class;
	}

	@Override
	public InputStream toInputStream(ServletContextResourceSource resourceSource,ServletContext servletContext) {
		return servletContext.getResourceAsStream(resourceSource.getPath());
	}
}