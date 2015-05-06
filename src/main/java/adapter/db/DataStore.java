package adapter.db;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15-2-20.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */


public class DataStore {

  private final ConnectionProvider connectionProvider;

  @Inject
  public DataStore(ConnectionProvider connectionProvider) {

    this.connectionProvider = connectionProvider;
  }


  public void execute(String... queries) {

    Connection connection = connectionProvider.getConnection();

    Statement statement;
    try {

      statement = connection.createStatement();
      for (String query : queries) {
        statement.execute(query);
      }

      statement.close();
//        connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void executeQery(String query, Object... objects) {

    if (!haveCorrectParameters(query, objects)) {

      throw new IncorrectMethodParamsException();
    } else {

      Connection connection = connectionProvider.getConnection();

      PreparedStatement statement;
      int index = 0;
      try {

        statement = connection.prepareStatement(query);
        for (Object object : objects) {
          statement.setObject(++index, object);
        }

        statement.execute();

        statement.close();
//        connection.close();


      } catch (IncorrectMethodParamsException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  public Integer executeCount(String query) {
    Connection connection = connectionProvider.getConnection();
    Statement statement;
    ResultSet resultSet = null;
    Integer result = 0;
    try {

      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);
      resultSet.next();
      result = resultSet.getInt(1);

      statement.close();
//        connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }


  private boolean haveCorrectParameters(String query, Object[] objects) {

    int counter = 0;

    for (char s : query.toCharArray()) {
      if (s == '?') {
        counter++;
      }
    }

    return counter == objects.length;
  }


  public <T> List<T> fetchRows(String query, RowFetcher<T> fetcher) {

    Connection connection = connectionProvider.getConnection();

    List<T> result = new ArrayList<T>();
    try {

      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        T rowItem = fetcher.fetchRow(rs);
        result.add(rowItem);
      }

      stmt.close();
//      connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public <T> T fetchRow(String query, RowFetcher<T> fetcher) {

    Connection connection = connectionProvider.getConnection();

    T rowItem = null;

    try {

      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);

        rs.next();
        rowItem = fetcher.fetchRow(rs);


      stmt.close();
//      connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return /*result*/rowItem;
  }

  private class IncorrectMethodParamsException extends RuntimeException {
  }

}
