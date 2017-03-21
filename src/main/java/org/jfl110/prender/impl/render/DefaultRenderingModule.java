package org.jfl110.prender.impl.render;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderFilter;
import org.jfl110.prender.api.render.RenderMapService;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.render.RenderServiceResolver;
import org.jfl110.prender.impl.parse.TagImplRenderService;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * Convenience binding of all the default rendering services
 * 
 * @authorJFL110
 */
public class DefaultRenderingModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(RenderFilter.class).to(DefaultRenderFilter.class);
		bind(RenderMapService.class).to(DefaultRenderMapService.class);
		bind(RenderServiceResolver.class).to(DefaultRenderServiceResolver.class);
		
		// Render services
		Multibinder<RenderService<? extends RenderNode>> multibinder = 
				Multibinder.newSetBinder(binder(),new TypeLiteral<RenderService<? extends RenderNode>>() {});
		
		// Defaults
		multibinder.addBinding().to(CollectionRenderService.class);
		multibinder.addBinding().to(HtmlPageRenderService.class);
		multibinder.addBinding().to(TagImplRenderService.class);
		multibinder.addBinding().to(PlaceholderRenderService.class);
	}
}