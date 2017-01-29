package org.jfl110.prender.impl.Integration;

import static org.jfl110.prender.impl.render.HtmlPageRenderNode.htmlPage;

class IntegrationTestCases {

	static IntegrationTestCase emptyPage(){
		return  new IntegrationTestCase()
			.titled("Empty page")
			.forRootNode(htmlPage("index.html"))
			.withResource("index.html", "")
			.expect("<html><head></head><body></body></html>");
	}
	
	
	static IntegrationTestCase cssInline1(){
		return new IntegrationTestCase()
			.titled("Css Inline 1")
			.forRootNode(htmlPage("index.html"))
			.withResource("index.html", "<html><head><style>div\n{height  : 2px;\n}\n</style></head><body></body></html>")
			.expect("<html><head><style>/*C*/div{height:2px;}</style></head><body></body></html>");
	}
	
	
	static IntegrationTestCase cssInlineAndCompress(){
		return new IntegrationTestCase()
			.titled("Css Inline And Compress")
			.forRootNode(htmlPage("index.html"))
			.withResource("index.html", "<html><head><link inline type=\"text/css\" rel=\"stylesheet\" href=\"style.css\" /></style></head><body></body></html>")
			.withResource("style.css", "body{ margin:0px; } div\n{height  : 2px;\n}\n")
			.expect("<html><head><style type=\"text/css\">/*C*/body{margin:0;}div{height:2px;}</style></head><body></body></html>");
	}
	
	
	static IntegrationTestCase jsInline1(){
		return new IntegrationTestCase()
				.titled("JS Inline 1")
				.forRootNode(htmlPage("index.html"))
				.withResource("index.html", "<html><head><script src=\"script.js\" inline async/></head><body><h1>HELLO</h1></body></html>")
				.withResource("script.js", "function doSomething(){}")
				.expect("<html><head><script async>function doSomething(){}</script></head><body><h1>HELLO</h1></body></html>");
	}
}
