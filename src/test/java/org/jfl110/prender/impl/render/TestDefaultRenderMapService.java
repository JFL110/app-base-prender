package org.jfl110.prender.impl.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletRequest;

import org.jfl110.prender.api.render.RenderMap;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * Tests DefaultRenderMapService
 *
 * @author JFL110
 */
public class TestDefaultRenderMapService {

	private final RenderMap map = mock(RenderMap.class);
	private final ServletRequest servletRequest = mock(ServletRequest.class);
	
	DefaultRenderMapService service = new DefaultRenderMapService();
	
	@Test
	public void testPut(){
		service.put(map, servletRequest);
		verify(servletRequest).setAttribute("RenderMapKey",map);
	}
	
	@Test
	public void testNullGet(){
		assertFalse(service.get(servletRequest).isPresent());
	}
	
	@Test
	public void testGet(){
		when(servletRequest.getAttribute("RenderMapKey")).thenReturn(map);
		
		Optional<RenderMap> output = service.get(servletRequest);
		
		assertTrue(output.isPresent());
		assertEquals(map,output.get());
	}
}
