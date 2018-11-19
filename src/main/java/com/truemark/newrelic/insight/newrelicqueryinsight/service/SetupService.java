package com.truemark.newrelic.insight.newrelicqueryinsight.service;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.QueryConfiguration;

import java.util.List;

/**
 * @author Dilip S Sisodia
 */
public interface SetupService {
  List<QueryConfiguration> getQueriesByDatasource(String datasource);
}
