/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.document;

import static com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.Resource;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.html.form.Forms;
import com.gistlabs.mechanize.document.html.image.Images;
import com.gistlabs.mechanize.document.link.Link;
import com.gistlabs.mechanize.document.link.Links;
import com.gistlabs.mechanize.document.node.Node;

/**
 * Represents a single or multiple-page document having a root node.
 * @author Martin Kersten <Martin.Kersten.mk@gmail.com>
 */
public abstract class Document extends Resource {
	private Links links;
	private Forms forms;
	private Images images;

	public Document(MechanizeAgent agent, HttpRequestBase request, HttpResponse response) {
		super(agent, request, response);
	}

	/**
	 * Returns the root node of the document
	 * @return
	 */
	public abstract Node getRoot();

	/**
	 *  Query for a matching link, find first match by either id or by class attributes.
	 * 
	 * @param query wrapped with byIdOrClass()
	 * @return first Link found
	 */
	public Link link(String query) {
		return links().get(byIdOrClass(query));
	}
	
	public Links links() {
		if(this.links == null) {
			this.links = loadLinks();
		}
		return this.links;
	}

	protected Links loadLinks() {
		return new Links(this, null);
	}

	/**
	 *  Query for a matching form, find first match by either id or by class attributes.
	 * 
	 * @param query wrapped with byIdOrClass()
	 * @return first Form found
	 */
	public Form form(String query) {
		return forms().get(byIdOrClassOrName(query));
	}

	public Forms forms() {
		if(this.forms == null) {
			this.forms = loadForms();
		}
		return this.forms;
	}

	protected Forms loadForms() {
		return new Forms(this, null);
	}

	public Images images() {
		if(this.images == null) {
			this.images = loadImages();
		}
		return this.images;
	}

	protected Images loadImages() {
		return new Images(this, null);
	}

}
