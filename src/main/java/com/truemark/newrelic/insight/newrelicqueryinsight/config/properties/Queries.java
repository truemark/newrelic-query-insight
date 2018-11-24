package com.truemark.newrelic.insight.newrelicqueryinsight.config.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Holds data for queries configuration mapped from properties file.
 *
 * @author Dilip S Sisodia
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class Queries {
  private String sql;
  private String datasourceName;
  private String insightEventType;
  private String cronSchedule;
}
