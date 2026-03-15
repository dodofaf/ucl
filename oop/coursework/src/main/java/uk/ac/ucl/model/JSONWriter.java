package uk.ac.ucl.model;

import java.io.PrintWriter;
import java.util.List;

public class JSONWriter {
    public void writeJSON(DataFrame frame, PrintWriter writer) {
        if (frame == null || frame.getRowCount() == 0) {
            writer.print("[]");
            return;
        }

        List<String> columns = frame.getColumnNames();
        int rowCount = frame.getRowCount();
        writer.println("[");
        for (int i = 0; i < rowCount; i++) {
            writer.println("  {");
            for (int j = 0; j < columns.size(); j++) {
                String name = columns.get(j);
                String val = frame.getValue(name, i);
                if (val == null) val = "";
                val = val.replace("\\", "\\\\").replace("\"", "\\\"");
                writer.print("    \"" + name + "\": \"" + val + "\"");
                if (j < columns.size()-1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }

            if (i < rowCount-1) {
                writer.println("  },");
            } else {
                writer.println("  }");
            }
        }

        writer.println("]");
    }
}