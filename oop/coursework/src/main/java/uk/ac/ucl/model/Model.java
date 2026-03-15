package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Model
{
    private DataFrame patients;

     /**
     * Generates a list of formatted full names for all patients, alongside their IDs.
     * @return A List of key-value pairs where the key is the Patient ID and the value is their full formatted name.
     */
    public List<Map.Entry<String, String>> getPatientNames()
    {
        List<String> prefixes = patients.getColumn("PREFIX");
        List<String> first = patients.getColumn("FIRST");
        List<String> last = patients.getColumn("LAST");
        List<String> suffixes = patients.getColumn("SUFFIX");
        List<Map.Entry<String, String>> names = new ArrayList<>(patients.getRowCount());
        for (int i = 0; i< patients.getRowCount(); ++i) {
            String name = "";
            if (!prefixes.get(i).isEmpty())
                name += prefixes.get(i) + " ";
            if (!first.get(i).isEmpty())
                name += first.get(i) + " ";
            if (!last.get(i).isEmpty())
                name += last.get(i) + " ";
            if (!suffixes.get(i).isEmpty())
                name += suffixes.get(i) + " ";
            names.add(Map.entry(patients.getValue("ID", i), name));
        }
        return names;
    }

     /**
     * Retrieves all data associated with a specific patient ID.
     * @param patientID The ID of the patient.
     * @return A List of Map entries representing the patient's data, or null if the patient is not found.
     */
    public List<Map.Entry<String, String>> getPatientsData(String patientID) {
        int row = patients.getRowNumber("ID", patientID);
        if (row == -1)
            return null;
        return patients.getRow(row);
    }

     /**
     * Loads patient data from a CSV file into the DataFrame.
     * @param fileName The path to the CSV file to read.
     */
    public void readFile(String fileName)
    {
        DataLoader loader = new DataLoader();
        patients = loader.loadDataFrame(fileName);
    }

     /**
     * Searches for a keyword either within a specific column or across all columns.
     * @param column  The specific column to search in, or "all" to search globally.
     * @param keyword The search term.
     * @return A List of Map entries containing the ID and formatted name of matching patients.
     */
    public List<Map.Entry<String, String>> searchFor(String column, String keyword) {
        List<Integer> matchedRows;

        if (column == null || column.trim().isEmpty() || column.equalsIgnoreCase("all")) {
            matchedRows = patients.searchALL("all", keyword);
        } else {
            matchedRows = patients.searchColumn(column, keyword);
        }

        List<Map.Entry<String, String>> results = new ArrayList<>();

        for (int row : matchedRows) {
            String name = "";
            String prefix = patients.getValue("PREFIX", row);
            String first = patients.getValue("FIRST", row);
            String last = patients.getValue("LAST", row);
            String suffix = patients.getValue("SUFFIX", row);

            if (!prefix.isEmpty()) name += prefix + " ";
            if (!first.isEmpty()) name += first + " ";
            if (!last.isEmpty()) name += last + " ";
            if (!suffix.isEmpty()) name += suffix + " ";

            String id = patients.getValue("ID", row);
            results.add(Map.entry(id, name.trim()));
        }

        return results;
    }

     /**
     * Gets the names of all columns currently in the DataFrame.
     * @return A list of column header names.
     */
    public List<String> getColumnNames() {
        return patients.getColumnNames();
    }

     /**
     * Adds a new patient record to the DataFrame and saves the updated data to CSV.
     * @param patientData A map containing column names as keys and the new patient's data as values.
     */
    public void addPatient(Map<String, String> patientData) {
        for (String colName : getColumnNames()) {
            String value = patientData.getOrDefault(colName, "");
            patients.addValue(colName, value);
        }
        saveToCSV("data/patiens_new.csv");
    }

     /**
     * Updates an existing patient's record based on their ID, then saves to CSV.
     * @param patientId   The ID of the patient to update.
     * @param patientData A map of the columns to update and their new values.
     */
    public void updatePatient(String patientId, Map<String, String> patientData) {
        int row = patients.getRowNumber("ID", patientId);
        if (row != -1) {
            for (String colName : getColumnNames()) {
                if (patientData.containsKey(colName)) {
                    patients.putValue(colName, row, patientData.get(colName));
                }
            }
        }
        saveToCSV("data/patiens_new.csv");
    }

     /**
     * Removes a patient record from the DataFrame based on their ID, then saves to CSV.
     * @param patientId The ID of the patient to delete.
     */
    public void deletePatient(String patientId) {
        int row = patients.getRowNumber("ID", patientId);
        if (row != -1) {
            patients.removeRow(row);
        }
        saveToCSV("data/patiens_new.csv");
    }

     /**
     * Serializes the current state of the DataFrame into a CSV file.
     * @param filePath The destination path where the CSV will be saved.
     */
    public void saveToCSV(String filePath) {
        if (patients == null || patients.getRowCount() == 0) {
            System.out.println("No data to save.");
            return;
        }

        List<String> columns = patients.getColumnNames();
        int rowCount = patients.getRowCount();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(String.join(",", columns));

            for (int i = 0; i < rowCount; i++) {
                StringBuilder rowString = new StringBuilder();

                for (int j = 0; j < columns.size(); j++) {
                    String colName = columns.get(j);
                    String val = patients.getValue(colName, i);
                    if (val == null) val = "";

                    if (val.contains(",")) {
                        val = "\"" + val + "\"";
                    }

                    rowString.append(val);

                    if (j < columns.size() - 1) {
                        rowString.append(",");
                    }
                }
                writer.println(rowString.toString());
            }

            System.out.println("Data successfully saved to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error saving data to CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

     /**
     * Exports the current DataFrame data as a JSON object using the injected writer.
     * @param writer The PrintWriter to stream the JSON output to.
     */
    public void exportJSON(PrintWriter writer) {
        if (patients != null) {
            JSONWriter jsonWriter = new JSONWriter();
            jsonWriter.writeJSON(patients, writer);
        }
    }

     /**
     * Retrieves all patient records, optionally sorting them by a specific column.
     * @param sortByColumn The name of the column to sort by (can be null).
     * @param sortOrder    The direction of the sort ("asc" or "desc").
     * @return A list of mapped patient rows.
     */
    public List<Map<String, String>> getAllPatients(String sortByColumn, String sortOrder) {
        List<Map<String, String>> allRows = new ArrayList<>();

        List<String> columns = patients.getColumnNames();
        int rowCount = patients.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            Map<String, String> row = new LinkedHashMap<>();
            for (String col : columns) {
                row.put(col, patients.getValue(col, i));
            }
            allRows.add(row);
        }

        if (sortByColumn != null && columns.contains(sortByColumn)) {
            boolean isDesc = "desc".equalsIgnoreCase(sortOrder);

            allRows.sort((row1, row2) -> {
                String val1 = row1.get(sortByColumn);
                String val2 = row2.get(sortByColumn);

                if (val1 == null) val1 = "";
                if (val2 == null) val2 = "";

                int comparisonResult = val1.compareToIgnoreCase(val2);
                return isDesc ? -comparisonResult : comparisonResult;
            });
        }

        return allRows;
    }
}
