package org.jfl110.prender.impl.parse;


import java.util.Collection;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.parse.RenderTag;
import org.jfl110.prender.api.parse.RenderTagBuilder;
import org.jfl110.prender.api.render.RenderNodeSpace;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class RenderTags {

	/**
	 * Starts a new tag.
	 */
	public static RenderTagBuilder tag(String tagName) {
		return new RenderTagImpl().setTagName(tagName);
	}

	
	/**
	 * Creates a new RenderAttribute.
	 */
	public static RenderAttribute attribute(String name, String value){
		return new RenderAttributeImpl(name,value);
	}
	
	
	/**
	 * Creates a new RenderAttribute.
	 */
	public static RenderAttribute attribute(String name){
		return new RenderAttributeImpl(name);
	}
	
	
	/**
	 * Copies a list of attributes
	 */
	public static Collection<RenderAttribute> copyOfAttributes(Collection<RenderAttribute> attributes){
		return Lists.newArrayList(
				Iterables.transform(attributes, new Function<RenderAttribute,RenderAttribute>(){
					@Override
					public RenderAttribute apply(RenderAttribute attributeToCopy) {
						return attribute(attributeToCopy.name(),attributeToCopy.value());
					}
				}));
	}
	
	
	/**
	 * Creates a shallow copy of a tag, including attributes based on a predicate.
	 */
	public static RenderTag shallowCopyTag(RenderTag renderTag,Predicate<RenderAttribute> attributeInclusionPredicate){
		return RenderTags
				.tag(renderTag.tagName())
				.addAttributes(
					Iterables.filter(
							copyOfAttributes(renderTag.attributes()),attributeInclusionPredicate))
				.addChildSpaces(renderTag.children())
				.build();
	}
	
	
	/**
	 * Case-insensitive match on an attribute
	 */
	public static Predicate<RenderAttribute> isAttribute(final String attributeName) {
		return new Predicate<RenderAttribute>() {
			@Override
			public boolean apply(RenderAttribute attribute) {
				return attribute.name() != null && attributeName.equals(attribute.name().toLowerCase());
			}
		};
	}
	
	/**
	 * Case-insensitive match on an attribute and value
	 */
	public static Predicate<RenderAttribute> isAttribute(final String attributeName,final String value) {
		return new Predicate<RenderAttribute>() {
			@Override
			public boolean apply(RenderAttribute attribute) {
				return attribute.name() != null 
						&& attributeName.equals(attribute.name().toLowerCase())
						&& ((value == null && attribute.value() == null) || value.equals(attribute.value()));
			}
		};
	}
	
	/**
	 * Finds first matching the supplied predicate
	 */
	public static Optional<RenderTag> findFirst(final RenderTag root, final Predicate<RenderTag> matching){
		if(matching.apply(root)){
			return Optional.of(root);
		}
		
		 for(RenderNodeSpace childSpace : root.children()){
			 RenderNode child = childSpace.get();
			 
			 if((child instanceof RenderTag)){
				 Optional<RenderTag> childMatch = findFirst((RenderTag) child,matching);
				 if(childMatch.isPresent()){
					 return childMatch;
				 }
			 }
		 }
		 
		 return Optional.absent();
	}
	
	
	/**
	 * Finds first attribute matching the supplied predicate
	 */
	public static Optional<RenderAttribute> findFirstAttribute(final RenderTag root, final Predicate<RenderAttribute> matching){
		 for(RenderAttribute attribute : root.attributes()){
			 if(matching.apply(attribute)){
				 return Optional.of(attribute);
			 }
		 }
		 return Optional.absent();
	}
	
	
	/**
	 * Matches tags by name
	 */
	public static Predicate<RenderTag> withTagName(final String tagName){
		 return new Predicate<RenderTag>() {
				@Override
				public boolean apply(RenderTag input) {
					return tagName.equals(input.tagName());
				}
			};
	}
}