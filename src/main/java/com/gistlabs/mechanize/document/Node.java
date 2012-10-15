package com.gistlabs.mechanize.document;

import java.util.List;

import com.gistlabs.mechanize.query.AbstractQuery;

/**
 * Describes a node of a document having attributes and child nodes.
 * 
 * <p>To support additional interpreted attributes special attribute names can be used following the notation ${attributeName}.
 *    Refer to the current node implementation for supported special attributes (e.g <code>HtmlElement.getAttribute("${text}")</code>)</p>
 *  
 * @author Martin Kersten <Martin.Kersten.mk@gmail.com>
 */
public interface Node {

	/** Returns the name of the node or null if none (similar to getAttribute(SpecialAttribute.SPECIAL_ATTRIBUTE_NAME)). */
	String getName();
	
	/** Returns the name of the node or null if none (similar to getAttribute(SpecialAttribute.SPECIAL_ATTRIBUTE_VALUE)). */
	String getValue();
	
	/** Returns the first child element matching the query by performing a deep first left right search. */
	Node get(AbstractQuery<?> query);

	/** Returns all child elements matching the query by performing a deep first left right search. */
	List<? extends Node> getAll(AbstractQuery<?> query);

	/** Returns the child elements. */
	List<? extends Node> getChildren();

	/** Returns true if the attribute is set and has a value of if a special attribute (${name}) is supported. */
	boolean hasAttribute(String attributeKey);

	/** Returns the value of the attribute. */ 
	String getAttribute(String attributeKey);
	
	/** Returns true if the given attribute name is a multiple value attribute being a comma separated list without whitespace. */
	boolean isMultipleValueAttribute(String attributeKey);
	
	/** Returns all attribute names being present including any supported special attribute. */
	List<String> getAttributeNames();
}