package org.jfl110.prender.api;

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