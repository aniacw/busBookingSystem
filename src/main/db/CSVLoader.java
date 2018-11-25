package main.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class CSVLoader {
    private DataBaseManager dataBaseManager;

    public CSVLoader(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public void loadIntoTable(String filename, String tableName) throws SQLException, FileNotFoundException {
        FileInputStream stream = new FileInputStream(filename);
        Scanner scanner = new Scanner(stream);
        String header = scanner.nextLine();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            dataBaseManager.insertValuesIntoNewTable(tableName, header, line);
        }
    }

}