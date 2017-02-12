package org.jfl110.prender.impl.serve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfl110.prender.api.render.RenderMap;
import org.jfl110.prender.api.render.RenderMapService;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("serial")
class PageServletServlet extends HttpServlet{

	private final Supplier<RenderMap> renderMapSupplier;
	private Provider<RenderMapService> renderMapService;
	
	PageServletServlet(Supplier<RenderMap> renderMapSupplier){
		this.renderMapSupplier = renderMapSupplier;
	}
	
	@Inject
	void inject(Provider<RenderMapService> renderMapService){
		this.renderMapService = renderMapService;
	}
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		renderMapService.get().put(renderMapSupplier.get(), request);
	}
}