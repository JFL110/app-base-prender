package org.jfl110.prender.impl.parse;

import static org.jfl110.prender.TestingUtils.assertAbsent;
import static org.jfl110.prender.impl.parse.RenderTags.findFirstAttribute;
import static org.jfl110.prender.impl.parse.RenderTags.isAttribute;
import static org.junit.Assert.assertEquals;

import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;
import org.junit.Test;

/**
 * Tests RenderTags
 *
 * @author JFL110
 */
public class TestRenderTags {

	private final RenderAttribute attribute1 = RenderTags.attribute("attribute1");
	private final RenderAttribute attribute2 = RenderTags.attribute("attribute2");
	private final RenderTag child1 = RenderTags.tag("child1").build();
	private final RenderTag child2 = RenderTags.tag("child2").build();
	private final RenderTag child3 = RenderTags.tag("child3").addChild(child1).build();

	private final RenderTag tag1 = RenderTags.tag("tag").addChild(child3).addAttribute(attribute1).build();

	@Test
	public void testFindFirstAttribute() {
		assertEquals(attribute1, findFirstAttribute(tag1, isAttribute(attribute1.name())).get());
		assertAbsent(findFirstAttribute(tag1, isAttribute(attribute2.name())));
	}

	@Test
	public void testFindFirstChild() {
		assertAbsent(RenderTags.findFirst(tag1, RenderTags.withTagName(child2.tagName())));
		assertEquals(child3, RenderTags.findFirst(tag1, RenderTags.withTagName(child3.tagName())).get());
		assertEquals(child1, RenderTags.findFirst(tag1, RenderTags.withTagName(child1.tagName())).get());
	}
}