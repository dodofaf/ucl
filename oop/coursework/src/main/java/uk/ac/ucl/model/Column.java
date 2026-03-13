package uk.ac.ucl.model;

import java.util.ArrayList;

class Column {
    private final String name;
    private final ArrayList<String> data;

    Column (String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return data.size();
    }

    public String getRowValue(int row) {
        return data.get(row);
    }

    public void setRowValue(int row, String new_value) {
        data.set(row, new_value);
    }

    public void addRowValue(String value) {
        data.add(value);
    }

    public ArrayList<String> getData() {
        return data;
    }
}
