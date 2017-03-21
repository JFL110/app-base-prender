package org.jfl110.prender.impl.render;

import static com.google.inject.util.Providers.of;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.HtmlPageRenderNode;
import org.jfl110.prender.api.parse.HtmlParseOptions;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.resources.Resource;
import org.jfl110.prender.api.resources.ResourceSourceServiceResolver;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Tests HtmlPageRenderService
 *
 * @author JFL110
 */
public class TestHtmlPageRenderService {

	private final HtmlParsingService htmlParsingService = mock(HtmlParsingService.class);
	private final ResourceSourceServiceResolver resourceSourceServiceResolver = mock(ResourceSourceServiceResolver.class);
	private final HtmlPageRenderService service = new HtmlPageRenderService(of(htmlParsingService), of(resourceSourceServiceResolver));

	private final RenderMap renderMap = mock(RenderMap.class);
	private final ServletContext servletContext = mock(ServletContext.class);
	private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
	private final Resource resource = mock(Resource.class);
	private final ArgumentCaptor<HtmlParseOptions> htmlParseOptions = ArgumentCaptor.forClass(HtmlParseOptions.class);
	
	@Test
	public void testFullPage() throws Exception{
		HtmlPageRenderNode page = HtmlPageRenderNode.localHtmlPage("/page.html");
		when(resourceSourceServiceResolver.toInputStream(page.getPage(), servletContext)).thenReturn(resource);
		
		service.render(page, renderMap, httpServletRequest, servletContext);
		
		verify(htmlParsingService).parse(eq(resource), eq(servletContext),htmlParseOptions.capture());
		assertFalse(htmlParseOptions.getValue().isBodyOnly());
	}
	
	
	@Test
	public void testBodyOnly() throws Exception{
		HtmlPageRenderNode page = HtmlPageRenderNode.localHtmlPage("/page.html").bodyOnly();
		when(resourceSourceServiceResolver.toInputStream(page.getPage(), servletContext)).thenReturn(resource);
		
		service.render(page, renderMap, httpServletRequest, servletContext);
		
		verify(htmlParsingService).parse(eq(resource), eq(servletContext),htmlParseOptions.capture());
		assertTrue(htmlParseOptions.getValue().isBodyOnly());
	}
}
