/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.document.link;

import java.util.List;

import com.gistlabs.mechanize.Resource;
import com.gistlabs.mechanize.document.documentElements.DocumentElements;
import com.gistlabs.mechanize.document.node.Node;
import com.gistlabs.mechanize.document.query.QueryStrategy;

/** 
 * A collection of Link objects. 
 * 
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 */
public class Links extends DocumentElements<Link> {
	public Links(Resource page, QueryStrategy queryStrategy) {
		this(page, null, queryStrategy);
	}
	
	public Links(Resource page, List<? extends Node> links, QueryStrategy queryStrategy) {
		super(page, links, queryStrategy);
	}
	
	@Override
	protected Link newRepresentation(Node node) {
		return new Link(getPage(), node);
	}
}
