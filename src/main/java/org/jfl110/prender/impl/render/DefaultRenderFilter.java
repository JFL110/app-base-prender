package org.jfl110.prender.impl.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;
import org.jfl110.prender.api.render.RenderFilter;
import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMapService;
import org.jfl110.prender.api.render.RenderServiceResolver;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
class DefaultRenderFilter implements RenderFilter{
	private static Logger logger = Logger.getLogger(DefaultRenderFilter.class.getSimpleName());
	
	private FilterConfig filterConfig;
	private final Provider<RenderMapService> renderMapService;
	private final Provider<RenderServiceResolver> renderServiceResolver;

	@Inject
	DefaultRenderFilter(Provider<RenderMapService> renderMapService,
			Provider<RenderServiceResolver> renderServiceResolver) {
		this.renderMapService = renderMapService;
		this.renderServiceResolver = renderServiceResolver;
	}

	private static class ResponseWrapper extends HttpServletResponseWrapper {

		private boolean responseAccessed;

		ResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			responseAccessed = true;
			return super.getOutputStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			responseAccessed = true;
			return super.getWriter();
		}

		boolean responseHasBeenAccessed() {
			return responseAccessed;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			logger.warning("Doing nothing - request is not HttpServletRequest");
			filterChain.doFilter(request, response);
			return;
		}

		if (!(response instanceof HttpServletResponse)) {
			logger.warning("Doing nothing - response is not HttpServletResponse");
			filterChain.doFilter(request, response);
			return;
		}
		
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
		filterChain.doFilter(request, responseWrapper);
		Optional<RenderMap> renderMap = renderMapService.get().get(request);

		if (!renderMap.isPresent()) {
			return;
		}

		if (responseWrapper.responseHasBeenAccessed()) {
			logger.warning("Response may have already been written to");
		}

		try {
			write(Collections.<RenderNode> singleton(renderMap.get().rootNode()),
					renderMap.get(),
					response.getWriter(),
					(HttpServletRequest) request,
					filterConfig.getServletContext());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void write(Collection<RenderNode> nodes,RenderMap renderMap,  PrintWriter writer, HttpServletRequest request,ServletContext context) throws IOException {
		for (RenderNode node : nodes) {
			writeNode(node,renderMap,writer, request,context);
		}
	}

	private void writeNode(RenderNode node,RenderMap renderMap,  PrintWriter writer, HttpServletRequest request,ServletContext context) throws IOException {
		if (node instanceof RenderStream) {
			((RenderStream) node).write(writer);
			return;
		}
		
		write(renderServiceResolver.get().render(node,renderMap, request, context),renderMap, writer, request,context);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {}
}
