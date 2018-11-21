package com.truemark.newrelic.insight.newrelicqueryinsight.data.dao;

import com.truemark.newrelic.insight.newrelicqueryinsight.data.DatasourceContainer;
import com.truemark.newrelic.insight.newrelicqueryinsight.config.properties.DatasourceName;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.CustomResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Dilip S Sisodia
 */
@Transactional(readOnly = true)
public class CustomDao extends JdbcDaoSupport {

  public CustomDao(DatasourceName datasourceName, DatasourceContainer container) {
    setDataSource(container.createDatasource(datasourceName.getName()));
  }

  public List<Map<String, Object>> getQueryResult(String query) {
    return getJdbcTemplate().query(query, new CustomResultSetExtractor());
  }
}
