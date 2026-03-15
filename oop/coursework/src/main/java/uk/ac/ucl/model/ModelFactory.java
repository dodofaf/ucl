package uk.ac.ucl.model;

import java.io.IOException;

public class ModelFactory
{
    private static Model model;

     /**
     * Retrieves the singleton instance of the Model.
     * If the model hasn't been created yet, it initializes it and loads
     * the default dataset before returning it.
     * @return The active Model instance.
     * @throws IOException If there is an issue reading the initial data file (though usually caught/handled inside Model/DataLoader).
     */
    public static Model getModel() throws IOException
    {
        if (model == null) {
            model = new Model();
            model.readFile("data/patients100.csv");
        }
        return model;
    }
}
