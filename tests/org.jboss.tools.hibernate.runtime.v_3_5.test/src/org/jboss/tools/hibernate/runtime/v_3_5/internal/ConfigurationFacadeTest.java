package org.jboss.tools.hibernate.runtime.v_3_5.internal;

import org.hibernate.cfg.Configuration;
import org.jboss.tools.hibernate.runtime.common.AbstractConfigurationFacade;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.junit.Assert;

public class ConfigurationFacadeTest {
	
	private String methodName = null;
	private Object[] arguments = null;
	
	private IConfiguration configuration = null;
	
	public void setUp() {
		methodName = null;
		arguments = null;
		configuration = new AbstractConfigurationFacade(null, new TestConfiguration()) {};
	}
	
	public void testGetProperty() {
		Assert.assertSame(TestConfiguration.PROPERTY, configuration.getProperty("foobar"));
		Assert.assertEquals("getProperty", methodName);
		Assert.assertArrayEquals(new Object[] { "foobar" }, arguments);
	}
	
	@SuppressWarnings("serial")
	private class TestConfiguration extends Configuration {
		static final String PROPERTY = "TestConfiguration.PROPERTY";
		public String getProperty(String driver) {
			methodName = "getProperty";
			arguments = new Object[] { driver };
			return PROPERTY;
		}
	}

}