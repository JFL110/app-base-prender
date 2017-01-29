package org.jfl110.prender.impl.parse;

import static com.google.inject.util.Providers.of;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.jfl110.prender.TestingUtils.assertAbsent;
import static org.jfl110.prender.TestingUtils.matchingAttribute;
import static org.jfl110.prender.impl.StringRenderNode.string;
import static org.jfl110.prender.impl.parse.CssCompressionParseTransformation.COMPRESSED_MARKER;
import static org.jfl110.prender.impl.parse.RenderTags.attribute;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;

import org.jfl110.prender.TestingUtils;
import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.RenderStream;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.parse.CssCompressor;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.common.base.Optional;

/**
 * Tests CssCompressionParseTransformation

 * @author JFL110
 */
public class TestCssCompressionParseTransformation {

	private final CssCompressor compressor = mock(CssCompressor.class);
	private final ServletContext servletContext = mock(ServletContext.class);
	private final CssCompressionParseTransformation transformation = 
			new CssCompressionParseTransformation(of( compressor));
	
	private final String uncompressedCss = "uncompressed-css";
	private final String compressedCss = "compressed-css";
	
	private final RenderAttribute attribute1 = attribute("title","some-title");
	private final RenderAttribute hrefAttribute = attribute("href","style.css");
	
	private final RenderTag matchingTag1 = RenderTags.tag("style").addChild(string(uncompressedCss)).build();
	private final RenderTag matchingTag2 = RenderTags.tag("STYLE").addChild(string(uncompressedCss)).build();
	private final RenderTag matchingTag3 = RenderTags.tag("style").addAttribute(attribute1).addChild(string(uncompressedCss)).build();
	
	private final RenderTag nonMatchingTag1 = RenderTags.tag("style").addAttribute(hrefAttribute).addChild(string(uncompressedCss)).build();
	
	@Before
	public void setUp() throws IOException{
		doAnswer(new Answer<Void>(){
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				((Writer) invocation.getArgument(0)).write(compressedCss);
				return null;
			}
		}).
		when(compressor).compress(Mockito.any(Writer.class), Mockito.eq(uncompressedCss), Mockito.anyInt());
	}
	
	@Test
	public void test() throws IOException{
		assertOutput(transformation.transform(matchingTag1, servletContext),Lists.<RenderAttribute>newArrayList());
		assertOutput(transformation.transform(matchingTag2, servletContext),Lists.<RenderAttribute>newArrayList());
		assertOutput(transformation.transform(matchingTag3, servletContext),Lists.newArrayList(attribute1));
		assertAbsent(transformation.transform(nonMatchingTag1, servletContext));
	}
	
	
	private void assertOutput(Optional<SerializableRenderNode> output,List<RenderAttribute> expectedAttributes) throws IOException{
		TestingUtils.assertPresent(output);
		assertThat(output.get(), instanceOf(RenderTag.class));
		RenderTag tag = ((RenderTag) output.get());
		
		assertEquals("style",((RenderTag) output.get()).tagName());
		
		RenderNode child = Iterables.get(tag.children(),0).get();
		assertThat(child, instanceOf(RenderStream.class));
		StringWriter writer = new StringWriter();
		((RenderStream) child).write(writer);
		
		assertEquals(COMPRESSED_MARKER+compressedCss,writer.toString());
		
		assertThat(tag.attributes(),hasSize(expectedAttributes.size()));
		
		for(RenderAttribute attribute : expectedAttributes){
			assertThat(tag.attributes(),hasItem(matchingAttribute(attribute)));
		}
	}
}
