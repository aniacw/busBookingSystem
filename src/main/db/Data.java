package main.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Data implements Iterable<Data.Row> {
    public static class Row {
        private Object[] data;

        private Row(int size) {
            this.data = new Object[size];
        }

        private Row(Object[] data) {
            this.data = data;
        }

        public Object get(int i) {
            return data[i - 1];
        }
    }

    private ArrayList<Row> data;
    private String[] columnNames;
    private int columns;

    private void initialize(ResultSet resultSet) throws SQLException {
        ResultSetMetaData meta = resultSet.getMetaData();
        columns = meta.getColumnCount();
        columnNames = new String[columns];
        for (int i = 0; i < columns; ++i)
            columnNames[i] = meta.getColumnName(i + 1);
        while (resultSet.next()) {
            Row row = new Row(columns); //row odpowiada za 1 rzad
            for (int i = 0; i < columns; ++i)
                row.data[i] = resultSet.getObject(i + 1);
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
//        Object[] r = data.get(row-1);
//        return (T)r[column-1];

        return (T) data.get(row - 1).data[column - 1];
    }

    public <T> T getFromTopRow(int column) {
        return (T) data.get(0).data[column - 1];
    }

    public Row getRow(int row) {
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
            return (T) data.get(0).data[0];
        else
            return null;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<Row> iterator() {
        return data.iterator();
    }


    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getColumnIndex(String columnName) throws IllegalArgumentException {
        for (int i = 0; i < columns; ++i) {
            if (columnNames[i].equalsIgnoreCase(columnName))
                return i + 1;
        }
        throw new IllegalArgumentException("Column not found");
    }
}
