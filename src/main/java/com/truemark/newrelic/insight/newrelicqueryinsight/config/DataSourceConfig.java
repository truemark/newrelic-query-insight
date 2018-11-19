package com.truemark.newrelic.insight.newrelicqueryinsight.config;

import com.truemark.newrelic.insight.newrelicqueryinsight.condition.DatasourceEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.HashMap;
import javax.sql.DataSource;

/**
 * @author Dilip S Sisodia
 */
@Configuration
@Slf4j
public class DataSourceConfig {

  @Autowired
  private Environment environment;

  @DatasourceEnabled("ORACLE")
  @Bean(name = "oracleDataSource")
  @ConfigurationProperties(prefix = "oracle.datasource")
  public DataSource oracleDataSource() {
    return DataSourceBuilder.create().build();
  }

  @DatasourceEnabled("POSTGRE")
  @Bean(name = "postgreDataSource")
  @ConfigurationProperties(prefix = "postgre.datasource")
  public DataSource postgreDataSource() {
    return DataSourceBuilder.create().build();
  }

  @DatasourceEnabled("SQLSERVER")
  @Bean(name = "sqlServerDataSource")
  @ConfigurationProperties(prefix = "sql-server.datasource")
  public DataSource sqlServerDataSource() {
    return DataSourceBuilder.create().build();
  }

  @DatasourceEnabled("MYSQL")
  @Bean(name = "mysqlDataSource")
  @ConfigurationProperties(prefix = "mysql.datasource")
  public DataSource mysqlDataSource() {
    return DataSourceBuilder.create().build();
  }

  private HashMap<String, Object> getJpaProperties(String datasourceType) {
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto",
        environment.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect",
        environment.getProperty(datasourceType + ".hibernate.dialect"));
    return properties;
  }

}
