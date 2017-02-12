package org.jfl110.prender.impl.render;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.render.RenderServiceResolver;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class TestDefaultRenderServiceResolverGuice {

	private Injector injector;
	private RenderServiceResolver service;

	private abstract class RenderNodeType1 implements RenderNode {
	}

	private abstract class RenderNodeType2 implements RenderNode {
	}

	private static class RenderService1 implements RenderService<RenderNodeType1> {
		@Override
		public Class<RenderNodeType1> getRenderNodeType() {
			return RenderNodeType1.class;
		}

		@Override
		public Collection<RenderNode> render(RenderNodeType1 node,RenderMap renderMap, HttpServletRequest requestData,ServletContext context) {
			return null;
		}
	}

	@Before
	public void setUp() {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			public void configure() {
				 Multibinder.newSetBinder(binder(),new TypeLiteral<RenderService<? extends RenderNode>>() {})
				 .addBinding().to(RenderService1.class);
				bind(RenderServiceResolver.class).to(DefaultRenderServiceResolver.class);
			}
		});

		service = injector.getInstance(RenderServiceResolver.class);
	}

	@Test
	public void testCorrectRenderService1() {
		service.get(RenderNodeType1.class);
	}

	
	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedExceptionForUnknownNodeType() {
		service.get(RenderNodeType2.class);
	}
}
