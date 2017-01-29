package org.jfl110.prender.impl.parse;

import java.util.Collection;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;
import org.jfl110.prender.api.parse.RenderTagBuilder;
import org.jfl110.prender.api.render.RenderNodeSpace;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
final class RenderTagImpl implements RenderTag, RenderTagBuilder {

	private final Collection<RenderNodeSpace> children = Lists.newArrayList();
	private final Collection<RenderAttribute> attributes = Lists.newArrayList();
	private String tagName;

	@Override
	public RenderTagBuilder addChild(RenderNodeSpace child) {
		children.add(child);
		return this;
	}

	@Override
	public RenderTagBuilder addChildSpaces(Collection<RenderNodeSpace> children) {
		this.children.addAll(children);
		return this;
	}
	
	@Override
	public RenderTagBuilder addChild(RenderNode child) {
		children.add(RenderNodeSpace.renderNodeSpace(child));
		return this;
	}

	@Override
	public RenderTagBuilder addChildren(Iterable<RenderNode> children) {
		for(RenderNode renderNode : children){
			this.children.add(RenderNodeSpace.renderNodeSpace(renderNode));
		}
		return this;
	}

	@Override
	public RenderTagBuilder addAttribute(RenderAttribute attribute) {
		attributes.add(attribute);
		return this;
	}
	
	@Override
	public Collection<RenderNodeSpace> children(){
		return children;
	}

	@Override
	public RenderTagBuilder addAttributes(Iterable<RenderAttribute> attributes) {
		for(RenderAttribute attribute : attributes){
			this.attributes.add(attribute);
		}
		return this;
	}

	@Override
	public RenderTagBuilder setTagName(String tagName) {
		this.tagName = tagName;
		return this;
	}

	@Override
	public String tagName() {
		return tagName;
	}

	@Override
	public Collection<RenderAttribute> attributes() {
		return attributes;
	}

	@Override
	public RenderTag build() {
		return this;
	}

	@Override
	public RenderTagBuilder addAttribute(String key, String value) {
		attributes.add(new RenderAttributeImpl(key,value));
		return this;
	}

	@Override
	public RenderTagBuilder addAttribute(String key) {
		attributes.add(new RenderAttributeImpl(key));
		return this;
	}
}