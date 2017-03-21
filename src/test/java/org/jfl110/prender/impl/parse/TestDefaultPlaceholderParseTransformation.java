package org.jfl110.prender.impl.parse;

import static org.jfl110.prender.TestingUtils.assertAbsent;
import static org.jfl110.prender.TestingUtils.assertPresent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.PlaceholderRenderNode;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.parse.RenderTag;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

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
	private final RenderTag matchingOptionalTag1 = RenderTags.tag("div").addAttribute("data-optional-placeholder-key", keyA).build();
	
	
	@Test
	public void test() throws IOException{
		assertAbsent(transformation.transform(nonMatchingTag1, servletContext));
		
		assertPlaceholder(matchingTag1,keyA);
		assertPlaceholder(matchingTag2,keyA);
		assertPlaceholder(matchingTag3,keyB);
		assertPlaceholder(matchingOptionalTag1,keyA,true);
	}
	
	
	private void assertPlaceholder(RenderTag tag,String expectedKey) throws IOException{
		assertPlaceholder(tag, expectedKey,false);
	}
	
	
	private void assertPlaceholder(RenderTag tag,String expectedKey, boolean expectedOptional) throws IOException{
		Optional<SerializableRenderNode> result = transformation.transform(tag, servletContext);
		assertPresent(result);
		
		assertTrue(result.get() instanceof PlaceholderRenderNode);
		assertEquals(expectedKey,((PlaceholderRenderNode) result.get()).key());
		
		if(expectedOptional){
			Optional<RenderTag> original = ((PlaceholderRenderNode) result.get()).optionalOriginal();
			assertPresent(original);
			assertFalse(Iterables.any(original.get().attributes(), RenderTags.isAttribute("data-optional-placeholder-key")));
		}
	}
}
