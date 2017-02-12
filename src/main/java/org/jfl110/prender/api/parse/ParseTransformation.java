package org.jfl110.prender.api.parse;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.SerializableRenderNode;

import com.google.common.base.Optional;

/**
 * Transformation to apply to RenderTags during parsing.
 * 
 * If the input is not applicable for the transformation then transform
 * must return Optional.absent(). 
 *
 * The transformation must NOT be idempotent, meaning:
 * f(f(x)) = Optional.absent()  or f(f(f(x))) = Optional.absent() 
 * to some resonable limit of recursive calls.
 *
 * The result of the transformation may be cached and so should not include
 * dynamic data. See RenderTransformation for the inclusion of dynamic data.
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
