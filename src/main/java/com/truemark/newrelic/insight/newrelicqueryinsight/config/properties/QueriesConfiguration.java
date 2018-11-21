package com.truemark.newrelic.insight.newrelicqueryinsight.config.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilip S Sisodia
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "db")
@Slf4j
public class QueriesConfiguration {
  private List<Queries> queries = new ArrayList<>();
}
