package org.jfl110.prender.impl.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.google.inject.ImplementedBy;

@ImplementedBy(StreamToStringService.StreamToStringServiceImpl.class)
interface StreamToStringService {

	public String convertStreamToString(InputStream inputStream, String charset) throws IOException;

	public String convertStreamToString(InputStream inputStream) throws IOException;

	class StreamToStringServiceImpl implements StreamToStringService {
		
		private static final String DELIMITER = "\\A";

		/**
		 * Input stream -> String
		 */
		@Override
		public String convertStreamToString(InputStream inputStream, String charset) throws IOException {
			Scanner scanner = null;
			try {
				scanner = charset == null ? new java.util.Scanner(inputStream)
						: new java.util.Scanner(inputStream, charset);
				scanner.useDelimiter(DELIMITER);
				return scanner.hasNext() ? scanner.next() : "";
			} finally {
				scanner.close();
				inputStream.close();
			}
		}

		/**
		 * Input stream -> String
		 */
		@Override
		public String convertStreamToString(InputStream inputStream) throws IOException {
			return convertStreamToString(inputStream, null);
		}

	}
}
