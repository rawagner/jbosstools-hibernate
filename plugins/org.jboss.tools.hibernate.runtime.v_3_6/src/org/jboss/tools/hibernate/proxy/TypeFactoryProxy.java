package org.jboss.tools.hibernate.proxy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.jboss.tools.hibernate.runtime.common.AbstractTypeFactoryFacade;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.ITable;
import org.jboss.tools.hibernate.runtime.spi.IType;

public class TypeFactoryProxy extends AbstractTypeFactoryFacade {
	
	private Map<IType, String> typeFormats = null;
	private BasicTypeRegistry typeRegistry = new BasicTypeRegistry();

	public TypeFactoryProxy(IFacadeFactory facadeFactory, Object target) {
		super(facadeFactory, target);
	}

	@Override
	public IType getBooleanType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.BOOLEAN);
	}

	@Override
	public IType getByteType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.BYTE);
	}

	@Override
	public IType getBigIntegerType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.BIG_INTEGER);
	}

	@Override
	public IType getShortType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.SHORT);
	}

	@Override
	public IType getCalendarType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.CALENDAR);
	}

	@Override
	public IType getCalendarDateType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.CALENDAR_DATE);
	}

	@Override
	public IType getIntegerType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.INTEGER);
	}

	@Override
	public IType getBigDecimalType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.BIG_DECIMAL);
	}

	@Override
	public IType getCharacterType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.CHARACTER);
	}

	@Override
	public IType getClassType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.CLASS);
	}

	@Override
	public IType getCurrencyType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.CURRENCY);
	}

	@Override
	public IType getDateType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.DATE);
	}

	@Override
	public IType getDoubleType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.DOUBLE);
	}

	@Override
	public IType getFloatType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.FLOAT);
	}

	@Override
	public IType getLocaleType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.LOCALE);
	}

	@Override
	public IType getLongType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.LONG);
	}

	@Override
	public IType getStringType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.STRING);
	}

	@Override
	public IType getTextType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.TEXT);
	}

	@Override
	public IType getTimeType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.TIME);
	}

	@Override
	public IType getTimestampType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.TIMESTAMP);
	}

	@Override
	public IType getTimezoneType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.TIMEZONE);
	}

	@Override
	public IType getTrueFalseType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.TRUE_FALSE);
	}

	@Override
	public IType getYesNoType() {
		return new TypeProxy(getFacadeFactory(), StandardBasicTypes.YES_NO);
	}

	@Override
	public IType getNamedType(String typeName) {
		return new TypeProxy(getFacadeFactory(), typeRegistry.getRegisteredType(typeName));
	}

	@Override
	public IType getBasicType(String typeName) {
		return getNamedType(typeName);
	}

	@Override
	public Map<IType, String> getTypeFormats() {
		if (typeFormats == null) {
			initializeTypeFormats();
		}
		return typeFormats;
	}
	
	private void initializeTypeFormats() {
		typeFormats = new HashMap<IType, String>();		
		addTypeFormat(getBooleanType(), Boolean.TRUE);
		addTypeFormat(getByteType(), Byte.valueOf((byte) 42));
		addTypeFormat(getBigIntegerType(), BigInteger.valueOf(42));
		addTypeFormat(getShortType(), Short.valueOf((short) 42));
		addTypeFormat(getCalendarType(), new GregorianCalendar());
		addTypeFormat(getCalendarDateType(), new GregorianCalendar());
		addTypeFormat(getIntegerType(), Integer.valueOf(42));
		addTypeFormat(getBigDecimalType(), new BigDecimal(42.0));
		addTypeFormat(getCharacterType(), Character.valueOf('h'));
		addTypeFormat(getClassType(), ITable.class);
		addTypeFormat(getCurrencyType(), Currency.getInstance(Locale.getDefault()));
		addTypeFormat(getDateType(), new Date());
		addTypeFormat(getDoubleType(), Double.valueOf(42.42));
		addTypeFormat(getFloatType(), Float.valueOf((float)42.42));
		addTypeFormat(getLocaleType(), Locale.getDefault());
		addTypeFormat(getLongType(), Long.valueOf(42));
		addTypeFormat(getStringType(), "a string"); //$NON-NLS-1$
		addTypeFormat(getTextType(), "a text"); //$NON-NLS-1$
		addTypeFormat(getTimeType(), new Date());
		addTypeFormat(getTimestampType(), new Date());
		addTypeFormat(getTimezoneType(), TimeZone.getDefault());
		addTypeFormat(getTrueFalseType(), Boolean.TRUE);
		addTypeFormat(getYesNoType(), Boolean.TRUE);
	}
	
	private void addTypeFormat(IType type, Object value) {
		typeFormats.put(type, type.toString(value));
	}
}
