/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.form;

import com.gistlabs.mechanize.document.node.Node;

/** 
 * Represents an input element of type 'text'.  
 * 
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 * @since 2012-09-12
 */
public class Search extends FormElement {

	public Search(Form form, Node node) {
		super(form, node);
	}
}
