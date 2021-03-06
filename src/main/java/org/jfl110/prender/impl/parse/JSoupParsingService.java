package org.jfl110.prender.impl.parse;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.parse.HtmlParseOptions;
import org.jfl110.prender.api.resources.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Provider;

@ImplementedBy(JSoupParsingService.JSoupParsingServiceImpl.class)
interface JSoupParsingService {
	
	Element parseStream(Resource page, ServletContext context,HtmlParseOptions options) throws IOException;
	
	class JSoupParsingServiceImpl implements JSoupParsingService{
		
		private static final String DEFAULT_CHARSET = "UTF-8";
		
		private final Provider<StreamToStringService> streamToStringService;
		
		@Inject
		JSoupParsingServiceImpl(Provider<StreamToStringService> streamToStringService){
			this.streamToStringService = streamToStringService;
		}

		
		@Override
		public Element parseStream(Resource page, ServletContext context,HtmlParseOptions options) throws IOException {
			configureDetaultOptions(options);
			return options.isBodyOnly() ? parseBodyOnly(page.getInputStream(),page.getPath(),options) : parseWholeDocument(page.getInputStream(),page.getPath(),options);
		}
		
		/**
		 * Parse the entire document
		 */
		private Document parseWholeDocument(InputStream inputStream,String path,HtmlParseOptions options)  throws IOException{
			return Jsoup.parse(inputStream, options.getCharset(),path);
		}
		
		/**
		 * Parse just the body
		 */
		private Element parseBodyOnly(InputStream inputStream,String path,HtmlParseOptions options)  throws IOException{
			Document doc =  Jsoup.parseBodyFragment(
					streamToStringService.get().convertStreamToString(inputStream,options.getCharset()),path);
			return doc.body();
		}
		
		/**
		 * Set option defaults
		 */
		private void configureDetaultOptions(HtmlParseOptions options){
			if(options.getCharset() == null){
				options.withCharset(DEFAULT_CHARSET);
			}
		}
	}
}