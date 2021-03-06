/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.hibernate.xml.model.impl;

import org.jboss.tools.common.model.impl.OrderedObjectImpl;

public class HibernateVersionImpl extends OrderedObjectImpl {
    private static final long serialVersionUID = 220540876994971851L;
	
	public String getPathPart() {
		return "version"; //$NON-NLS-1$
	}
	
	public String getPresentationString() {
		return "" + getAttributeValue("name");  //$NON-NLS-1$ //$NON-NLS-2$
	}
	
}
