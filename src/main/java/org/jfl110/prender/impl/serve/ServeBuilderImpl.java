package org.jfl110.prender.impl.serve;

import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import org.jfl110.prender.api.HtmlPageRenderNode;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderFilter;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMaps;
import org.jfl110.prender.api.serve.ServeBuilder;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import com.google.inject.servlet.ServletModule;

class ServeBuilderImpl extends ServletModule implements ServeBuilder<ServeBuilder<?>> {
	
	private Supplier<RenderMap> renderMapFunction;
	private Class<? extends HttpServlet> servletClazz;
	private final String path;
	private final Set<Class<? extends Filter>> filters = Sets.newHashSet();
	
	ServeBuilderImpl(String path){
		this.path = path;
	}

	@Override
	public ServeBuilder<?> with(RenderNode renderNode) {
		return with(RenderMaps.renderMap(renderNode));
	}
	
	@Override
	public ServeBuilder<?> withLocalPage(String pagePath) {
		return with(HtmlPageRenderNode.localHtmlPage(pagePath));
	}

	@Override
	public ServeBuilder<?> with(RenderMap renderMap) {
		if(renderMapFunction != null){
			throw new IllegalArgumentException("RenderMap function already specified");
		}
		
		if(servletClazz != null){
			throw new IllegalArgumentException("Cannot specify RenderMap and HttpServlet");
		}
		
		renderMapFunction = Suppliers.ofInstance(renderMap);
		return this;
	}
	
	
	@Override
	public ServeBuilder<?> through(Class<? extends javax.servlet.Filter> filterClazz) {
		filters.add(filterClazz);
		return this;
	}
	

	@Override
	public ServeBuilder<?> with(Class<? extends HttpServlet> servletClazz) {
		
		if(renderMapFunction != null){
			throw new IllegalArgumentException("Cannot specify RenderMap and HttpServlet");
		}
		
		this.servletClazz = servletClazz;
		return this;
	}


	@Override
	protected void configureServlets() {
		for(Class<? extends Filter> filterClazz : filters){
			filter(path).through(filterClazz);
		}
		
		filter(path).through(RenderFilter.class);
		
		if(servletClazz == null){
			serve(path).with(new PageServletServlet(renderMapFunction));
		}else{
			serve(path).with(servletClazz);
		}
		
	}
}