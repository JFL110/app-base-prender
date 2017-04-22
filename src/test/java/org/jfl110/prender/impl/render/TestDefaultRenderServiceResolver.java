package org.jfl110.prender.impl.render;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.render.RenderServiceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TestDefaultRenderServiceResolver {

	private class RenderNodeType1 implements RenderNode {
	}

	private class RenderNodeType2 implements RenderNode {
	}

	private class RenderNodeType3 implements RenderNode {
	}

	private abstract class RenderService1 implements RenderService<RenderNodeType1> {
	}

	private abstract class RenderService2 implements RenderService<RenderNodeType2> {
	}

	private final RenderService1 renderService1 = Mockito.mock(RenderService1.class);
	private final RenderService2 renderService2 = Mockito.mock(RenderService2.class);
	private Set<RenderService<? extends RenderNode>> renderServices;

	private RenderServiceResolver service;

	@Before
	public void setUp() {
		renderServices = Sets.newHashSet();
		renderServices.add(renderService1);
		renderServices.add(renderService2);

		Mockito.when(renderService1.getRenderNodeType()).thenReturn(RenderNodeType1.class);
		Mockito.when(renderService2.getRenderNodeType()).thenReturn(RenderNodeType2.class);

		service = new DefaultRenderServiceResolver(renderServices);
	}

	@Test
	public void testCorrectRenderService() {
		assertEquals("Should get service 1 for node type 1", renderService1, service.get(RenderNodeType1.class));
		assertEquals("Should get service 2 for node type 2", renderService2, service.get(RenderNodeType2.class));
	}
	
	
	@Test
	public void testRender() throws IOException{
		// Given
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		ServletContext servletContext = mock(ServletContext.class);
		RenderNodeType1 renderNode = new RenderNodeType1();
		RenderMap renderMap = mock(RenderMap.class);
		
		// When
		Collection<RenderNode> renderOutput = Lists.newArrayList();
		
		// Then
		when(renderService1.render(renderNode,renderMap, httpServletRequest, servletContext)).thenReturn(renderOutput);
		
		assertEquals(renderOutput,service.render(renderNode,renderMap, httpServletRequest, servletContext));
	}


	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedExceptionForUnknownNodeType() {
		service.get(RenderNodeType3.class);
	}
}