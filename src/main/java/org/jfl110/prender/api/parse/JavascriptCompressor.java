package org.jfl110.prender.api.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import com.google.inject.ImplementedBy;

/**
 * Minifies javascript.
 *
 * @author JFL110
 */
@ImplementedBy(JavascriptCompressor.NoCompressJavascriptCompressor.class)
public interface JavascriptCompressor {

	/**
	 * Compress Javascript
	 */
	void compress(Writer out, InputStream input) throws IOException;
	
	
	/**
	 * Compress Javascript
	 */
	void compress(Writer out, String input) throws IOException;


	/**
	 * Default no-op JavascriptCompressor
	 *
	 * @author JFL110
	 */
	class NoCompressJavascriptCompressor implements JavascriptCompressor {

		@Override
		public void compress(Writer out, InputStream inputStream) throws IOException {
			Reader input = new InputStreamReader(inputStream);
			char[] buffer = new char[1024];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				out.write(buffer, 0, n);
			}
		}

		@Override
		public void compress(Writer out, String input) throws IOException {
			out.write(input);
		}
	}
}
