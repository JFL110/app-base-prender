package org.jfl110.prender.impl.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.ServletContextResourceSource;
import org.jfl110.prender.api.resources.ServletContextResourceSources;
import org.junit.Test;

/**
 * Tests ServletContextResourceSourceService
 *
 * @author JFL110
 */
public class TestServletContextResourceSourceService {

	private final ServletContextResourceSourceService service = new ServletContextResourceSourceService();
	
	@Test
	public void testToInputStream(){
		ServletContextResourceSource source = ServletContextResourceSources.servletContextResource("PATH");
		ServletContext servletContext = mock(ServletContext.class);
		InputStream inputStream= mock(InputStream.class);
		when(servletContext.getResourceAsStream(source.getPath())).thenReturn(inputStream);
		
		assertEquals(inputStream,service.toInputStream(source, servletContext).getInputStream());
		assertEquals(source.getPath(),service.toInputStream(source, servletContext).getPath());
		assertEquals(ServletContextResourceSource.class,service.getResourceSourceType());
	}
}
