package org.jboss.tools.hibernate.runtime.v_4_3.internal;

import org.hibernate.SessionFactory;
import org.jboss.tools.hibernate.runtime.common.AbstractSessionFactoryFacade;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;

public class SessionFactoryFacadeImpl extends AbstractSessionFactoryFacade {
	
	public SessionFactoryFacadeImpl(
			IFacadeFactory facadeFactory, 
			SessionFactory sessionFactory) {
		super(facadeFactory, sessionFactory);
	}

}