package com.truemark.newrelic.insight.newrelicqueryinsight.data.dao;

import com.truemark.newrelic.insight.newrelicqueryinsight.condition.DatasourceEnabled;
import com.truemark.newrelic.insight.newrelicqueryinsight.data.CustomResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * @author Dilip S Sisodia
 */
@Repository("mysqlDao")
@Transactional(readOnly = true)
@DatasourceEnabled("MYSQL")
public class MySqlDao extends JdbcDaoSupport {

  @Autowired
  public MySqlDao(@Qualifier("mysqlDataSource") DataSource dataSource) {
    setDataSource(dataSource);
  }

  public List<Map<String, Object>> getQueryResult(String query) {
    return getJdbcTemplate().query(query, new CustomResultSetExtractor());
  }
}
