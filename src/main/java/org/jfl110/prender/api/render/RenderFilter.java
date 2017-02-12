package org.jfl110.prender.api.render;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.ImplementedBy;

@ImplementedBy(RenderFilter.NoOpRenderFilter.class)
public interface RenderFilter extends Filter {

	class NoOpRenderFilter implements RenderFilter {

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			chain.doFilter(request, response);
		}

		@Override
		public void destroy() {
		}
	}
}