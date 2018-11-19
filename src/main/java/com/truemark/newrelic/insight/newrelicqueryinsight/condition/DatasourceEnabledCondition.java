package com.truemark.newrelic.insight.newrelicqueryinsight.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author Dilip S Sisodia
 */
public class DatasourceEnabledCondition implements Condition {
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
    Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(
        DatasourceEnabled.class.getName());
    boolean enabledDBType = false;
    String datasourceType = (String) attributes.get("value");
    enabledDBType = Boolean.parseBoolean(conditionContext.getEnvironment().getProperty(datasourceType.toLowerCase() + ".enabled"));
    return (datasourceType != null && enabledDBType);
  }
}
