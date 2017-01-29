package org.jfl110.prender.api.parse;

/**
 * Options when parsing HTML via HtmlParsingService
 * 
 * @author JFL110
 */
public class HtmlParseOptions {

	private String charset;
	private boolean bodyOnly;

	private HtmlParseOptions() {
	}

	public static HtmlParseOptions parseOptions() {
		return new HtmlParseOptions();
	}
	

	public HtmlParseOptions withCharset(String charset) {
		this.charset = charset;
		return this;
	}
	
	public HtmlParseOptions bodyOnly(){
		this.bodyOnly = true;
		return this;
	}

	public String getCharset() {
		return charset;
	}
	
	public boolean isBodyOnly() {
		return bodyOnly;
	}
}