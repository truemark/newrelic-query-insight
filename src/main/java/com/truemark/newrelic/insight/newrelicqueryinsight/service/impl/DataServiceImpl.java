package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.Queries;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.DatasourceContainer;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.CustomDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.DataService;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.InsightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service to get data from datasource.
 *
 * @author Dilip S Sisodia
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

  @Autowired
  private InsightsService insightsService;

  @Autowired
  private DatasourceContainer container;

  public void fetchAndPostDataToInsight(String datasourceToQuery,
                                        List<Queries> queries) {
    fetchData(datasourceToQuery, queries);
  }

  public void fetchData(String datasourceName, List<Queries> queries) {
    for (Queries query : queries) {
      CustomDao dao = container.getCustomDao(datasourceName);
      postDataToInsight(dao.getQueryResult(query.getSql()), query.getInsightEventType());
    }
  }

  public void postDataToInsight(List<Map<String, Object>> data, String eventType) {
    try {
      insightsService.sendEvents(data, eventType);
    } catch (IOException e) {
      log.error("Error sending data to insight: " + e.getMessage());
    }
  }
}
