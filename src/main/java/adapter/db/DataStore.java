package adapter.db;


import com.google.inject.Inject;
import com.google.inject.Provider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15-2-20.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */


public class DataStore {


    private final Provider<Connection> connectionProvider;

    @Inject
    public DataStore(Provider<Connection> connectionProvider) {

        this.connectionProvider = connectionProvider;
    }


    public void execute(String... queries) {

        Connection connection = connectionProvider.get();
        Statement statement;
        try {

            statement = connection.createStatement();
            for (String query : queries) {
                statement.execute(query);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executeQuery(String query, Object... objects) {

        Connection connection = connectionProvider.get();

        PreparedStatement statement;
        int index = 0;
        try {

            statement = connection.prepareStatement(query);
            for (Object object : objects) {
                statement.setObject(++index, object);
            }

            statement.execute();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Integer executeCount(String query) {
        Connection connection = connectionProvider.get();

        Statement statement;
        ResultSet resultSet;
        Integer result = 0;
        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            result = resultSet.getInt(1);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public <T> List<T> fetchRows(String query, RowFetcher<T> fetcher) {

        Connection connection = connectionProvider.get();

        List<T> result = new ArrayList<T>();
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                T rowItem = fetcher.fetchRow(rs);
                result.add(rowItem);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public <T> T fetchRow(String query, RowFetcher<T> fetcher) {

        Connection connection = connectionProvider.get();

        T rowItem = null;

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowItem = fetcher.fetchRow(rs);


            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowItem;
    }

}
