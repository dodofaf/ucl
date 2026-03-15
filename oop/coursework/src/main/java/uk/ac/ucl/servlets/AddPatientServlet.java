package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet {
     /**
     * Handles HTTP GET requests.
     * Retrieves the necessary column names from the model and forwards the user
     * to the patient addition form.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        request.setAttribute("columns", model.getColumnNames());

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/addPatient.jsp");
        dispatch.forward(request, response);
    }

     /**
     * Handles HTTP POST requests.
     * Extracts submitted form data, packages it into a map, adds it to the model,
     * and redirects back to the main index page.
     * @param request  The HttpServletRequest object containing form parameters.
     * @param response The HttpServletResponse object used for redirection.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        List<String> columns = model.getColumnNames();
        Map<String, String> newPatientData = new HashMap<>();

        for (String colName : columns) {
            String value = request.getParameter(colName);
            newPatientData.put(colName, value != null ? value : "");
        }

        model.addPatient(newPatientData);
        response.sendRedirect("index.html");
    }
}
