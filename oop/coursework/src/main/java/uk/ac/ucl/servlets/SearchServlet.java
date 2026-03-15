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
import java.util.List;
import java.util.Map;

@WebServlet("/runsearch")
public class SearchServlet extends HttpServlet {
     /**
     * Handles HTTP GET requests by routing them to the POST logic.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

     /**
     * Handles HTTP POST requests.
     * Extracts search parameters, queries the model, and forwards the results.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchString = request.getParameter("searchstring");
        String column = request.getParameter("column");

        try {
            Model model = ModelFactory.getModel();

            if (searchString == null || searchString.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter a search term.");
            } else {
            List<Map.Entry<String, String>> searchResult = model.searchFor(column, searchString);
            request.setAttribute("result", searchResult);
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
            dispatch.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}
