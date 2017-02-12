package org.jfl110.prender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.impl.parse.RenderTags;

import com.google.common.base.Optional;

public class TestingUtils {
	
	public static <T> void assertPresent(Optional<T> optional){
		assertTrue("Expected present",optional.isPresent());
	}
	
	public static <T> void assertAbsent(Optional<T> optional){
		assertFalse("Expected absent",optional.isPresent());
	}
	
	
	public static Matcher<RenderAttribute> matchingAttribute(final RenderAttribute attributeToMatch){
		return new TypeSafeMatcher<RenderAttribute>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("name:").appendText(attributeToMatch.name())
				.appendText(" value:").appendValue(attributeToMatch.value());
			}

			@Override
			protected boolean matchesSafely(RenderAttribute attribute) {
				return (attributeToMatch.value() == null ? 
						RenderTags.isAttribute(attributeToMatch.name()) :
					RenderTags.isAttribute(attributeToMatch.name(), attributeToMatch.value()))
						.apply(attribute);
			}
		};
	}
}
