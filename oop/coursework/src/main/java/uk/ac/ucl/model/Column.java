package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public int getRowNumber(String value) {
        for (int i=0;i<data.size();++i)
            if (data.get(i).equals(value))
                return i;
        return -1;
    }

    public List<Integer> search(String key) {
        List<Integer> search_res = new ArrayList<>();
        for (int i=0;i<data.size();++i)
            if (data.get(i).equals(key))
                search_res.add(i);
        return search_res;
    }

    public void removeValue(int row) {
        data.remove(row);
    }
}
