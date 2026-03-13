package uk.ac.ucl.model;

import java.util.ArrayList;

class DataFrame {
    private final ArrayList<Column> columns;

    DataFrame() {
        this.columns = new ArrayList<>();
    }

    public void addColumn(String name) {
        columns.add(new Column(name));
        for (int i=0;i<getRowCount();++i) {
            columns.getLast().addRowValue("");
        }
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Column col : columns)
            names.add(col.getName());
        return names;
    }

    public int getRowCount() {
        if (columns.isEmpty())
            return 0;
        return columns.getFirst().getSize();
    }

    public String getValue(String name, int row) {
        for (Column col : columns) {
            if (col.getName().equals(name))
                return col.getRowValue(row);
        }
        return "";
    }

    public void putValue(String name, int row, String value) {
        for (Column col : columns) {
            if (col.getName().equals(name))
                col.setRowValue(row, value);
        }
    }

    public void addValue(String name, String value) {
        for (Column col : columns) {
            if (col.getName().equals(name))
                col.addRowValue(value);
        }
    }

    public ArrayList<String> getColumn(String name) {
        for (Column col : columns) {
            if (col.getName().equals(name))
                return col.getData();
        }
        return new ArrayList<>();
    }
}
