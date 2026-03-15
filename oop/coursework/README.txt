Patient Data Management System (OOP Coursework)
Overview

This project is a Java-based web application designed to manage and interact with patient records.

Instead of relying on an external database, the application builds a custom, memory-efficient data structure (DataFrame) to parse, store, query, and manipulate data natively from CSV files.
Key Features

    Patient Directory: View a complete, sortable list of all patients in the system.

    Detailed Profiles: View all corresponding data for a specific patient using their unique ID.

    Search Functionality: Perform global keyword searches across all data fields or restrict searches to a specific column.

    CRUD Operations: * Create: Add new patient records to the system.

        Read: View existing patient records.

        Update: Edit specific details of an existing patient.

        Delete: Remove a patient from the system entirely.

    Data Persistence: Automatically saves all additions, updates, and deletions back into a CSV file (patiens_new.csv).

    JSON Export: Download the entire current patient dataset formatted as a JSON file.


How to Run

    Compile the project: Ensure all Maven dependencies (like embedded Tomcat and Jakarta Servlet APIs) are downloaded and the project is compiled.

    Bash:
    mvn clean compile

    Start the Server: Execute the Main.java class located in uk.ac.ucl.main.Main. This file initializes the embedded Tomcat server.

    Bash:
    mvn exec:java -Dexec.mainClass="uk.ac.ucl.main.Main"

    (Alternatively, run Main.java directly from your IDE).

    Access the Web App: Once the console output indicates the server has started successfully, open your web browser and navigate to:

    http://localhost:8080

Data Source

The application initially loads data from data/patients100.csv. As you add, edit, or delete patients via the web interface, the updated state is continuously saved to data/patiens_new.csv to ensure data persistence across server restarts.