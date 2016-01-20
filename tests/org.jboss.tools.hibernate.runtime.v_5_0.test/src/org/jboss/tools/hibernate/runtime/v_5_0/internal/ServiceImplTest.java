package org.jboss.tools.hibernate.runtime.v_5_0.internal;

import java.io.File;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.JoinedSubclass;
import org.hibernate.mapping.ManyToOne;
import org.hibernate.mapping.Map;
import org.hibernate.mapping.OneToMany;
import org.hibernate.mapping.OneToOne;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Set;
import org.hibernate.mapping.SingleTableSubclass;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2x.HibernateMappingGlobalSettings;
import org.jboss.tools.hibernate.runtime.common.IFacade;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.jboss.tools.hibernate.runtime.spi.IHQLCodeAssist;
import org.jboss.tools.hibernate.runtime.spi.IHibernateMappingExporter;
import org.jboss.tools.hibernate.runtime.spi.IHibernateMappingGlobalSettings;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IPrimaryKey;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.ISchemaExport;
import org.jboss.tools.hibernate.runtime.spi.ITable;
import org.jboss.tools.hibernate.runtime.spi.ITableIdentifier;
import org.jboss.tools.hibernate.runtime.spi.IValue;
import org.junit.Assert;
import org.junit.Test;

public class ServiceImplTest {
	
	private ServiceImpl service = new ServiceImpl();
	
	@Test
	public void testServiceCreation() {
		Assert.assertNotNull(service);
	}
	
	@Test
	public void testNewAnnotationConfiguration() {
		IConfiguration configuration = service.newAnnotationConfiguration();
		Assert.assertNotNull(configuration);
		Object target = ((IFacade)configuration).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Configuration);
	}
	
	@Test
	public void testNewJpaConfiguration() {
		IConfiguration configuration = service.newJpaConfiguration(null, "test", null);
		Assert.assertNotNull(configuration);
		Object target = ((IFacade)configuration).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Configuration);
	}
	
	@Test
	public void testNewDefaultConfiguration() {
		IConfiguration configuration = service.newDefaultConfiguration();
		Assert.assertNotNull(configuration);
		Object target = ((IFacade)configuration).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Configuration);
	}
	
	@Test
	public void testNewHibernateMappingExporter() {
		IConfiguration configuration = service.newDefaultConfiguration();
		File file = new File("");
		IHibernateMappingExporter hibernateMappingExporter = 
				service.newHibernateMappingExporter(configuration, file);
		Configuration cfg = (Configuration)((IFacade)configuration).getTarget();
		HibernateMappingExporterExtension hmee = 
				(HibernateMappingExporterExtension)((IFacade)hibernateMappingExporter).getTarget();
		Assert.assertSame(file, hmee.getOutputDirectory());
		Assert.assertSame(cfg, hmee.getConfiguration());
	}
	
	@Test
	public void testNewSchemaExport() {
		IConfiguration configuration = service.newDefaultConfiguration();
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		ISchemaExport schemaExport = service.newSchemaExport(configuration);
		Assert.assertNotNull(schemaExport);
	}
	
	@Test
	public void testNewHQLCodeAssist() {
		IConfiguration configuration = service.newDefaultConfiguration();
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		IHQLCodeAssist hqlCodeAssist = service.newHQLCodeAssist(configuration);
		Assert.assertNotNull(hqlCodeAssist);
	}
	
	@Test
	public void testNewProperty() {
		IProperty property = service.newProperty();
		Assert.assertNotNull(property);
		Object target = ((IFacade)property).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Property);
	}
	
	@Test
	public void testNewTable() {
		ITable table = service.newTable("foo");
		Assert.assertNotNull(table);
		Object target = ((IFacade)table).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Table);
		Assert.assertEquals("foo", ((Table)target).getName());
	}
	
	@Test
	public void testNewMap() {
		IPersistentClass persistentClass = service.newRootClass();
		IValue map = service.newMap(persistentClass);
		Assert.assertNotNull(map);
		Object target = ((IFacade)map).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Map);
	}
	
	@Test
	public void testNewSet() {
		IPersistentClass persistentClass = service.newRootClass();
		IValue set = service.newSet(persistentClass);
		Assert.assertNotNull(set);
		Object target = ((IFacade)set).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof Set);
	}
	
	@Test
	public void testNewManyToOne() {
		ITable table = service.newTable("foo");
		IValue manyToOne = service.newManyToOne(table);
		Assert.assertNotNull(manyToOne);
		Object target = ((IFacade)manyToOne).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof ManyToOne);
	}
	
	@Test
	public void testNewOneToMany() {
		IPersistentClass persistentClass = service.newRootClass();
		IValue oneToMany = service.newOneToMany(persistentClass);
		Assert.assertNotNull(oneToMany);
		Object target = ((IFacade)oneToMany).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof OneToMany);
	}
	
	@Test
	public void testNewOneToOne() {
		IPersistentClass persistentClass = service.newRootClass();
		IValue oneToOne = service.newOneToOne(persistentClass);
		Assert.assertNotNull(oneToOne);
		Object target = ((IFacade)oneToOne).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof OneToOne);
	}
	
	@Test
	public void testNewSingleTableSubclass() {
		IPersistentClass persistentClass = service.newRootClass();
		IPersistentClass singleTableSublass = service.newSingleTableSubclass(persistentClass);
		Assert.assertNotNull(singleTableSublass);
		Object target = ((IFacade)singleTableSublass).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof SingleTableSubclass);
		Assert.assertSame(persistentClass, singleTableSublass.getSuperclass());
		Assert.assertSame(
				((IFacade)persistentClass).getTarget(), 
				((SingleTableSubclass)target).getSuperclass());
	}
	
	@Test
	public void testNewJoinedSubclass() {
		IPersistentClass persistentClass = service.newRootClass();
		IPersistentClass joinedSubclass = service.newJoinedSubclass(persistentClass);
		Assert.assertNotNull(joinedSubclass);
		Object target = ((IFacade)joinedSubclass).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof JoinedSubclass);
		Assert.assertSame(persistentClass, joinedSubclass.getSuperclass());
		Assert.assertSame(
				((IFacade)persistentClass).getTarget(), 
				((JoinedSubclass)target).getSuperclass());
	}
	
	@Test
	public void testNewSpecialRootClass() {
		IProperty property = service.newProperty();
		IPersistentClass specialRootClass = service.newSpecialRootClass(property);
		Assert.assertNotNull(specialRootClass);
		Object target = ((IFacade)specialRootClass).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof RootClass);
		Assert.assertSame(property, specialRootClass.getProperty());
	}
	
	@Test
	public void testNewRootClass() {
		IPersistentClass rootClass = service.newRootClass();
		Assert.assertNotNull(rootClass);
		Object target = ((IFacade)rootClass).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof RootClass);
	}
	
	@Test
	public void testNewPrimaryKey() {
		IPrimaryKey primaryKey = service.newPrimaryKey();
		Assert.assertNotNull(primaryKey);
		Object target = ((IFacade)primaryKey).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof PrimaryKey);
	}
	
	@Test
	public void testNewHibernateMappingGlobalSettings() {
		IHibernateMappingGlobalSettings hibernateMappingGlobalSettings =
				service.newHibernateMappingGlobalSettings();
		Assert.assertNotNull(hibernateMappingGlobalSettings);
		Object target = ((IFacade)hibernateMappingGlobalSettings).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof HibernateMappingGlobalSettings);
	}
	
	@Test
	public void testCreateTableIdentifier() {
		ITable table = service.newTable("foo");
		ITableIdentifier tableIdentifier = service.createTableIdentifier(table);
		Assert.assertNotNull(tableIdentifier);
		Object target = ((IFacade)tableIdentifier).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof TableIdentifier);
	}
	
	@Test
	public void testNewTableIdentifier() {
		ITableIdentifier tableIdentifier = service.newTableIdentifier(
				"catalog", "schema", "typeName");
		Assert.assertNotNull(tableIdentifier);
		Object target = ((IFacade)tableIdentifier).getTarget();
		Assert.assertNotNull(target);
		Assert.assertTrue(target instanceof TableIdentifier);
	}
	
	@Test
	public void testIsInitialized() {
		Assert.assertTrue(service.isInitialized(new Object()));
	}
	
	@Test
	public void testGetJPAMappingFilePaths() {
		List<String> result = service.getJPAMappingFilePaths("test", null);
		Assert.assertEquals(0, result.size());
	}
	
	@Test
	public void testGetClassWithoutInitializingProxy() {
		Assert.assertSame(
				Object.class, 
				service.getClassWithoutInitializingProxy(new Object()));
	}
	
	@Test
	public void testGetClassLoader(){
		Assert.assertSame(
				ServiceImpl.class.getClassLoader(), 
				service.getClassLoader());
	}
	
}