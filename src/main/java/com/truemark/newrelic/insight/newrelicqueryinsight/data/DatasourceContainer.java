package com.truemark.newrelic.insight.newrelicqueryinsight.data;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.DatasourceName;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.CustomDao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 * @author Dilip S Sisodia
 */
public class DatasourceContainer {

  private Map<String, DataSource> runtimeDatasources = new HashMap<>();
  private Map<String, CustomDao> runtimeCustomDao = new HashMap<>();
  private RuntimeDatasourceFactory datasourceFactory;

  public void setRuntimeDatasourceFactory(RuntimeDatasourceFactory datasourceFactory) {
    this.datasourceFactory = datasourceFactory;
  }

  public DataSource createDatasource(String datasourceName) {
    if (runtimeDatasources.containsKey(datasourceName)) {
      return runtimeDatasources.get(datasourceName);
    } else {
      DataSource dataSource = datasourceFactory.getDatasource(datasourceName);
      runtimeDatasources.put(datasourceName, dataSource);

      return dataSource;
    }
  }

  public CustomDao getCustomDao(String datasourceName) {
    if (!runtimeCustomDao.containsKey(datasourceName)) {
      DatasourceName dsName = new DatasourceName();
      dsName.setName(datasourceName);
      CustomDao dao = new CustomDao(dsName, this);
      runtimeCustomDao.put(datasourceName, dao);
    }
    return runtimeCustomDao.get(datasourceName);
  }

}
