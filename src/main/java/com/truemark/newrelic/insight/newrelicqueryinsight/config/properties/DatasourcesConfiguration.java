package com.truemark.newrelic.insight.newrelicqueryinsight.config.properties;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.ApplicationDatasource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties mapper for datasources from properties file.
 *
 * @author Dilip S Sisodia
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "datasources")
@Slf4j
public class DatasourcesConfiguration {
  private List<ApplicationDatasource> datasources = new ArrayList<>();
}
