package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class DataFrame {
    private final LinkedHashMap<String, Column> columns;

    DataFrame() {
        this.columns = new LinkedHashMap<>();
    }

    public void addColumn(String name) {
        columns.put(name, new Column(name));
    }

    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }

    public int getRowCount() {
        if (columns.isEmpty())
            return 0;
        return columns.firstEntry().getValue().getSize();
    }

    public String getValue(String name, int row) {
        Column col = columns.get(name);
        if (col != null) {
            return col.getRowValue(row);
        }
        return "";
    }

    public void putValue(String name, int row, String value) {
        Column col = columns.get(name);
        if (col != null) {
            col.setRowValue(row, value);
        }
    }

    public void addValue(String name, String value) {
        Column col = columns.get(name);
        if (col != null) {
            col.addRowValue(value);
        }
    }

    public ArrayList<String> getColumn(String name) {
        Column col = columns.get(name);
        if (col != null) {
                return col.getData();
        }
        return new ArrayList<>();
    }

    public int getRowNumber(String name, String value) {
        Column col = columns.get(name);
        if (col != null) {
            return col.getRowNumber(value);
        }
        return -1;
    }

    public List<Map.Entry<String, String>> getRow(int row) {
        List<Map.Entry<String, String>> row_data = new ArrayList<>();
        for (Column col : columns.values()) {
            row_data.add(Map.entry(col.getName(), col.getRowValue(row)));
        }
        return row_data;
    }

    public List<Integer> searchColumn(String name, String key) {
        if (columns.get(name) == null)
            return new ArrayList<>();
        return columns.get(name).search(key);
    }

    public List<Integer> searchALL(String name, String key) {
        List<Integer> search_res = new ArrayList<>();
        for (Column col : columns.values()) {
            search_res = Stream.concat(search_res.stream(), col.search(key).stream())
                                .distinct()
                                .sorted()
                                .toList();
        }
        return search_res;
    }
}
