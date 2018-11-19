package com.truemark.newrelic.insight.newrelicqueryinsight.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service to handle functionality to post data to Insights.
 *
 * @author Dilip S Sisodia
 */
public interface InsightsService {

  /**
   * Posts the list of events to the Insight Collector.
   *
   * @param events list of events
   * @param eventType insight event type
   * @return true if successful
   * @throws IOException in case of an error
   */
  boolean sendEvents(List<Map<String, Object>> events, String eventType) throws IOException;
}
