package org.jfl110.prender.impl.parse;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.jfl110.prender.TestingUtils.assertAbsent;
import static org.jfl110.prender.TestingUtils.assertPresent;
import static org.jfl110.prender.TestingUtils.matchingAttribute;
import static org.jfl110.prender.impl.parse.RenderTags.attribute;
import static org.jfl110.prender.impl.parse.RenderTags.tag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.StringRenderNode;
import org.jfl110.prender.api.cache.CacheService;
import org.jfl110.prender.api.cache.TestingCacheService;
import org.jfl110.prender.api.parse.RenderTag;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.util.Providers;

/**
 * Tests JSInlineParseTransformation
 *
 * @author JFL110
 */
public class TestJSInlineParseTransformation {
	
	private final CacheService cacheService = TestingCacheService.testingCacheService();
	private final StreamToStringService streamToStringService = mock(StreamToStringService.class);
	private final InputStream srcStream = mock(InputStream.class);
	private final ServletContext servletContext = mock(ServletContext.class);

	private final JSInlineParseTransformation transformation = new JSInlineParseTransformation(
			Providers.of(cacheService), Providers.of(streamToStringService));

	private final String srcPath = "someSrc.js";
	private final String src = "function(){}";

	private final RenderTag matchingTag1 = tag("script").addAttribute("src", srcPath).addAttribute("inline").build();
	private final RenderTag matchingTag2 = tag("SCRIPT").addAttribute("src", srcPath).addAttribute("inline").build();
	private final RenderTag matchingTag3 = tag("SCRIPT").addAttribute("src", srcPath).addAttribute("INLINE").build();
	private final RenderTag matchingTag4 = tag("SCRIPT").addAttribute("SRC", srcPath).addAttribute("inline").build();
	private final RenderTag matchingTag5WithAttributes = tag("script").addAttribute("src", srcPath).addAttribute("inline").addAttribute("defer").build();

	private final RenderTag nonMatchingTag1 = tag("img").addAttribute("src", srcPath).addAttribute("inline").build();
	private final RenderTag nonMatchingTag2 = tag("script").addAttribute("src", srcPath).build();
	private final RenderTag nonMatchingTag3 = tag("script").addAttribute("inline").build();
	private final RenderTag nonMatchingTag4 = tag("script").addAttribute("src", srcPath)
												.addAttribute("inline").addChild(tag("x").build()).build();

	@Before
	public void setUp() throws IOException {
		when(servletContext.getResourceAsStream(srcPath)).thenReturn(srcStream);
		when(streamToStringService.convertStreamToString(srcStream)).thenReturn(src);
	}

	@Test
	public void testMatchingTag() {
		assertPresent(transformation.transform(matchingTag1,servletContext));
		assertPresent(transformation.transform(matchingTag2,servletContext));
		assertPresent(transformation.transform(matchingTag3,servletContext));
		assertPresent(transformation.transform(matchingTag4,servletContext));
		assertPresent(transformation.transform(matchingTag5WithAttributes,servletContext));

		assertAbsent(transformation.transform(nonMatchingTag1,servletContext));
		assertAbsent(transformation.transform(nonMatchingTag2,servletContext));
		assertAbsent(transformation.transform(nonMatchingTag3,servletContext));
		assertAbsent(transformation.transform(nonMatchingTag4,servletContext));
	}

	
	@Test
	public void testInlining() {
		Optional<SerializableRenderNode> node = transformation.transform(matchingTag1, servletContext);
		assertPresent(node);
		
		assertThat(node.get(), instanceOf(RenderTag.class));
		RenderTag tag = (RenderTag) node.get();
		
		assertEquals("script",tag.tagName());
		assertThat(tag.children(),hasSize(1));
		
		RenderNode child = tag.children().iterator().next().get();
		assertThat(child,instanceOf(StringRenderNode.class));
		
		assertEquals(src,((StringRenderNode) child).getString());
	}
	
	
	@Test 
	public void testKeepAttributes(){
		Optional<SerializableRenderNode> node = transformation.transform(matchingTag5WithAttributes, servletContext);
		assertPresent(node);
		
		assertThat(node.get(), instanceOf(RenderTag.class));
		RenderTag tag = (RenderTag) node.get();
	
		assertThat(tag.attributes(),hasItem(matchingAttribute(attribute("defer"))));
		assertThat(tag.attributes(),not(hasItem(matchingAttribute(attribute("src")))));
	}
	
	
	@Test
	public void testErrorDuringGetStream() throws IOException{
		when(streamToStringService.convertStreamToString(srcStream)).thenThrow(IOException.class);
		assertAbsent(transformation.transform(matchingTag1,servletContext));
	}
	
	
	@Test
	public void testNullStream() throws IOException{
		when(streamToStringService.convertStreamToString(srcStream)).thenReturn(null);
		assertAbsent(transformation.transform(matchingTag1,servletContext));
	}
	
	
	@Test
	public void testErrorDuringRead() throws IOException{
		when(streamToStringService.convertStreamToString(srcStream)).thenThrow(IOException.class);
		assertAbsent(transformation.transform(matchingTag1,servletContext));
	}
}
