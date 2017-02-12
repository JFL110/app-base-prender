package org.jfl110.prender.impl.render;


import static org.jfl110.prender.api.parse.HtmlParseOptions.parseOptions;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.HtmlBodyRenderNode;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderService;
import org.jfl110.prender.api.resources.Resource;
import org.jfl110.prender.api.resources.ResourceSourceServiceResolver;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HtmlBodyRenderService implements RenderService<HtmlBodyRenderNode>{

	private final Provider<HtmlParsingService> htmlParsingService;
	private final Provider<ResourceSourceServiceResolver> resourceSourceServiceResolver;
	
	@Inject
	HtmlBodyRenderService(Provider<HtmlParsingService> htmlParsingService,
			Provider<ResourceSourceServiceResolver> resourceSourceServiceResolver){
		this.htmlParsingService = htmlParsingService;
		this.resourceSourceServiceResolver = resourceSourceServiceResolver;
	}
	
	@Override
	public Class<HtmlBodyRenderNode> getRenderNodeType() {
		return HtmlBodyRenderNode.class;
	}

	@Override
	public Collection<RenderNode> render(HtmlBodyRenderNode node,RenderMap renderMap,  HttpServletRequest requestData,ServletContext context) throws IOException {
		Resource inputStream = resourceSourceServiceResolver.get().toInputStream(node.getBodyResource(), context);
		return Collections.<RenderNode>singleton(
				htmlParsingService.get().parse(inputStream,context,parseOptions().bodyOnly()));
	}

}
