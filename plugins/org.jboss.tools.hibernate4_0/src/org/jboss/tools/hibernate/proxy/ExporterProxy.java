package org.jboss.tools.hibernate.proxy;

import java.io.File;
import java.util.Properties;

import org.hibernate.console.HibernateConsoleRuntimeException;
import org.hibernate.console.spi.IConfiguration;
import org.hibernate.console.spi.IExporter;
import org.hibernate.tool.hbm2x.ArtifactCollector;
import org.hibernate.tool.hbm2x.Exporter;
import org.hibernate.tool.hbm2x.GenericExporter;
import org.hibernate.util.xpl.ReflectHelper;

public class ExporterProxy implements IExporter {
	
	private Exporter target;
	
	public ExporterProxy(String exporterClassName) {
		target = createTarget(exporterClassName);
	}

	@Override
	public void setConfiguration(IConfiguration configuration) {
		if (configuration instanceof ConfigurationProxy) {
			target.setConfiguration(((ConfigurationProxy)configuration).getConfiguration());
		}
	}
	
	private Exporter createTarget(String exporterClassName) {
		Exporter result = null;
		try {
			result = (Exporter) ReflectHelper.classForName(exporterClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new HibernateConsoleRuntimeException(e);
		}
		return result;
	}

	@Override
	public void setProperties(Properties properties) {
		target.setProperties(properties);
	}

	@Override
	public void setArtifactCollector(ArtifactCollector collector) {
		target.setArtifactCollector(collector);
	}

	@Override
	public void setOutputDirectory(File file) {
		target.setOutputDirectory(file);
	}

	@Override
	public void setTemplatePath(String[] strings) {
		target.setTemplatePath(strings);
	}

	@Override
	public void start() {
		target.start();
	}

	@Override
	public Properties getProperties() {
		return target.getProperties();
	}

	@Override
	public GenericExporter getGenericExporter() {
		GenericExporter result = null;
		if (target instanceof GenericExporter) {
			result = (GenericExporter)target;
		}
		return result;
	}

}
