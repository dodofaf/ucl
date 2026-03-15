package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

class Column {
    private final String name;
    private final ArrayList<String> data;

     /**
     * Constructs a new empty Column with the specified name.
     * @param name The name of the column.
     */
    Column (String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

     /**
     * Gets the name of the column.
     * @return The column name.
     */
    public String getName() {
        return name;
    }

     /**
     * Gets the number of rows currently stored in this column.
     * @return The size of the column.
     */
    public int getSize() {
        return data.size();
    }

     /**
     * Retrieves the value at a specific row index.
     * @param row The index of the row.
     * @return The string value at the specified row.
     */
    public String getRowValue(int row) {
        return data.get(row);
    }

     /**
     * Updates the value at a specific row index.
     * @param row       The index of the row to update.
     * @param new_value The new string value to set.
     */
    public void setRowValue(int row, String new_value) {
        data.set(row, new_value);
    }

     /**
     * Appends a new value to the end of the column.
     * @param value The value to add.
     */
    public void addRowValue(String value) {
        data.add(value);
    }

     /**
     * Retrieves the entire list of data stored in this column.
     * @return An ArrayList containing all row values.
     */
    public ArrayList<String> getData() {
        return data;
    }

     /**
     * Finds the first row index that matches the given value.
     * @param value The string value to search for.
     * @return The index of the first matching row, or -1 if not found.
     */
    public int getRowNumber(String value) {
        for (int i=0;i<data.size();++i)
            if (data.get(i).equals(value))
                return i;
        return -1;
    }

     /**
     * Finds all row indices that exactly match the provided key.
     * @param key The search key.
     * @return A list of integer row indices where the key was found.
     */
    public List<Integer> search(String key) {
        List<Integer> search_res = new ArrayList<>();
        for (int i=0;i<data.size();++i)
            if (data.get(i).equals(key))
                search_res.add(i);
        return search_res;
    }

    /**
     * Removes the value at the specified row index.
     * @param row The index of the row to remove.
     */
    public void removeValue(int row) {
        data.remove(row);
    }
}
