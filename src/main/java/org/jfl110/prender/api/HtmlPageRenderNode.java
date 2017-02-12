package org.jfl110.prender.api;

import org.jfl110.prender.api.resources.ResourceSource;
import org.jfl110.prender.api.resources.ServletContextResourceSources;

public final class HtmlPageRenderNode implements RenderNode{
	
	private final ResourceSource resourceSource;
	
	public static HtmlPageRenderNode htmlPage(ResourceSource resourceSource){
		return new HtmlPageRenderNode(resourceSource);
	}
	
	public static HtmlPageRenderNode localHtmlPage(String path){
		return new HtmlPageRenderNode(ServletContextResourceSources.servletContextResource(path));
	}
	
	private HtmlPageRenderNode(ResourceSource resourceSource){
		this.resourceSource = resourceSource;
	}
	
	
	public ResourceSource getPage() {
		return resourceSource;
	}	
}