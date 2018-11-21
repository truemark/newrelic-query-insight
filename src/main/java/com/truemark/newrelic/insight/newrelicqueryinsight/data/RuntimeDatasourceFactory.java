package com.truemark.newrelic.insight.newrelicqueryinsight.data;

import javax.sql.DataSource;

/**
 * Contract for RuntimeDatasourceFactory.
 *
 * @author Dilip S Sisodia
 */
public interface RuntimeDatasourceFactory {
  public DataSource getDatasource(String datasourceName);
}
