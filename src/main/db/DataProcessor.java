package main.db;

public interface DataProcessor {
    void process(Object o, int row, int column);
}
