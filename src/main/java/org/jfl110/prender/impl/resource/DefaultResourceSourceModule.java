package org.jfl110.prender.impl.resource;

import org.jfl110.prender.api.resources.ResourceSource;
import org.jfl110.prender.api.resources.ResourceSourceService;
import org.jfl110.prender.api.resources.ResourceSourceServiceResolver;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * Binds the default ResourceSource related services
 *
 * @author JFL110
 */
public class DefaultResourceSourceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ResourceSourceServiceResolver.class).to(DefaultResourceSourceServiceResolver.class);
		
		Multibinder<ResourceSourceService<? extends ResourceSource>> multibinder =
					Multibinder.newSetBinder(binder(),new TypeLiteral<ResourceSourceService<? extends ResourceSource>>(){{}});
		
		multibinder.addBinding().to(ServletContextResourceSourceService.class);
		multibinder.addBinding().to(URLResourceSourceService.class);
	}

}