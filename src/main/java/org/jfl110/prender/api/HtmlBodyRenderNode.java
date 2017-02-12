package org.jfl110.prender.api;

import org.jfl110.prender.api.resources.ResourceSource;

public class HtmlBodyRenderNode implements RenderNode{
	
	private final ResourceSource resourceSource;
	
	public static HtmlBodyRenderNode htmlBody(ResourceSource resourceSource){
		return new HtmlBodyRenderNode(resourceSource);
	}
	
	private HtmlBodyRenderNode(ResourceSource resourceSource){
		this.resourceSource = resourceSource;
	}
	
	
	public ResourceSource getBodyResource() {
		return resourceSource;
	}	
}