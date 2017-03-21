package org.jfl110.prender.impl.Integration;

import static org.jfl110.prender.api.HtmlPageRenderNode.htmlPage;
import static org.jfl110.prender.api.render.RenderMaps.renderMap;
import static org.jfl110.prender.api.resources.ServletContextResourceSources.servletContextResource;

import org.jfl110.prender.api.StringRenderNodes;

class IntegrationTestCases {

	static IntegrationTestCase emptyPage(){
		return  new IntegrationTestCase()
			.titled("Empty page")
			.forRootNode(htmlPage(servletContextResource("index.html")))
			.withResource("index.html", "")
			.expect("<html><head></head><body></body></html>");
	}
	
	
	static IntegrationTestCase cssInline1(){
		return new IntegrationTestCase()
			.titled("Css Inline 1")
			.forRootNode(htmlPage(servletContextResource("index.html")))
			.withResource("index.html", "<html><head><style>div\n{height  : 2px;\n}\n</style></head><body></body></html>")
			.expect("<html><head><style>/*C*/div{height:2px;}</style></head><body></body></html>");
	}
	
	
	static IntegrationTestCase cssInlineAndCompress(){
		return new IntegrationTestCase()
			.titled("Css Inline And Compress")
			.forRootNode(htmlPage(servletContextResource("index.html")))
			.withResource("index.html", "<html><head><link data-inline type=\"text/css\" rel=\"stylesheet\" href=\"style.css\" /></style></head><body></body></html>")
			.withResource("style.css", "body{ margin:0px; } div\n{height  : 2px;\n}\n")
			.expect("<html><head><style type=\"text/css\">/*C*/body{margin:0;}div{height:2px;}</style></head><body></body></html>");
	}
	
	
	static IntegrationTestCase jsInline1(){
		return new IntegrationTestCase()
				.titled("JS Inline 1")
				.forRootNode(htmlPage(servletContextResource("index.html")))
				.withResource("index.html", "<html><head><script src=\"script.js\" data-inline async/></head><body><h1>HELLO</h1></body></html>")
				.withResource("script.js", "function doSomething(){}")
				.expect("<html><head><script async>function doSomething(){}</script></head><body><h1>HELLO</h1></body></html>");
	}
	
	
	static IntegrationTestCase optionalPlaceholder(){
		return new IntegrationTestCase()
				.titled("Optional placeholder")
				.forMap(
						renderMap(htmlPage(servletContextResource("index.html")))
						.putPlaceholderValue("b-key", StringRenderNodes.string("REPLACEMENT VALUE")))
				.withResource("index.html", "<html><head></head><body><div data-optional-placeholder-key=\"b-key\">DEFAULT VALUE</div><div data-optional-placeholder-key=\"a-key\"><b>DEFAULT <i>VALUE</i></b></div></body></html>")
				.expect("<html><head></head><body>REPLACEMENT VALUE<div><b>DEFAULT <i>VALUE</i></b></div></body></html>");
	}
}
