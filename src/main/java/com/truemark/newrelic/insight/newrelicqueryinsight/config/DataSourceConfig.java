package com.truemark.newrelic.insight.newrelicqueryinsight.config;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.ApplicationDatasource;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.DatasourcesConfiguration;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.DatasourceContainer;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.RuntimeDatasourceFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import javax.sql.DataSource;

/**
 * Datasource configuration.
 *
 * @author Dilip S Sisodia
 */
@Configuration
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
@Slf4j
public class DataSourceConfig {

  @Autowired
  private DatasourcesConfiguration datasourcesConfiguration;

  @Bean
  RuntimeDatasourceFactory runtimeDatasourceFactory() {
    return new RuntimeDatasourceFactory() {
      @Override
      public DataSource getDatasource(String datasourceName) {
        return new HikariDataSource(hikariConfig(datasourceName));
      }
    };
  }

  @Bean(name = "runtimeDatasourceContainer")
  DatasourceContainer container() {
    DatasourceContainer container = new DatasourceContainer();
    container.setRuntimeDatasourceFactory(runtimeDatasourceFactory());
    return container;
  }

  public HikariConfig hikariConfig(String datasourceName) {
    Optional<ApplicationDatasource> datasourceConfiguration =
        datasourcesConfiguration.getDatasources().stream().filter(ds ->
            ds.getName().equals(datasourceName)).findFirst();
    return datasourceConfiguration
        .orElseThrow(() ->
            new BeanCreationException("No datasource with " + "name"
                + datasourceName + "found.")).getHikari();
  }

}
