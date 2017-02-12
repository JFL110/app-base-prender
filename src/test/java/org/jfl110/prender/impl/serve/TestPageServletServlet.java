package org.jfl110.prender.impl.serve;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMapService;
import org.junit.Test;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.util.Providers;

/**
 * Tests PageServletServlet
 *
 *
 * @author JFL110
 */
public class TestPageServletServlet {

	private final RenderMap map = mock(RenderMap.class);
	private final Supplier<RenderMap> mapSuppler = Suppliers.ofInstance(map);
	private final PageServletServlet servlet = new PageServletServlet(mapSuppler);
	private final RenderMapService renderMapService = mock(RenderMapService.class);
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	
	@Test
	public void test() throws Exception{
		servlet.inject(Providers.of(renderMapService));
		
		servlet.doGet(request, mock(HttpServletResponse.class));
		
		verify(renderMapService).put(map, request);
	}
}
