package org.jfl110.prender.impl.Integration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfl110.prender.api.render.RenderFilter;
import org.jfl110.prender.impl.parse.DefaultParsingModule;
import org.jfl110.prender.impl.render.DefaultRenderingModule;
import org.jfl110.prender.impl.resource.DefaultResourceSourceModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Prender integration tests
 *
 * @author JFL110
 */
@RunWith(Parameterized.class)
public class IntegrationTests {

	public static Collection<IntegrationTestCase> testCases() {
		return Lists.newArrayList(
				IntegrationTestCases.emptyPage(),
				IntegrationTestCases.cssInline1(),
				IntegrationTestCases.jsInline1(),
				IntegrationTestCases.cssInlineAndCompress(),
				IntegrationTestCases.optionalPlaceholder()
				);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Lists.newArrayList(Iterables.transform(testCases(), new Function<IntegrationTestCase, Object[]>() {
			@Override
			public Object[] apply(IntegrationTestCase testCase) {
				return testCase.toObject();
			}
		}));
	};
	
	@Parameter(value = 0)
	public String testCaseName;
	
	@Parameter(value = 1)
	public IntegrationTestCase testCase;
	
	private RenderFilter renderFilter;
	private Injector injector;
	
	private final StringWriter outputStringWriter = new StringWriter();
	private final ServletContext servletContext = mock(ServletContext.class);
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	private final FilterConfig filterConfig = mock(FilterConfig.class);
	
	@Before
	public void setUp() throws IOException,ServletException {
		
		injector = Guice.createInjector(
				new DefaultRenderingModule(),
				new DefaultParsingModule(),
				new DefaultResourceSourceModule());
		
		when(filterConfig.getServletContext()).thenReturn(servletContext);
		renderFilter = injector.getInstance(RenderFilter.class);
		renderFilter.init(filterConfig);
		
		when(response.getWriter()).thenReturn(new PrintWriter(outputStringWriter));
		
		when(servletContext.getResourceAsStream(Mockito.any(String.class)))
		.thenAnswer(new Answer<InputStream>(){
			@Override
			public InputStream answer(InvocationOnMock invocation) throws Throwable {
				String content = testCase.getResourceMap().get(invocation.getArgument(0));
				return new ByteArrayInputStream((content.getBytes(StandardCharsets.UTF_8)));
			}
		});
	
		when(request.getAttribute("RenderMapKey")).thenReturn(testCase.getRootMap());
	}
	
	
	@Test
	public void testFullStack() throws IOException,ServletException{
		renderFilter.doFilter(request, response, mock(FilterChain.class));
		assertEquals(testCase.getExpectedOutput(),outputStringWriter.toString());
	}
}
