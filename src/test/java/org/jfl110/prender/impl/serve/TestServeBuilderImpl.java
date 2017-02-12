package org.jfl110.prender.impl.serve;

import static org.mockito.Mockito.mock;

import javax.servlet.Filter;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMapService;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Tests ServeBuilderImpl
 *
 *
 * @author JFL110
 */
public class TestServeBuilderImpl {

	private final RenderNode renderNode = mock(RenderNode.class);

	@Test
	public void test() {
		injector(Serving.of("path").with(renderNode).through(Filter.class));
		// TODO
	}

	private Injector injector(final Module module) {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(RenderMapService.class).toInstance(mock(RenderMapService.class));
			}
		}, module);
	}
}