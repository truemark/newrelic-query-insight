package com.truemark.newrelic.insight.newrelicqueryinsight.config.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dilip S Sisodia
 */
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationDatasource {
    private String name;
    private boolean enabled;
    private HikariConfig hikari;
}
