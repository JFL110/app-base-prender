package org.jfl110.prender.impl.parse;

import static org.jfl110.prender.TestingUtils.assertAbsent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.TestingUtils;
import org.jfl110.prender.api.PlaceholderRenderNode;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.parse.RenderTag;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * Tests DefaultPlaceholderParseTransformation
 *
 * @author JFL110
 */
public class TestDefaultPlaceholderParseTransformation {

	private final DefaultPlaceholderParseTransformation transformation = new DefaultPlaceholderParseTransformation();
	private final ServletContext servletContext = mock(ServletContext.class);	
	
	private final String keyA = "keyA";
	private final String keyB = "keyB";
	
	private final RenderTag nonMatchingTag1 = RenderTags.tag("br").build();
	private final RenderTag matchingTag1 = RenderTags.tag("div").addAttribute("data-placeholder-key", keyA).build();
	private final RenderTag matchingTag2 = RenderTags.tag("x:placeholder").addAttribute("key", keyA).build();
	private final RenderTag matchingTag3 = RenderTags.tag("x:placeholder").addAttribute("key", keyA).addAttribute("data-placeholder-key", keyB).build();
	
	
	@Test
	public void test() throws IOException{
		assertAbsent(transformation.transform(nonMatchingTag1, servletContext));
		
		assertPlaceholder(matchingTag1,keyA);
		assertPlaceholder(matchingTag2,keyA);
		assertPlaceholder(matchingTag3,keyB);
	}
	
	
	private void assertPlaceholder(RenderTag tag,String expectedKey) throws IOException{
		Optional<SerializableRenderNode> result = transformation.transform(tag, servletContext);
		TestingUtils.assertPresent(result);
		
		assertTrue(result.get() instanceof PlaceholderRenderNode);
		assertEquals(expectedKey,((PlaceholderRenderNode) result.get()).key());
	}
}
