package com.truemark.newrelic.insight.newrelicqueryinsight.service;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.Queries;

import java.util.List;

/**
 * Contract for SetupService.
 *
 * @author Dilip S Sisodia
 */
public interface SetupService {
  List<Queries> getQueriesByDatasource(String datasource);
}
