package org.jfl110.prender.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;

/**
* A RenderStream that wraps a String and writes it to a Writer.
*
* @author JFL110
*/
public final class StringRenderNode implements RenderStream {

	private final String string;

	private StringRenderNode(String string) {
		this.string = string;
	}
	
	
	/**
	* Creates a new StringRenderNode
	*/
	public static StringRenderNode string(String string) {
		return new StringRenderNode(string);
	}

	
	/**
	* Gets the String that will be written by write(Writer).
	* @return the String that will be written by write(Writer). 
	*/ 
	public String getString() {
		return string;
	}

	
	@Override
	public void write(Writer writer) throws IOException {
		writer.write(string);
	}
	
	
	@Override
	public String toString() {
		return "StringRenderNode ["+string+"]";
	}
}
