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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The SearchServlet handles HTTP requests for performing patient searches.
 * It is mapped to the URL "/runsearch".
 *
 * This servlet demonstrates:
 * 1. Handling both GET and POST requests.
 * 2. Interacting with a Model via a Factory pattern.
 * 3. Input validation.
 * 4. Error handling and forwarding to error pages.
 * 5. Request-scoped attribute passing to JSPs for rendering results.
 */
@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests.
     *
     * By calling doPost, this allows search results to be bookmarked and refreshed
     * (since many browsers default to GET for URL-based navigation).
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
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
     * This is where the core search logic resides.
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        List<String> columns = model.getColumnNames();
        Map<String, String> newPatientData = new HashMap<>();

        // Dynamically grab all form inputs based on the column names
        for (String colName : columns) {
            String value = request.getParameter(colName);
            newPatientData.put(colName, value != null ? value : "");
        }

        // Add to model
        model.addPatient(newPatientData);

        // Redirect back to the main page (or to the new patient's profile)
        response.sendRedirect("index.html");
    }
}
