package org.jfl110.prender.api.parse;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.SerializableRenderNode;

import com.google.common.base.Optional;

/**
 * Transformation to apply to RenderTags during parsing.
 *
 * @author JFL110
 */
public interface ParseTransformation {

	/**
	 * Transform a RenderTag. 
	 * 
	 * @param input The RenderTag that may be transformed.
	 * @param servletContext The servlet context for access to resources.
	 * @return Optional.absent() if no transformation is applicable, otherwise the transformation result.
	 * @throws IOException
	 */
	Optional<SerializableRenderNode> transform(RenderTag input,ServletContext servletContext) throws IOException;
}
