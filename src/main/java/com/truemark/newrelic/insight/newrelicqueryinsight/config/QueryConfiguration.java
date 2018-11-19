package com.truemark.newrelic.insight.newrelicqueryinsight.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dilip S Sisodia
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryConfiguration {
    private String sql;
    private String datasourceType;
    private String insightEventType;
}
