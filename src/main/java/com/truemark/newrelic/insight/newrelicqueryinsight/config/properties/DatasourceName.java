package com.truemark.newrelic.insight.newrelicqueryinsight.config.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Holds datasource name.
 *
 * @author Dilip S Sisodia
 */
@Component
@Data
public class DatasourceName {
  private String name;
}
