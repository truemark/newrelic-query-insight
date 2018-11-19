package com.truemark.newrelic.insight.newrelicqueryinsight.service;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.QueryConfiguration;

import java.util.List;

/**
 * @author Dilip S Sisodia
 */
public interface DataService {

  void fetchAndPostDataToInsight(String datasourceToQuery, List<QueryConfiguration> queries);
}
