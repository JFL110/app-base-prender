/*
 * YUI Compressor
 * http://developer.yahoo.com/yui/compressor/
 * Author: Julien Lecomte -  http://www.julienlecomte.net/
 * Author: Isaac Schlueter - http://foohack.com/
 * Author: Stoyan Stefanov - http://phpied.com/
 * Contributor: Dan Beam - http://danbeam.org/
 * Copyright (c) 2013 Yahoo! Inc.  All rights reserved.
 * The copyrights embodied in the content of this file are licensed
 * by Yahoo! Inc. under the BSD (revised) open source license.
 */
package org.jfl110.prender.impl.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;

import org.jfl110.prender.api.parse.CssCompressor;

/**
 * Wrapper for YUI CssCompressor
 * @authorJFL110
 */
class YUICssCompressor implements CssCompressor {
	
	@Override
	public void compress(Writer out, InputStream input, int maxCharsPerLine) throws IOException {
		new com.yahoo.platform.yui.compressor.CssCompressor(new InputStreamReader(input))
				.compress(out, maxCharsPerLine);
	}

	
	@Override
	public void compress(Writer out, String input, int maxCharsPerLine) throws IOException {
				new com.yahoo.platform.yui.compressor.CssCompressor(new StringReader(input))
				.compress(out, maxCharsPerLine);
	}
}
