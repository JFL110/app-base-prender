package org.jfl110.prender.api;

import org.jfl110.prender.api.resources.ResourceSource;

public final class HtmlPageRenderNode implements RenderNode{
	
	private final ResourceSource resourceSource;
	
	public static HtmlPageRenderNode htmlPage(ResourceSource resourceSource){
		return new HtmlPageRenderNode(resourceSource);
	}
	
	private HtmlPageRenderNode(ResourceSource resourceSource){
		this.resourceSource = resourceSource;
	}
	
	
	public ResourceSource getPage() {
		return resourceSource;
	}	
}