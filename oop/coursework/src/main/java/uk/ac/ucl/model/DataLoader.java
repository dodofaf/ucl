package uk.ac.ucl.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DataLoader {
    DataFrame loadDataFrame(String filepath) {
        DataFrame frame = new DataFrame();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
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
        } catch (IOException e) {
            System.err.println("Invalid filepath.");
        }

        return frame;
    }
}
