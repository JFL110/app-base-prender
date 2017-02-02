package org.jfl110.prender.impl.parse;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static org.jfl110.prender.api.render.RenderNodeSpace.getNodes;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.StringRenderNodes;
import org.jfl110.prender.api.parse.RenderAttribute;
import org.jfl110.prender.api.render.RenderService;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class TagImplRenderService implements RenderService<RenderTagImpl> {

	private static final String TAG_OPEN = "<";
	private static final String TAG_CLOSE = ">";
	private static final String SPACE = " ";
	private static final String EQUALS_QUOTE = "=\"";
	private static final String QUOTE = "\"";
	private static final String TAG_OPEN_SLASH = "</";
	private static final String TAG_CLOSE_SLASH = "/>";
	
	private static final List<String> SINGLE_TAGS = Lists.newArrayList("br","meta","link");

	@Inject
	TagImplRenderService() {}

	@Override
	public Class<RenderTagImpl> getRenderNodeType() {
		return RenderTagImpl.class;
	}

	@Override
	public Collection<RenderNode> render(RenderTagImpl tag, HttpServletRequest requestData,ServletContext context) {

		Collection<RenderNode> output = newArrayList();

		if (tag.tagName() == null || tag.tagName() == "") {
			renderInnards(tag, output);
		} else if(isRenderAsSingleTag(tag)){
			renderAsSingleTag(tag,output);
		}else{
			renderAsOpenClose(tag, output);
		}

		return output;
	}

	/**
	 * <foo> bar </foo>
	 */
	private void renderAsOpenClose(RenderTagImpl tag, Collection<RenderNode> output) {

		StringBuilder frontTagBuilder = new StringBuilder(TAG_OPEN).append(tag.tagName());

		addAttributes(tag.attributes(), frontTagBuilder);

		frontTagBuilder.append(TAG_CLOSE);

		output.add(StringRenderNodes.string(frontTagBuilder.toString()));
		renderInnards(tag, output);

		StringBuilder endTagBuilder = new StringBuilder(TAG_OPEN_SLASH).append(tag.tagName()).append(TAG_CLOSE);

		output.add(StringRenderNodes.string(endTagBuilder.toString()));
	}
	
	/**
	 * <foo/>
	 */
	private void renderAsSingleTag(RenderTagImpl tag, Collection<RenderNode> output) {
		StringBuilder frontTagBuilder = new StringBuilder(TAG_OPEN).append(tag.tagName());
		addAttributes(tag.attributes(), frontTagBuilder);
		frontTagBuilder.append(TAG_CLOSE_SLASH);
		output.add(StringRenderNodes.string(frontTagBuilder.toString()));
	}

	/**
	 * ... bar ...
	 */
	private void renderInnards(RenderTagImpl tag, Collection<RenderNode> output) {
		output.addAll(transform(Lists.newArrayList(tag.children()), getNodes()));
	}
	
	private boolean isRenderAsSingleTag(RenderTagImpl tag){
		return tag.children().size() == 0 && SINGLE_TAGS.contains(tag.tagName());
	}

	private void addAttributes(Collection<? extends RenderAttribute> attributes, StringBuilder stringBuilder) {
		for (RenderAttribute attribute : attributes) {
			stringBuilder.append(SPACE).append(attribute.name());
			if(attribute.value()!=null){
				stringBuilder.append(EQUALS_QUOTE).append(attribute.value())
				.append(QUOTE);
			}
		}
	}
}
