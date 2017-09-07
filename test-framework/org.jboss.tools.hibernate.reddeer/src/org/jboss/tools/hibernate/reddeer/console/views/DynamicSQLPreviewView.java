/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.hibernate.reddeer.console.views;

import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Hibernate Dynamic SQL Preview View implementation
 * Provides HQL Editor 
 * @author Jiri Peterka
 *
 */
public class DynamicSQLPreviewView extends WorkbenchView {

	/**
	 * Initialization and lookup for Hibernate Dynamic SQL Preview
	 */
	public DynamicSQLPreviewView() {
		super("Hibernate Dynamic SQL Preview");
	}

}
