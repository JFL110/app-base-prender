package org.jfl110.prender.impl.parse;

import org.jfl110.prender.api.parse.RenderAttribute;

class RenderAttributeImpl implements RenderAttribute {

	private final String name;
	private final String value;

	RenderAttributeImpl(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	RenderAttributeImpl(String name) {
		this.name = name;
		this.value = null;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String value() {
		return value;
	}
}