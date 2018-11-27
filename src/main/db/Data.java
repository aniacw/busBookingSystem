package main.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Data implements Iterable<Object[]> {
    private ArrayList<Object[]> data;
    private int columns;

    private void initialize(ResultSet resultSet) throws SQLException {
        columns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            Object[] row = new Object[columns]; //row odpowiada za 1 rzad
            for (int i = 0; i < columns; ++i)
                row[i] = resultSet.getObject(i + 1);
            data.add(row);
        }
    }

    public Data() {
        data = new ArrayList<>();
    }

    public Data(ResultSet resultSet) throws SQLException {
        data = new ArrayList<>();
        initialize(resultSet);
    }

    public <T> T get(int row, int column) {
        return (T) data.get(row - 1)[column - 1];
    }

    public <T> T getFromTopRow(int column) {
        return (T) data.get(0)[column - 1];
    }

    public Object[] getRow(int row) {
        return data.get(row - 1);
    }

    public int getColumnCount() {
        return columns;
    }

    public int getRowCount() {
        return data.size();
    }

    public <T> T asSingleObject() {
        if (columns == 1 && data.size() == 1)
            return (T) data.get(0)[0];
        else
            return null;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<Object[]> iterator() {
        return data.iterator();
    }


    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
