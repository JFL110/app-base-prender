package org.jfl110.prender.impl.render;

import static org.hamcrest.Matchers.hasSize;
import static org.jfl110.prender.api.PlaceholderRenderNode.placeholder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.junit.Test;

import com.google.common.base.Optional;


/**
 * Tests PlaceholderRenderService
 *
 * @author JFL110
 */
public class TestPlaceholderRenderService {

	private final PlaceholderRenderService service = new PlaceholderRenderService();
	private final RenderMap renderMap = mock(RenderMap.class);
	private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
	private final ServletContext servletContext = mock(ServletContext.class);
	private final RenderNode renderNode = mock(RenderNode.class);
	
	private final String key = "KEY";
	
	@Test
	public void testPresentKey() throws IOException{
		when(renderMap.placeHolderValue(key)).thenReturn(Optional.of(renderNode));
		Collection<RenderNode> result = service.render(placeholder(key), renderMap, httpServletRequest, servletContext);
	
		assertThat(result,hasSize(1));
		assertEquals(renderNode,result.iterator().next());
	}
	
	
	@Test
	public void testAbsentPresentKey() throws IOException{
		when(renderMap.placeHolderValue(key)).thenReturn(Optional.<RenderNode>absent());
		Collection<RenderNode> result = service.render(placeholder(key), renderMap, httpServletRequest, servletContext);
	
		assertThat(result,hasSize(0));
	}
}