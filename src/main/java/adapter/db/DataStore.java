package adapter.db;


import com.google.common.collect.Lists;
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
        try {

            Connection connection = connectionProvider.get();
            Statement statement = connection.createStatement();
            for (String query : queries) {
                statement.execute(query);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query, Object... objects) {
        try {

            int index = 0;
            Connection connection = connectionProvider.get();
            PreparedStatement statement = connection.prepareStatement(query);
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

        Integer result=0;
        try {

            Connection connection = connectionProvider.get();
            Statement  statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            result = resultSet.getInt(1);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public <T> List<T> fetchRows(String query, RowFetcher<T> fetcher) {

        List<T> result = Lists.newArrayList();
        try {

            Connection connection = connectionProvider.get();
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

        T rowItem = null;

        try {
            Connection connection = connectionProvider.get();
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
