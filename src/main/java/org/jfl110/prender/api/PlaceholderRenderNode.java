package org.jfl110.prender.api;

@SuppressWarnings("serial")
public class PlaceholderRenderNode implements SerializableRenderNode{

	private final String key;
	
	private PlaceholderRenderNode(String key){
		this.key = key;
	}
	
	public static PlaceholderRenderNode placeholder(String key){
		return new PlaceholderRenderNode(key);
	}
	
	
	public String key(){
		return key;
	}
}