package org.jfl110.prender.api;

public class StringRenderNodes {
	
	/**
	* Creates a new StringRenderNode
	*/
	public static StringRenderNode string(String string) {
		return new StringRenderNode(string);
	}

}
