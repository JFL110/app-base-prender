package org.jfl110.prender.api.parse;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests NoOpCssCompressor
 *
 * @author JFL110
 */
public class TestNoOpCssCompressor {

	private final CssCompressor compressor =  new CssCompressor.NoCompressCssCompressor();
	
	private final String css = "body{ \n margin:0px; \n}";
	
	@Test
	public void testCompress() throws IOException{
		StringWriter stringWriter = new StringWriter();
		compressor.compress(stringWriter, css, 1000);
		assertEquals(css,stringWriter.toString());
	}
	
	
	@Test
	public void testCompressInputStream() throws IOException{
		StringWriter stringWriter = new StringWriter();
		compressor.compress(stringWriter, new ByteArrayInputStream(css.getBytes(StandardCharsets.UTF_8)), 1000);
		assertEquals(css,stringWriter.toString());
	}
}
