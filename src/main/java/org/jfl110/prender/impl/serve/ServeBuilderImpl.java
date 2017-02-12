package org.jfl110.prender.impl.serve;

import java.util.Set;

import javax.servlet.Filter;

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
		renderMapFunction = Suppliers.ofInstance(renderMap);
		return this;
	}
	
	
	@Override
	public ServeBuilder<?> through(Class<? extends javax.servlet.Filter> filterClazz) {
		filters.add(filterClazz);
		return this;
	}


	@Override
	protected void configureServlets() {
		for(Class<? extends Filter> filterClazz : filters){
			filter(path).through(filterClazz);
		}
		
		filter(path).through(RenderFilter.class);
		serve(path).with(new PageServletServlet(renderMapFunction));
	}

}