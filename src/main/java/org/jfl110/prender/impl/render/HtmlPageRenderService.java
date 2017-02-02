package org.jfl110.prender.impl.render;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.HtmlPageRenderNode;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.render.RenderService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HtmlPageRenderService implements RenderService<HtmlPageRenderNode>{

	private final Provider<HtmlParsingService> htmlParsingService;
	
	@Inject
	HtmlPageRenderService(Provider<HtmlParsingService> htmlParsingService){
		this.htmlParsingService = htmlParsingService;
	}
	
	@Override
	public Class<HtmlPageRenderNode> getRenderNodeType() {
		return HtmlPageRenderNode.class;
	}

	@Override
	public Collection<RenderNode> render(HtmlPageRenderNode node, HttpServletRequest requestData,ServletContext context) throws IOException {
		return Collections.<RenderNode>singleton(htmlParsingService.get().parse(node.pageName(), context));
	}

}
