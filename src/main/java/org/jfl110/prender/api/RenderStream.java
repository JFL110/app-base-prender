package org.jfl110.prender.api;

import java.io.IOException;
import java.io.Writer;

/**
 * A type of RenderNode which can be directly 
 * written to the output.
 * 
 * @author JFL110
 */
public interface RenderStream extends RenderNode{
	void write(Writer writer) throws IOException;
}