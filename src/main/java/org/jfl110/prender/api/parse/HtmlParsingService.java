package org.jfl110.prender.api.parse;

import java.io.IOException;

import javax.servlet.ServletContext;

/**
 * Service to parse HTML.
 *
 * @author JFL110
 */
public interface HtmlParsingService {

	RenderTag parse(String path, ServletContext servletContext) throws IOException;
	
	RenderTag parse(String path,ServletContext servletContext,HtmlParseOptions options) throws IOException;
}