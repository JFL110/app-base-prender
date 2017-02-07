package org.jfl110.prender.impl.render;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.HtmlPageRenderNode;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.resources.InputStreamWithPath;
import org.jfl110.prender.api.resources.ResourceSourceServiceResolver;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HtmlPageRenderService implements RenderService<HtmlPageRenderNode>{

	private final Provider<HtmlParsingService> htmlParsingService;
	private final Provider<ResourceSourceServiceResolver> resourceSourceServiceResolver;
	
	@Inject
	HtmlPageRenderService(Provider<HtmlParsingService> htmlParsingService,
			Provider<ResourceSourceServiceResolver> resourceSourceServiceResolver){
		this.htmlParsingService = htmlParsingService;
		this.resourceSourceServiceResolver = resourceSourceServiceResolver;
	}
	
	@Override
	public Class<HtmlPageRenderNode> getRenderNodeType() {
		return HtmlPageRenderNode.class;
	}

	@Override
	public Collection<RenderNode> render(HtmlPageRenderNode node, HttpServletRequest requestData,ServletContext context) throws IOException {
		InputStream inputStream = resourceSourceServiceResolver.get().toInputStream(node.getPage(), context);
		return Collections.<RenderNode>singleton(htmlParsingService.get().parse(InputStreamWithPath.inputStreamWithPath(node.getPage().getPath(), inputStream), context));
	}

}
