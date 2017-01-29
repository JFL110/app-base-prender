package org.jfl110.prender.impl.render;

import org.jfl110.prender.api.RenderNode;

public final class HtmlPageRenderNode implements RenderNode{
	
	private final String pageName;
	
	public static HtmlPageRenderNode htmlPage(String pageName){
		return new HtmlPageRenderNode(pageName);
	}
	
	private HtmlPageRenderNode(String pageName){
		this.pageName = pageName;
	}
	
	
	public String pageName() {
		return pageName;
	}
	
}
