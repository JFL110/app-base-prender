package org.jfl110.prender.api.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import com.google.inject.ImplementedBy;

/**
 * Minifies/compresses CSS
 * 
 * @author JFL110
 */
@ImplementedBy(CssCompressor.NoCompressCssCompressor.class)
public interface CssCompressor {

	/**
	 * Compress CSS
	 */
	void compress(Writer out, InputStream input, int maxCharsPerLine) throws IOException;
	
	
	/**
	 * Compress CSS
	 */
	void compress(Writer out, String input, int maxCharsPerLine) throws IOException;


	/**
	 * Default no-op CssCompressor
	 */
	class NoCompressCssCompressor implements CssCompressor {
		
		@Override
		public void compress(Writer out, InputStream inputStream, int maxCharsPerLine) throws IOException {
			Reader input = new InputStreamReader(inputStream);
			char[] buffer = new char[1024];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				out.write(buffer, 0, n);
			}
		}

		@Override
		public void compress(Writer out, String input, int maxCharsPerLine) throws IOException {
			out.write(input);
		}
	}
}
