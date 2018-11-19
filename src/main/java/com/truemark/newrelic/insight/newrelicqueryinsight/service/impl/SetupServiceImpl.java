package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.AvailableQueries;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.QueryConfiguration;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.QueryDatasources;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.DataService;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.SetupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

/**
 * @author Dilip S Sisodia
 */
@Service
@Slf4j
public class SetupServiceImpl implements SetupService {

  @Autowired
  private AvailableQueries queries;

  @Autowired
  private DataService dataService;

  @Autowired
  private QueryDatasources queryDatasources;

  @Autowired
  private Environment environment;

  private List<QueryConfiguration> oracleQueries = new ArrayList<>();
  private List<QueryConfiguration> postgreQueries = new ArrayList<>();
  private List<QueryConfiguration> sqlServerQueries = new ArrayList<>();


  @PostConstruct
  public void setup() {
    oracleQueries = queries.getQueries().stream().filter(qc ->
        qc.getDatasourceType().equals("oracle")).collect(Collectors.toList());
    postgreQueries = queries.getQueries().stream().filter(qc ->
        qc.getDatasourceType().equals("postgre")).collect(Collectors.toList());
    sqlServerQueries = queries.getQueries().stream().filter(qc ->
        qc.getDatasourceType().equals("sqlserver")).collect(Collectors.toList());
  }

  @Scheduled(cron = "${job.insight.cron}")
  public void start() {
    List<String> datasources = queryDatasources.getDatasources();
    for (String qds : datasources) {
      boolean datasourceEnabled = Boolean.parseBoolean(environment.getProperty(qds + ".enabled"));
      if (datasourceEnabled) {
        List<QueryConfiguration> queries = getQueriesByDatasource(qds);
        dataService.fetchAndPostDataToInsight(qds, queries);
      }
    }
  }

  @Override
  public List<QueryConfiguration> getQueriesByDatasource(String datasource) {
    return queries.getQueries().stream().filter(qc ->
        qc.getDatasourceType().equalsIgnoreCase(datasource)).collect(Collectors.toList());
  }
}
