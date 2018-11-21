package com.truemark.newrelic.insight.newrelicqueryinsight.service;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.Queries;

import java.util.List;

/**
 * @author Dilip S Sisodia
 */
public interface DataService {

  void fetchAndPostDataToInsight(String datasourceToQuery, List<Queries> queries);
}
