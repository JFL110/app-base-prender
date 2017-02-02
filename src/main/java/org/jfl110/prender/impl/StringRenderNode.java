package org.jfl110.prender.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;

public final class StringRenderNode implements RenderStream {

	private final String string;

	private StringRenderNode(String string) {
		this.string = string;
	}

	public static StringRenderNode string(String string) {
		return new StringRenderNode(string);
	}

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
