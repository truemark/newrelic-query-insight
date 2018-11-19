package com.truemark.newrelic.insight.newrelicqueryinsight.service.impl;

import com.truemark.newrelic.insight.newrelicqueryinsight.config.QueryConfiguration;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.MySqlDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.OracleDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.PostgreDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.dao.SqlServerDao;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.DataService;
import com.truemark.newrelic.insight.newrelicqueryinsight.service.InsightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Dilip S Sisodia
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

  //  @Autowired
  private MySqlDao mySqlDao;

  //  @Autowired
  private SqlServerDao sqlServerDao;

  //  @Autowired
  private OracleDao oracleDao;

  //  @Autowired
  private PostgreDao postgreDao;

  //  @Autowired
  private InsightsService insightsService;

//  @Autowired
//  private ApplicationContext ctx;

  @Autowired
  public DataServiceImpl(InsightsService insightsService, ApplicationContext ctx) {
    this.insightsService = insightsService;
    try {
      mySqlDao = ctx.getBean(MySqlDao.class);
    } catch (BeanInstantiationException ex) {
      log.error("Not found Bean of type MySqlDao");
    }
    try {
      postgreDao = ctx.getBean(PostgreDao.class);
    } catch (Exception ex) {
      log.error("Not found Bean of type PostgreDao");
    }
    try {
      oracleDao = ctx.getBean(OracleDao.class);
    } catch (Exception ex) {
      log.error("Not found Bean of type OracleDao");
    }
    try {
      sqlServerDao = ctx.getBean(SqlServerDao.class);
    } catch (Exception ex) {
      log.error("Not found Bean of type SqlServerDao");
    }
  }

  public void fetchAndPostDataToInsight(String datasourceToQuery,
                                        List<QueryConfiguration> queries) {
    switch (datasourceToQuery) {
      case "postgre":
        if (postgreDao != null) {
          fetchDataFromPostgre(queries);
        } else {
          log.error("Postgres datasource not available.");
        }
        break;
      case "mysql":
        if (mySqlDao != null) {
          fetchDataFromMySql(queries);
        } else {
          log.error("MySql datasource not available.");
        }
        break;
      case "sqlserver":
        if (sqlServerDao != null) {
          fetchDataFromSqlServer(queries);
        } else {
          log.error("Sql Server datasource not available.");
        }
        break;
      case "oracle":
        if (oracleDao != null) {
          fetchDataFromOracle(queries);
        } else {
          log.error("Oracle datasource not available.");
        }
        break;
      default:
        log.error("No matching datasource found.");
    }
  }

  public void fetchDataFromMySql(List<QueryConfiguration> queries) {
    for (QueryConfiguration query : queries) {
      postDataToInsight(mySqlDao.getQueryResult(query.getSql()), query.getInsightEventType());
    }
  }

  public void fetchDataFromOracle(List<QueryConfiguration> queries) {
    for (QueryConfiguration query : queries) {
      postDataToInsight(oracleDao.getQueryResult(query.getSql()), query.getInsightEventType());
    }
  }

  public void fetchDataFromPostgre(List<QueryConfiguration> queries) {
    for (QueryConfiguration query : queries) {
      postDataToInsight(postgreDao.getQueryResult(query.getSql()), query.getInsightEventType());
    }
  }

  public void fetchDataFromSqlServer(List<QueryConfiguration> queries) {
    for (QueryConfiguration query : queries) {
      postDataToInsight(sqlServerDao.getQueryResult(query.getSql()), query.getInsightEventType());
    }
  }

  public void postDataToInsight(List<Map<String, Object>> data, String eventType) {
    try {
      insightsService.sendEvents(data, eventType);
    } catch (IOException e) {
      log.error("Error sending data to insight: " + e.getMessage());
    }
  }

}
