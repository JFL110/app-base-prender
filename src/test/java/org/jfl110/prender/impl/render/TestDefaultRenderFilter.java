package org.jfl110.prender.impl.render;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMapService;
import org.jfl110.prender.api.render.RenderServiceResolver;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.util.Providers;

/**
 * Tests DefaultRenderFilter
 *
 * @author JFL110
 */
public class TestDefaultRenderFilter {

	private final RenderMapService renderMapService = mock(RenderMapService.class);
	private final RenderServiceResolver renderServiceResolver = mock(RenderServiceResolver.class);

	private final DefaultRenderFilter filter = new DefaultRenderFilter(Providers.of(renderMapService),
			Providers.of(renderServiceResolver));
	private final FilterConfig filterConfig = mock(FilterConfig.class);
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	private final FilterChain filterChain = mock(FilterChain.class);
	private final RenderMap renderMap = mock(RenderMap.class);
	RenderStream renderStream = mock(RenderStream.class);
	private final ServletContext servletContext = mock(ServletContext.class);
	private final PrintWriter printWriter = mock(PrintWriter.class);
	
	@Before
	public void setUp() throws ServletException, IOException{
		filter.init(filterConfig);
		when(filterConfig.getServletContext()).thenReturn(servletContext);
		when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void testRendering() throws ServletException, IOException {
		
		when(renderMapService.get(request)).thenReturn(Optional.of(renderMap));
		when(renderMap.rootNode()).thenReturn(renderStream);
		
		filter.doFilter(request, response, filterChain);
		
		when(renderServiceResolver.render(renderStream,renderMap,request,servletContext)).thenReturn(Collections.<RenderNode>singleton(renderStream));
		verify(renderStream).write(printWriter);
	}
}
