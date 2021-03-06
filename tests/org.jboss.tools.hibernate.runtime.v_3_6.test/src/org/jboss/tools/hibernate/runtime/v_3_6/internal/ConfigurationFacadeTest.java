package org.jboss.tools.hibernate.runtime.v_3_6.internal;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.JDBCMetaDataConfiguration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.mapping.Table;
import org.jboss.tools.hibernate.runtime.common.AbstractConfigurationFacade;
import org.jboss.tools.hibernate.runtime.common.AbstractNamingStrategyFacade;
import org.jboss.tools.hibernate.runtime.common.AbstractReverseEngineeringStrategyFacade;
import org.jboss.tools.hibernate.runtime.common.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.jboss.tools.hibernate.runtime.spi.IMapping;
import org.jboss.tools.hibernate.runtime.spi.INamingStrategy;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.helpers.DefaultHandler;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ConfigurationFacadeTest {
	
	private static final IFacadeFactory FACADE_FACTORY = new FacadeFactoryImpl();

	private static final String TEST_CONFIGURATION_STRING =
			"<!DOCTYPE hibernate-configuration PUBLIC" +
			"		\"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"" +
			"		\"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">" +
			"<hibernate-configuration>" +
			"  <session-factory>" + 
			"  </session-factory>" + 
			"</hibernate-configuration>";

	private String methodName = null;
	private Object[] arguments = null;
	
	private IConfiguration configuration = null;
	
	@Before
	public void setUp() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Configuration.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(
					Object obj, 
					Method method, 
					Object[] args, 
					MethodProxy proxy) throws Throwable {
				if (methodName == null) {
					methodName = method.getName();
				}
				if (arguments == null) {
					arguments = args;
				}
				return proxy.invokeSuper(obj, args);
			}					
		});
		configuration = new AbstractConfigurationFacade(FACADE_FACTORY, enhancer.create()) {};
		reset();
	}
	
	@Test
	public void testGetProperty() {
		configuration.setProperty("foo", "bar");
		reset();
		Assert.assertEquals("bar", configuration.getProperty("foo"));
		Assert.assertEquals("getProperty", methodName);
		Assert.assertArrayEquals(new Object[] { "foo" }, arguments);
	}
	
	@Test
	public void testAddFile() throws Exception {
		File testFile = File.createTempFile("test", "tmp");
		PrintWriter printWriter = new PrintWriter(testFile);
		printWriter.write(TEST_CONFIGURATION_STRING);
		printWriter.close();
		Assert.assertSame(configuration, configuration.addFile(testFile));
		Assert.assertEquals("addFile", methodName);
		Assert.assertArrayEquals(new Object[] { testFile }, arguments);
	}
	
	@Test
	public void testSetProperty() {
		configuration.setProperty("foo", "bar");
		Assert.assertEquals("setProperty", methodName);
		Assert.assertArrayEquals(new Object[] { "foo", "bar" },  arguments);
		Assert.assertEquals("bar", configuration.getProperty("foo"));
	}
	
	@Test 
	public void testSetProperties() {
		Properties testProperties = new Properties();
		Assert.assertSame(configuration, configuration.setProperties(testProperties));
		Assert.assertEquals("setProperties", methodName);
		Assert.assertArrayEquals(new Object[] { testProperties }, arguments);
		Assert.assertSame(testProperties, configuration.getProperties());
	}
	
	@Test
	public void testSetEntityResolver() {
		EntityResolver testResolver = new DefaultHandler();
		configuration.setEntityResolver(testResolver);
		Assert.assertEquals("setEntityResolver", methodName);
		Assert.assertArrayEquals(new Object[] { testResolver }, arguments);
		Assert.assertSame(testResolver, configuration.getEntityResolver());
	}
	
	@Test
	public void testGetEntityResolver() {
		EntityResolver testResolver = new DefaultHandler();
		configuration.setEntityResolver(testResolver);
		reset();
		Assert.assertSame(testResolver, configuration.getEntityResolver());
		Assert.assertEquals("getEntityResolver", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testGetProperties() {
		Properties testProperties = new Properties();
		configuration.setProperties(testProperties);
		reset();
		Assert.assertSame(testProperties, configuration.getProperties());
		Assert.assertEquals("getProperties", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testSetNamingStrategy() {
		NamingStrategy dns = new DefaultNamingStrategy();
		INamingStrategy namingStrategy = new AbstractNamingStrategyFacade(FACADE_FACTORY, dns) {};
		configuration.setNamingStrategy(namingStrategy);
		Assert.assertEquals("setNamingStrategy", methodName);
		Assert.assertArrayEquals(new Object[] { dns }, arguments);
		Assert.assertSame(namingStrategy, configuration.getNamingStrategy());
	}
	
	@Test
	public void testAddProperties() {
		Assert.assertNull(configuration.getProperty("foo"));
		Properties testProperties = new Properties();
		testProperties.put("foo", "bar");
		reset();
		configuration.addProperties(testProperties);
		Assert.assertEquals("addProperties", methodName);
		Assert.assertArrayEquals(new Object[] { testProperties }, arguments);
		Assert.assertEquals("bar", configuration.getProperty("foo"));
	}
	
	@Test
	public void testConfigure() throws Throwable {
		configuration.configure();
		Assert.assertEquals("configure", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
		reset();
		Document testDocument = DocumentBuilderFactory
				.newInstance()
				.newDocumentBuilder()
				.newDocument();
		Element root = testDocument.createElement("hibernate-configuration");
		testDocument.appendChild(root);
		Element child = testDocument.createElement("session-factory");
		root.appendChild(child);
		configuration.configure(testDocument);
		Assert.assertEquals("configure", methodName);
		Assert.assertArrayEquals(new Object[] { testDocument }, arguments);
		reset();
		File testFile = File.createTempFile("test", "tmp");
		PrintWriter printWriter = new PrintWriter(testFile);
		printWriter.write(TEST_CONFIGURATION_STRING);
		printWriter.close();
		configuration.configure(testFile);
		Assert.assertEquals("configure", methodName);
		Assert.assertArrayEquals(new Object[] { testFile }, arguments);
	}
	
	@Test 
	public void testCreateMappings() {
		Assert.assertNotNull(configuration.createMappings());
		Assert.assertEquals("createMappings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testBuildMappings() {
		configuration.buildMappings();
		Assert.assertEquals("buildMappings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testBuildSessionFactory() throws Throwable {
		// "hibernate.dialect" must be set when no Connection available"
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		reset();
		Assert.assertNotNull(configuration.buildSessionFactory());
		Assert.assertEquals("buildSessionFactory", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testBuildSettings() {
		// "hibernate.dialect" must be set when no Connection available"
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		reset();
		Assert.assertNotNull(configuration.buildSettings());
		Assert.assertEquals("buildSettings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testGetClassMappings() {
		Assert.assertNotNull(configuration.getClassMappings());
		Assert.assertEquals("getClassMappings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testSetPreferBasicCompositeIds() {
		configuration.setPreferBasicCompositeIds(true);
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
		JDBCMetaDataConfiguration jdbcMetaDataConfiguration = new JDBCMetaDataConfiguration();
		configuration = new AbstractConfigurationFacade(FACADE_FACTORY, jdbcMetaDataConfiguration) {};
		configuration.setPreferBasicCompositeIds(false);
		Assert.assertFalse(jdbcMetaDataConfiguration.preferBasicCompositeIds());
		configuration.setPreferBasicCompositeIds(true);
		Assert.assertTrue(jdbcMetaDataConfiguration.preferBasicCompositeIds());
	}
	
	@Test
	public void testSetReverseEngineeringStrategy() {
		ReverseEngineeringStrategy res = new DefaultReverseEngineeringStrategy();
		IReverseEngineeringStrategy strategy = 
				new AbstractReverseEngineeringStrategyFacade(FACADE_FACTORY, res) {};
		configuration.setReverseEngineeringStrategy(strategy);
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
		JDBCMetaDataConfiguration jdbcMetaDataConfiguration = new JDBCMetaDataConfiguration();
		configuration = new AbstractConfigurationFacade(FACADE_FACTORY, jdbcMetaDataConfiguration) {};
		Assert.assertNotSame(res, jdbcMetaDataConfiguration.getReverseEngineeringStrategy());
		configuration.setReverseEngineeringStrategy(strategy);
		Assert.assertSame(res, jdbcMetaDataConfiguration.getReverseEngineeringStrategy());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testReadFromJDBC() {
		configuration.readFromJDBC();
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
		Configuration target = new JDBCMetaDataConfiguration() {
			@Override public void readFromJDBC() {
				methodName = "readFromJDBC";
				arguments = new Object[] {};
			}
		};
		configuration = new AbstractConfigurationFacade(FACADE_FACTORY, target) {};
		configuration.readFromJDBC();
		Assert.assertEquals("readFromJDBC", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testBuildMapping() {
		IMapping mapping = configuration.buildMapping();
		Assert.assertNotNull(mapping);
		Assert.assertEquals("buildMapping", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
		reset();
		Assert.assertSame(mapping, configuration.buildMapping());
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
	}
	
	@Test
	public void testGetClassMapping() {
		Assert.assertNull(configuration.getClassMapping("blah"));
		Assert.assertEquals("getClassMappings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
		reset();
		Assert.assertNull(configuration.getClassMapping("blah"));
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
	}
	
	@Test
	public void testGetNamingStrategy() {
		Assert.assertNotNull(configuration.getNamingStrategy());
		Assert.assertEquals("getNamingStrategy", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
		reset();
		Assert.assertNotNull(configuration.getNamingStrategy());
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
	}
		
	@SuppressWarnings("serial")
	@Test
	public void testGetTableMappings() {
		Assert.assertNull(configuration.getTableMappings());
		Assert.assertNull(methodName);
		Assert.assertNull(arguments);
		JDBCMetaDataConfiguration jdbcMetaDataConfiguration = new JDBCMetaDataConfiguration() {
			@Override public Iterator<Table> getTableMappings() {
				methodName = "getTableMappings";
				arguments = new Object[] {};
				return super.getTableMappings();
			}
		};
		configuration = new AbstractConfigurationFacade(FACADE_FACTORY, jdbcMetaDataConfiguration) {};
		Assert.assertNotNull(configuration.getTableMappings());
		Assert.assertEquals("getTableMappings", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
	@Test
	public void testGetDialect() {
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		Assert.assertNotNull(configuration.getDialect());
	}
	
	private void reset() {
		methodName = null;
		arguments = null;
	}

}
