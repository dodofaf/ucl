package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Model
{
    // The example code in this class should be replaced by your Model class code.
    // The data should be stored in a suitable data structure.
    private DataFrame data;

    public List<String> getPatientNames()
    {
        List<String> prefixes = data.getColumn("PREFIX");
        List<String> first = data.getColumn("FIRST");
        List<String> last = data.getColumn("LAST");
        List<String> suffixes = data.getColumn("SUFFIX");
        List<String> names = new ArrayList<>(data.getRowCount());
        for (int i=0;i<data.getRowCount();++i) {
            String name = "";
            if (!prefixes.get(i).isEmpty())
                name += prefixes.get(i) + " ";
            if (!first.get(i).isEmpty())
                name += first.get(i) + " ";
            if (!last.get(i).isEmpty())
                name += last.get(i) + " ";
            if (!suffixes.get(i).isEmpty())
                name += suffixes.get(i) + " ";
            names.add(name);
        }
        return names;
    }

    // This method illustrates how to read csv data from a file.
    // The data files are stored in the root directory of the project (the directory your project is in),
    // in the directory named data.
    public void readFile(String fileName)
    {
        DataLoader loader = new DataLoader();
        data = loader.loadDataFrame(fileName);
    }

    // This also returns dummy data. The real version should use the keyword parameter to search
    // the data and return a list of matching items.
    public List<String> searchFor(String keyword)
    {
        return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
    }
}
