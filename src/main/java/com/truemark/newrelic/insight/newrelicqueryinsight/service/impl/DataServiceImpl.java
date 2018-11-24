package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.Queries;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.DatasourceContainer;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.CustomDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.DataService;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.InsightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    for (Queries query : queries) {
      fetchData(datasourceToQuery, query);
    }
  }

  public void fetchData(String datasourceName, Queries query) {

    CustomDao dao = container.getCustomDao(datasourceName);
    postDataToInsight(dao.getQueryResult(query.getSql()), query.getInsightEventType());

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    CronSequenceGenerator generator = new CronSequenceGenerator(query.getCronSchedule());
    Date nextSchedule = generator.next(new Date());
    long scheduleDelay = nextSchedule.getTime() - new Date().getTime();
    scheduledExecutorService.schedule(new Runnable() {
      @Override
      public void run() {
        log.error("Running query: " + query);
        log.error("Running at: " + new Date());
        fetchData(datasourceName, query);
      }
    }, scheduleDelay, TimeUnit.MILLISECONDS);
    scheduledExecutorService.shutdown();
  }

  public void postDataToInsight(List<Map<String, Object>> data, String eventType) {
    try {
      insightsService.sendEvents(data, eventType);
    } catch (IOException e) {
      log.error("Error sending data to insight: " + e.getMessage());
    }
  }
}
