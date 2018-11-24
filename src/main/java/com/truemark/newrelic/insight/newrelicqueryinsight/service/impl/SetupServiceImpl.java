package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.ApplicationDatasource;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.DatasourcesConfiguration;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.Queries;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.QueriesConfiguration;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.DataService;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.SetupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

/**
 * Setup service for scheduling the polling of data.
 *
 * @author Dilip S Sisodia
 */
@Service
@Slf4j
public class SetupServiceImpl implements SetupService {

  @Autowired
  private QueriesConfiguration queries;

  @Autowired
  private DataService dataService;

  @Autowired
  private DatasourcesConfiguration datasourcesConfiguration;

  @PostConstruct
  public void start() {
    List<ApplicationDatasource> datasources = datasourcesConfiguration.getDatasources();
    for (ApplicationDatasource ads : datasources) {
      if (ads.isEnabled()) {
        List<Queries> queries = getQueriesByDatasource(ads.getName().toLowerCase());
        if (queries != null) {
          dataService.fetchAndPostDataToInsight(ads.getName(), queries);
        }
      }
    }
  }

  @Override
  public List<Queries> getQueriesByDatasource(String datasource) {
    try {
      return queries.getQueries().stream().filter(qc ->
          qc.getDatasourceName().equalsIgnoreCase(datasource)).collect(Collectors.toList());
    } catch (Exception ex) {
      log.error("There is some error with your queries in the configuration. "
          + "Please check the sql, datasourceName and insightEventType for queries.");
    }
    return null;
  }
}
