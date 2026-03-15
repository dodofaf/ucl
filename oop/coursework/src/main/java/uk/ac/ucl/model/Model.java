package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model
{
    // The example code in this class should be replaced by your Model class code.
    // The patients should be stored in a suitable patients structure.
    private DataFrame patients;

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

    public List<Map.Entry<String, String>> getPatientsData(String patientID) {
        int row = patients.getRowNumber("ID", patientID);
        if (row == -1)
            return null;
        return patients.getRow(row);
    }

    // This method illustrates how to read csv patients from a file.
    // The patients files are stored in the root directory of the project (the directory your project is in),
    // in the directory named patients.
    public void readFile(String fileName)
    {
        DataLoader loader = new DataLoader();
        patients = loader.loadDataFrame(fileName);
    }

    // This also returns dummy patients. The real version should use the keyword parameter to search
    // the patients and return a list of matching items.
    // Updated searchFor method
    public List<Map.Entry<String, String>> searchFor(String column, String keyword) {
        List<Integer> matchedRows;

        // If 'column' is null, empty, or set to "all", use searchALL
        if (column == null || column.trim().isEmpty() || column.equalsIgnoreCase("all")) {
            // Note: The first argument "all" is ignored by your DataFrame's searchALL implementation
            matchedRows = patients.searchALL("all", keyword);
        } else {
            // Otherwise, search within the specific column provided
            matchedRows = patients.searchColumn(column, keyword);
        }

        List<Map.Entry<String, String>> results = new ArrayList<>();

        // Map the matched row indices to their corresponding ID and Full Name
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

    public List<String> getColumnNames() {
        return patients.getColumnNames();
    }

    public void addPatient(Map<String, String> patientData) {
        for (String colName : getColumnNames()) {
            String value = patientData.getOrDefault(colName, "");
            patients.addValue(colName, value);
        }
        saveToCSV("data/patiens_new.csv");
    }

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

    public void deletePatient(String patientId) {
        int row = patients.getRowNumber("ID", patientId);
        if (row != -1) {
            patients.removeRow(row);
        }
        saveToCSV("data/patiens_new.csv");
    }

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
}
