package org.jfl110.prender.impl.parse;

import org.jfl110.prender.api.parse.CssCompressor;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.parse.ParseTransformation;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Convinence binding of all the defaul parsing services
 * @author JFL110
 */
public class DefaultParsingModule extends AbstractModule {

	@Override
	protected void configure() {
		// Parsing
		bind(HtmlParsingService.class).to(HtmlParsingServiceImpl.class);
		
		Multibinder<ParseTransformation> transformations = Multibinder
		.newSetBinder(binder(),ParseTransformation.class);
		
		transformations.addBinding().to(CssInlineParseTransformation.class);
		transformations.addBinding().to(CssCompressionParseTransformation.class);
		transformations.addBinding().to(JSInlineParseTransformation.class);
		
		bind(CssCompressor.class).to(YUICssCompressor.class);
	}
}
