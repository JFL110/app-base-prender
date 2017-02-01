package org.jfl110.prender.api.parse;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests NoOpJavascriptCompressor
 *
 * @author JFL110
 */
public class TestNoOpJavascriptCompressor {

	private final JavascriptCompressor compressor =  new JavascriptCompressor.NoCompressJavascriptCompressor();
	
	private final String javascript = "function(x){ \n \n}";
	
	@Test
	public void testCompress() throws IOException{
		StringWriter stringWriter = new StringWriter();
		compressor.compress(stringWriter,javascript);
		assertEquals(javascript,stringWriter.toString());
	}
	
	
	@Test
	public void testCompressInputStream() throws IOException{
		StringWriter stringWriter = new StringWriter();
		compressor.compress(stringWriter, new ByteArrayInputStream(javascript.getBytes(StandardCharsets.UTF_8)));
		assertEquals(javascript,stringWriter.toString());
	}
}
