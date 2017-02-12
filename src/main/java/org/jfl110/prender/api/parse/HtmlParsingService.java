package org.jfl110.prender.api.parse;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.Resource;

/**
 * Service to parse HTML.
 *
 * @author JFL110
 */
public interface HtmlParsingService {

	RenderTag parse(Resource page, ServletContext servletContext) throws IOException;
	
	RenderTag parse(Resource page,ServletContext servletContext,HtmlParseOptions options) throws IOException;
}