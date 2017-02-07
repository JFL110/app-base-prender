package org.jfl110.prender.api.parse;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.InputStreamWithPath;

/**
 * Service to parse HTML.
 *
 * @author JFL110
 */
public interface HtmlParsingService {

	RenderTag parse(InputStreamWithPath page, ServletContext servletContext) throws IOException;
	
	RenderTag parse(InputStreamWithPath page,ServletContext servletContext,HtmlParseOptions options) throws IOException;
}