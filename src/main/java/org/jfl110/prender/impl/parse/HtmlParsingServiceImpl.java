package org.jfl110.prender.impl.parse;

import static org.jfl110.prender.api.StringRenderNodes.string;
import static org.jfl110.prender.api.render.RenderNodeSpace.renderNodeSpace;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.jfl110.prender.api.RenderNode;
import org.jfl110.prender.api.SerializableRenderNode;
import org.jfl110.prender.api.parse.HtmlParseOptions;
import org.jfl110.prender.api.parse.HtmlParsingService;
import org.jfl110.prender.api.parse.ParseTransformation;
import org.jfl110.prender.api.parse.RenderTag;
import org.jfl110.prender.api.parse.RenderTagBuilder;
import org.jfl110.prender.api.render.RenderNodeSpace;
import org.jfl110.prender.api.resources.Resource;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.BooleanAttribute;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

class HtmlParsingServiceImpl implements HtmlParsingService {
	
	private static Logger logger = Logger.getLogger(HtmlParsingServiceImpl.class.getSimpleName());
	private static final int MAX_TRANSFORM_PASSES = 100;

	private final Provider<JSoupParsingService> jsoupParsingService;
	private final Set<ParseTransformation> postParseTransformations;

	@Inject
	HtmlParsingServiceImpl(Provider<JSoupParsingService> jsoupParsingService,
			Set<ParseTransformation> postParseTransformations) {
		this.jsoupParsingService = jsoupParsingService;
		this.postParseTransformations = postParseTransformations;
	}

	@Override
	public RenderTag parse(Resource page, ServletContext servletContext) throws IOException {
		return parse(page, servletContext, HtmlParseOptions.parseOptions());
	}

	@Override
	public RenderTag parse(Resource page, ServletContext servletContext,HtmlParseOptions options) throws IOException {
		Element doc = null;
		try {
			doc = jsoupParsingService.get().parseStream(page,servletContext,options);
		} catch (IOException e) {
			throw new IOException("Error reading document [" + page.getPath() + "]", e);
		}
		
		RenderTagBuilder rootTag = new RenderTagImpl();
		rootTag.setTagName("");
		
		addChildNodes(rootTag,doc.childNodes(),servletContext);

		page.getInputStream().close();
		
		return rootTag.build();
	}
	
	private void addChildNodes(RenderTagBuilder parent,List<Node> childNodes,ServletContext servletContext) throws IOException{
		for(Node node : childNodes){
			
			if(node instanceof Element){
				RenderTagImpl tag = new RenderTagImpl();
				tag.setTagName(((Element) node).tagName());
				addChildNodes(tag, node.childNodes(),servletContext);
				addChildAttributes(tag,node.attributes().asList());
				parent.addChild(RenderNodeSpace.renderNodeSpace(transform(tag,servletContext)));
				continue;
			}
			
			if(node instanceof TextNode){
				parent.addChild(renderNodeSpace(string(((TextNode) node).text())));
				continue;
			}
			
			if(node instanceof DataNode){
				parent.addChild(renderNodeSpace(string(((DataNode) node).getWholeData())));
				continue;
			}
			
			if(node instanceof Comment){
				continue;
			}
			
			if(node instanceof DocumentType){
				parent.addChild(renderNodeSpace(string("<!DOCTYPE html>"))); //TODO get actual doc type
				continue;
			}
			
			// TODO the rest
			
			logger.info("Unhandled Jsoup Node type ["+(node == null ? "NULL" : node.nodeName())+"]");
		}
	}
	
	private void addChildAttributes(RenderTagBuilder tag,List<Attribute> attributes){
		for(Attribute attribute : attributes){
			tag.addAttribute(
					attribute instanceof BooleanAttribute ? 
							new RenderAttributeImpl(attribute.getKey()):
							new RenderAttributeImpl(attribute.getKey(), attribute.getValue()));
		}
	}
	
	private RenderNode transform(RenderTag tag,ServletContext servletContext) throws IOException{
		RenderTag currentTag = tag;
		for(int pass = 0;  pass < MAX_TRANSFORM_PASSES; pass++){
			Optional<SerializableRenderNode> transformed = singlePassTransform(currentTag,servletContext);
			
			if(!transformed.isPresent()){
				return currentTag;
			}
			
			if(!(transformed.get() instanceof RenderTag)){
				return transformed.get();
			}
			
			currentTag = (RenderTag) transformed.get();
		}
		
		return currentTag;
	}
	
	private Optional<SerializableRenderNode> singlePassTransform(RenderTag tag,ServletContext servletContext) throws IOException{
		for(ParseTransformation transformation : postParseTransformations){
			Optional<SerializableRenderNode> transformedNode = transformation.transform(tag,servletContext);
			
			if(transformedNode.isPresent()){
				return transformedNode;
			}
		}
		return Optional.absent();
	}
}
