package org.jfl110.prender.impl.resource;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.resources.Resource;
import org.jfl110.prender.api.resources.ResourceSource;
import org.jfl110.prender.api.resources.ResourceSourceService;
import org.jfl110.prender.api.resources.ResourceSourceServiceResolver;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

class DefaultResourceSourceServiceResolver implements ResourceSourceServiceResolver{

	private final Set<? extends ResourceSourceService<? extends ResourceSource>> resourceSourceServices;
	private final Map<Class<? extends ResourceSource>, ResourceSourceService<? extends ResourceSource>> serviceMap = Maps.newConcurrentMap();

	@Inject
	DefaultResourceSourceServiceResolver(Set<ResourceSourceService<? extends ResourceSource>> resourceSourceServices) {
		this.resourceSourceServices = resourceSourceServices;
		buildMap(); 
	}
	
	private void buildMap() {
		for (ResourceSourceService<? extends ResourceSource> service : resourceSourceServices) {
			serviceMap.put(service.getResourceSourceType(), service);
		}
	}
	 
	@Override
	public Resource toInputStream(ResourceSource resourceSource,ServletContext servletContext) {
		return getService(resourceSource).toInputStream(resourceSource, servletContext);
	}
	
	
	@SuppressWarnings("unchecked")
	private <T extends ResourceSource> ResourceSourceService<T> getService(T resourceSource) {
		ResourceSourceService<? extends ResourceSource> service = serviceMap.get(resourceSource.getClass());
		
		if(service == null){
			throw new IllegalArgumentException("No service bound for type ["+resourceSource.getClass().getSimpleName()+"]");
		}
		
		try {
			return (ResourceSourceService<T>) service;
		} catch (ClassCastException e) {
			throw new RuntimeException("Mapping exception!", e);
		}
	}

}
