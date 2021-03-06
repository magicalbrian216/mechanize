/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.document.html.query;

import static com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder.*;
import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder;
import com.gistlabs.mechanize.document.html.query.HtmlQueryStrategy;
import com.gistlabs.mechanize.document.query.Pattern;


/**
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 */
public class HtmlQueryTest {

	HtmlQueryStrategy strategy = new HtmlQueryStrategy();
	
	@Test
	public void testSinglePartQueryWithString() {
		assertEquals("(<value,[*]>)", HtmlQueryBuilder.inBrackets(HtmlQueryBuilder.byAny("value")).toString());
		assertEquals("<not<value,[*]>>", HtmlQueryBuilder.not(HtmlQueryBuilder.byAny("value")).toString());
		assertEquals("<value,[test]>", HtmlQueryBuilder.by("test", "value").toString());
		assertEquals("<value,[test, test2]>", HtmlQueryBuilder.by(attributes("test", "test2"), "value").toString());
		assertEquals("<value,[*]>", HtmlQueryBuilder.byAny("value").toString());
		assertEquals("<value,[name]>", HtmlQueryBuilder.byName("value").toString());
		assertEquals("<value,[id]>", HtmlQueryBuilder.byId("value").toString());
		assertEquals("<value,[name, id]>", HtmlQueryBuilder.byNameOrId("value").toString());
		assertEquals("<value,[${nodeName}]>", HtmlQueryBuilder.byTag("value").toString());
		assertEquals("<value,[${classNames}]>", HtmlQueryBuilder.byClass("value").toString());
		assertEquals("<value,[href]>", HtmlQueryBuilder.byHRef("value").toString());
		assertEquals("<value,[src]>", HtmlQueryBuilder.bySrc("value").toString());
		assertEquals("<value,[title]>", HtmlQueryBuilder.byTitle("value").toString());
		assertEquals("<value,[width]>", HtmlQueryBuilder.byWidth("value").toString());
		assertEquals("<value,[height]>", HtmlQueryBuilder.byHeight("value").toString());
		assertEquals("<value,[value]>", HtmlQueryBuilder.byValue("value").toString()); 
		assertEquals("<value,[type]>", HtmlQueryBuilder.byType("value").toString());
		assertEquals("<value,[${nodeValue}]>", HtmlQueryBuilder.byText("value").toString());
		assertEquals("<value,[${innerHtml}]>", HtmlQueryBuilder.byInnerHtml("value").toString());
		assertEquals("<value,[${html}]>", HtmlQueryBuilder.byHtml("value").toString());
	}
	
	@Test
	public void testSinglePartQueryWithPattern() {
		assertEquals("<regEx(pattern),[test]>", HtmlQueryBuilder.by("test", regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[test, test2]>", HtmlQueryBuilder.by(attributes("test", "test2"), regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[*]>", HtmlQueryBuilder.byAny(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[name]>", HtmlQueryBuilder.byName(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[id]>", HtmlQueryBuilder.byId(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[name, id]>", HtmlQueryBuilder.byNameOrId(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[${nodeName}]>", HtmlQueryBuilder.byTag(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[${classNames}]>", HtmlQueryBuilder.byClass(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[href]>", HtmlQueryBuilder.byHRef(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[src]>", HtmlQueryBuilder.bySrc(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[title]>", HtmlQueryBuilder.byTitle(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[width]>", HtmlQueryBuilder.byWidth(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[height]>", HtmlQueryBuilder.byHeight(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[value]>", HtmlQueryBuilder.byValue(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[type]>", HtmlQueryBuilder.byType(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[${nodeValue}]>", HtmlQueryBuilder.byText(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[${innerHtml}]>", HtmlQueryBuilder.byInnerHtml(regEx("pattern")).toString());
		assertEquals("<regEx(pattern),[${html}]>", HtmlQueryBuilder.byHtml(regEx("pattern")).toString());
	}
	
	@Test
	public void testComposition() {
		assertEquals("<name,[name]>or<regEx(id),[id]>or<text,[${nodeValue}]>", byName("name").or.byId(regEx("id")).or.byText("text").toString());
	}

	@Test
	public void testAndComposition() {
		assertEquals("<name,[name]>and<regEx(id),[id]>and<text,[${nodeValue}]>", byName("name").and.byId(regEx("id")).and.byText("text").toString());
	}
	
	@Test
	public void testOrIsDefaultComposition() {
		assertEquals("<name,[name]>or<regEx(id),[id]>or<text,[${nodeValue}]>", byName("name").byId(regEx("id")).byText("text").toString());
	}
	
	@Test
	public void testOrIsStillStandardCompositionAfterAnd() {
		assertEquals("<name,[name]>and<regEx(id),[id]>or<text,[${nodeValue}]>", byName("name").and.byId(regEx("id")).byText("text").toString());
	}
	
	@Test
	public void testSimpleQueryMatchingTrue() {
		assertEquals("<value,[test]>", HtmlQueryBuilder.by("test", "value").toString());
		assertEquals("<value,[test, test2]>", HtmlQueryBuilder.by(attributes("test", "test2"), "value").toString());
		
		assertTrue(HtmlQueryBuilder.by("test", "value").matches(strategy, newElement("<a test='value'/>")));
		assertTrue(HtmlQueryBuilder.by(attributes("test", "test2"), "value").matches(strategy, newElement("<a test='value'/>")));
		assertTrue(HtmlQueryBuilder.by(attributes("test", "test2"), "value").matches(strategy, newElement("<a test2='value'/>")));

		assertTrue(HtmlQueryBuilder.byAny("value").matches(strategy, newElement("<a href='value'/>")));
		assertTrue(HtmlQueryBuilder.byName("value").matches(strategy, newElement("<a name='value'/>")));
		assertTrue(HtmlQueryBuilder.byId("value").matches(strategy, newElement("<a id='value'/>")));
		assertTrue(HtmlQueryBuilder.byNameOrId("value").matches(strategy, newElement("<a name='value'/>")));
		assertTrue(HtmlQueryBuilder.byNameOrId("value").matches(strategy, newElement("<a id='value'/>")));
		assertTrue(HtmlQueryBuilder.byTag("p").matches(strategy, newElement("<p/>")));
		assertTrue(HtmlQueryBuilder.byClass("value").matches(strategy, newElement("<a class='value'/>")));
		assertTrue(HtmlQueryBuilder.byHRef("value").matches(strategy, newElement("<a href='value'/>")));
		assertTrue(HtmlQueryBuilder.bySrc("value").matches(strategy, newElement("<a src='value'/>")));
		assertTrue(HtmlQueryBuilder.byTitle("value").matches(strategy, newElement("<a title='value'/>")));
		assertTrue(HtmlQueryBuilder.byWidth("value").matches(strategy, newElement("<a width='value'/>")));
		assertTrue(HtmlQueryBuilder.byHeight("value").matches(strategy, newElement("<a height='value'/>")));
		assertTrue(HtmlQueryBuilder.byValue("value").matches(strategy, newElement("<a value='value'/>")));
		assertTrue(HtmlQueryBuilder.byType("value").matches(strategy, newElement("<a type='value'/>")));
		assertTrue(HtmlQueryBuilder.byText("value").matches(strategy, 
				newElement("<a><b>v</b><strong>a<i>l</i>u</strong><b>e</b>")));
		assertTrue(HtmlQueryBuilder.byInnerHtml("value").matches(strategy, newElement("<a>value</a>")));
		assertTrue(HtmlQueryBuilder.byHtml("<a></a>").matches(strategy, newElement("<a></a>")));
	}
	
	@Test
	public void testCombinationQueryMatchingTrue() {
		assertTrue(byId("myId").or.bySrc("test.png").matches(strategy, newElement("<img id='myId'/>")));
		assertTrue(byId("myId").or.bySrc("test.png").matches(strategy, newElement("<img src='test.png'/>")));
		assertFalse(byId("myId").or.bySrc("test.png").matches(strategy, newElement("<img/>")));
	}
	
	private Element newElement(String string) {
		Document document = Jsoup.parse("<html><body>" + string + "</body></html>");
		return document.getElementsByTag("body").get(0).child(0);
	}
	
	@Test
	public void testAndCombination() {
		assertFalse(byId("myId").and.bySrc("test.png").matches(strategy, newElement("<img id='myId'/>")));
		assertFalse(byId("myId").and.bySrc("test.png").matches(strategy, newElement("<img src='test.png'/>")));
		assertTrue(byId("myId").and.bySrc("test.png").matches(strategy, newElement("<img id='myId' src='test.png'/>")));
		assertFalse(byId("myId").and.bySrc("test.png").matches(strategy, newElement("<img/>")));
	}

	@Test
	public void testInBracketsNot() {
		assertTrue(inBrackets(byId("myId")).matches(strategy, newElement("<img id='myId'/>")));
		assertFalse(inBrackets(byId("myId")).matches(strategy, newElement("<img id='otherId'/>")));
	}

	@Test
	public void testNot() {
		assertFalse(not(byId("myId")).matches(strategy, newElement("<img id='myId'/>")));
		assertTrue(not(byId("myId")).matches(strategy, newElement("<img id='otherId'/>")));
	}
	
	@Test
	public void testEverything() {
		assertEquals("<everything>", HtmlQueryBuilder.everything().toString());
		assertTrue(everything().matches(strategy, newElement("<a/>")));
	}
	
	@Test
	public void testCaseInsensitivePattern() {
		assertEquals("caseInsensitive(regEx(pattern))", caseInsensitive(regEx("pattern")).toString());
		assertEquals("caseInsensitive(string)", caseInsensitive("string").toString());
	}
	
	@Test
	public void testCaseSensitiveStringPattern() {
		Pattern pattern = HtmlQueryBuilder.string("string");
		assertTrue(pattern.doesMatch("string"));
		assertFalse(pattern.doesMatch("String"));
		assertFalse(pattern.doesMatch("STRING"));
		assertFalse(pattern.doesMatch("NoString"));
		assertFalse(pattern.doesMatch(null));
	}

	@Test
	public void testCaseInsensitiveStringPattern() {
		Pattern pattern = HtmlQueryBuilder.caseInsensitive("string");
		assertTrue(pattern.doesMatch("string"));
		assertTrue(pattern.doesMatch("String"));
		assertTrue(pattern.doesMatch("STRING"));
		assertFalse(pattern.doesMatch("NoString"));
		assertFalse(pattern.doesMatch(null));
	}
	
	@Test
	public void testCaseSensitiveRegularExpression() {
		Pattern pattern = regEx("[Tt]rue");
		assertTrue(pattern.doesMatch("true"));
		assertTrue(pattern.doesMatch("True"));
		assertFalse(pattern.doesMatch("TRUE"));
		assertFalse(pattern.doesMatch("false"));
		assertFalse(pattern.doesMatch(null));
	}
	
	@Test
	public void testCaseInsensitiveRegularExpression() {
		Pattern pattern = caseInsensitive(regEx("[t]rue"));
		assertTrue(pattern.doesMatch("true"));
		assertTrue(pattern.doesMatch("True"));
		assertTrue(pattern.doesMatch("TRUE"));
		assertFalse(pattern.doesMatch("false"));
		assertFalse(pattern.doesMatch(null));
	}
}
