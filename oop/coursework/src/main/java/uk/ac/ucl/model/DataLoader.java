package uk.ac.ucl.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DataLoader {
    DataFrame loadDataFrame(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            DataFrame frame = new DataFrame();
            String line = br.readLine();
            if (line == null)
                return frame;

            String[] names = Arrays.stream(line.split(","))
                                    .map(String::trim)
                                    .toArray(String[]::new);

            for (String name : names)
                frame.addColumn(name);

            while ((line = br.readLine()) != null) {
                String[] values = Arrays.stream(line.split(","))
                                        .map(String::trim)
                                        .toArray(String[]::new);;
                int i = 0;
                for (String name : names) {
                    if (i >= values.length)
                        frame.addValue(name, "");
                    else
                        frame.addValue(name, values[i]);
                    ++i;
                }
            }

            return frame;
        } catch (IOException e) {
            return null;
        }
    }
}
