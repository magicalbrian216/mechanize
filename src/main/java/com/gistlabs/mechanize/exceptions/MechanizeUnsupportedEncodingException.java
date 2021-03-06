/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.exceptions;

/**
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 */
public class MechanizeUnsupportedEncodingException extends MechanizeException {

	private static final long serialVersionUID = 1L;

	public MechanizeUnsupportedEncodingException() {
		super();
	}

	public MechanizeUnsupportedEncodingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MechanizeUnsupportedEncodingException(String message) {
		super(message);
	}

	public MechanizeUnsupportedEncodingException(Throwable cause) {
		super(cause);
	}
}
