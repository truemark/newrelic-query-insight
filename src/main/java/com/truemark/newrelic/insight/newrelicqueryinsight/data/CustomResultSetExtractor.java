package com.truemark.newrelic.insight.newrelicqueryinsight.data;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dilip S Sisodia
 */
public class CustomResultSetExtractor implements ResultSetExtractor<List<Map<String, Object>>> {
  @Nullable
  @Override
  public List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException,
      DataAccessException {
    List<Map<String, Object>> queryData = new ArrayList<>();

    ResultSetMetaData metaData = resultSet.getMetaData();
    while (resultSet.next()) {
      Map<String, Object> dataMap = new HashMap<>();
      for (int i = 1; i <= metaData.getColumnCount(); i++) {
        // use column names as the "key"
        String value = resultSet.getString(i);
        String columnName = metaData.getColumnName(i);
        dataMap.put(columnName, value);
      }
      queryData.add(dataMap);
    }
    return queryData;
  }
}