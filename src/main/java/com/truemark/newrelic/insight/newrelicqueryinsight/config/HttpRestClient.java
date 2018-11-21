package com.truemark.newrelic.insight.newrelicqueryinsight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Rest client configuration.
 *
 * @author Dilip S Sisodia
 */
@Configuration
public class HttpRestClient {

  private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

  private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

  @Value("${newrelic.inights-insert-key}")
  private String insertKey;

  @Value("${newrelic.insights-insert-url}")
  private String insertUrl;

  @Bean(name = "insightInsertClient")
  public WebClient insightInsertClient() {

    WebClient webClient = WebClient
        .builder()
        .baseUrl(insertUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("X-Insert-Key", insertKey)
        .build();

    return webClient;
  }

}
