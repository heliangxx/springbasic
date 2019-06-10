
package com.pactera.common.database;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}

	public DataSource getDataSource(String dsId) {
		DynamicDataSourceContextHolder.setDataSourceType(dsId);
		DataSource tempsource = determineTargetDataSource();
		DynamicDataSourceContextHolder.clearDataSourceType();
		return tempsource;
	}
}
