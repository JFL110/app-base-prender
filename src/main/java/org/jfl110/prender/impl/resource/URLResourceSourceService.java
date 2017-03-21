package org.jfl110.prender.impl.resource;

import static org.jfl110.prender.api.resources.Resource.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.Resource;
import org.jfl110.prender.api.resources.ResourceSourceService;
import org.jfl110.prender.api.resources.URLResourceSource;

class URLResourceSourceService implements ResourceSourceService<URLResourceSource>{

	@Override
	public Class<URLResourceSource> getResourceSourceType() {
		return URLResourceSource.class;
	}

	
	@Override
	public Resource toInputStream(URLResourceSource resourceSource,ServletContext servletContext) {
		InputStream inputStream;
		try {
			inputStream = resourceSource.getUrl().openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String path = resourceSource.getPath();
		return resource(path, inputStream);
	}
}