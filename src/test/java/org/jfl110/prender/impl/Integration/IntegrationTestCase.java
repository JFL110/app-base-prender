package org.jfl110.prender.impl.Integration;

import static org.jfl110.prender.api.render.RenderMaps.renderMap;

import java.util.Map;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.render.RenderMap;

import com.google.common.collect.Maps;

class IntegrationTestCase {

	private String title;
	private RenderMap rootMap;
	private String expectedOutput;
	private final Map<String, String> resourceMap = Maps.newHashMap();
	
	IntegrationTestCase titled(String title) {
		this.title = title;
		return this;
	}

	IntegrationTestCase forRootNode(RenderNode rootMap) {
		this.rootMap = renderMap(rootMap);
		return this;
	}
	
	IntegrationTestCase forMap(RenderMap renderMap) {
		this.rootMap = renderMap;
		return this;
	}

	IntegrationTestCase withResource(String address, String content) {
		resourceMap.put(address, content);
		return this;
	}

	IntegrationTestCase expect(String expectedOutput) {
		this.expectedOutput = expectedOutput;
		return this;
	}

	RenderMap getRootMap() {
		return rootMap;
	}

	String getExpectedOutput() {
		return expectedOutput;
	}

	Map<String, String> getResourceMap() {
		return resourceMap;
	}
	
	Object[] toObject(){
		return new Object[]{title,this};
	}
}