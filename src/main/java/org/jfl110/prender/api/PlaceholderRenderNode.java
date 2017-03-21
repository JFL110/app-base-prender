package org.jfl110.prender.api;

import org.jfl110.prender.api.parse.RenderTag;

import com.google.common.base.Optional;

@SuppressWarnings("serial")
public class PlaceholderRenderNode implements SerializableRenderNode{

	private final String key;
	private Optional<RenderTag> optionalOriginal = Optional.absent();
	
	private PlaceholderRenderNode(String key){
		this.key = key;
	}
	
	public static PlaceholderRenderNode placeholder(String key){
		return new PlaceholderRenderNode(key);
	}
	
	
	public PlaceholderRenderNode optional(RenderTag original){
		this.optionalOriginal = Optional.of(original);
		return this;
	}
	
	
	public Optional<RenderTag> optionalOriginal() {
		return optionalOriginal;
	}
	
	
	public String key(){
		return key;
	}
}