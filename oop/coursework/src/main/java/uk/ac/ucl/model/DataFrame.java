package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class DataFrame {
    private final LinkedHashMap<String, Column> columns;

    /**
     * Constructs a new, empty DataFrame.
     */
    DataFrame() {
        this.columns = new LinkedHashMap<>();
    }

     /**
     * Adds a new, empty column to the DataFrame.
     * @param name The name of the new column.
     */
    public void addColumn(String name) {
        columns.put(name, new Column(name));
    }

     /**
     * Retrieves a list of all column names in the DataFrame.
     * @return An ArrayList of column names.
     */
    public ArrayList<String> getColumnNames() {
        return new ArrayList<>(columns.keySet());
    }

     /**
     * Gets the total number of rows in the DataFrame.
     * Assumes all columns have the same size.
     * @return The number of rows, or 0 if the DataFrame is empty.
     */
    public int getRowCount() {
        if (columns.isEmpty())
            return 0;
        return columns.firstEntry().getValue().getSize();
    }

     /**
     * Retrieves a specific value from a given column and row index.
     * @param name The name of the column.
     * @param row  The row index.
     * @return The string value, or an empty string if the column does not exist.
     */
    public String getValue(String name, int row) {
        Column col = columns.get(name);
        if (col != null) {
            return col.getRowValue(row);
        }
        return "";
    }

     /**
     * Updates a specific value at the given column and row index.
     * @param name  The name of the column.
     * @param row   The row index.
     * @param value The new value to insert.
     */
    public void putValue(String name, int row, String value) {
        Column col = columns.get(name);
        if (col != null) {
            col.setRowValue(row, value);
        }
    }

     /**
     * Appends a new value to the end of the specified column.
     * @param name  The name of the column.
     * @param value The value to append.
     */
    public void addValue(String name, String value) {
        Column col = columns.get(name);
        if (col != null) {
            col.addRowValue(value);
        }
    }

     /**
     * Retrieves all data from a specific column.
     * @param name The name of the column.
     * @return An ArrayList containing the column's data, or an empty list if not found.
     */
    public ArrayList<String> getColumn(String name) {
        Column col = columns.get(name);
        if (col != null) {
                return col.getData();
        }
        return new ArrayList<>();
    }

     /**
     * Finds the first row index in a specific column that matches the given value.
     * @param name  The name of the column to search.
     * @param value The value to look for.
     * @return The row index of the match, or -1 if not found.
     */
    public int getRowNumber(String name, String value) {
        Column col = columns.get(name);
        if (col != null) {
            return col.getRowNumber(value);
        }
        return -1;
    }

     /**
     * Retrieves an entire row of data across all columns.
     * @param row The row index to retrieve.
     * @return A List of Map.Entry objects, where the key is the column name and the value is the row data.
     */
    public List<Map.Entry<String, String>> getRow(int row) {
        List<Map.Entry<String, String>> row_data = new ArrayList<>();
        for (Column col : columns.values()) {
            row_data.add(Map.entry(col.getName(), col.getRowValue(row)));
        }
        return row_data;
    }

     /**
     * Searches a specific column for a keyword and returns all matching row indices.
     * @param name The column name to search within.
     * @param key  The keyword to search for.
     * @return A list of integer row indices matching the search.
     */
    public List<Integer> searchColumn(String name, String key) {
        if (columns.get(name) == null)
            return new ArrayList<>();
        return columns.get(name).search(key);
    }

     /**
     * Searches all columns in the DataFrame for a keyword.
     * @param name The column name (currently unused in this logic, kept for signature).
     * @param key  The keyword to search for globally.
     * @return A sorted, distinct list of row indices where the keyword was found.
     */
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

     /**
     * Removes an entire row of data across all columns in the DataFrame.
     * @param row The index of the row to remove.
     */
    public void removeRow(int row) {
        for (Column col : columns.values()) {
            col.removeValue(row);
        }
    }
}
