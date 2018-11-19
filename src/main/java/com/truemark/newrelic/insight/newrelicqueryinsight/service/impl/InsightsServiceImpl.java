package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.service.InsightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Implements the InsightsService.
 *
 * @author Abhijeet Kale
 * @see InsightsService
 */
@Service
@Slf4j
public class InsightsServiceImpl implements InsightsService {

  @Autowired
  @Qualifier("insightInsertClient")
  private WebClient client;

  @Override
  public boolean sendEvents(List<Map<String, Object>> events, String eventType) throws IOException {

    // Add event type to the messages
    for (Map<String, Object> event : events) {
      event.put("eventType", eventType);
    }

    String response = client.post()
        .body(BodyInserters.fromObject(events))
        .exchange()
        .block()
        .bodyToMono(String.class)
        .doOnSuccess((resposne) -> {
          log.debug("Event sent to insight.");
        }).doOnError((error) -> {
          log.error("Error sending event to insight. " + error.getMessage());
        })
        .block();
    return true;
  }

}
