package org.jfl110.prender.api;

import java.io.IOException;
import java.io.Writer;

/**
* A RenderStream that wraps a String and writes it to a Writer.
*
* @author JFL110
*/
public final class StringRenderNode implements RenderStream {

	private final String string;

	StringRenderNode(String string) {
		this.string = string;
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
