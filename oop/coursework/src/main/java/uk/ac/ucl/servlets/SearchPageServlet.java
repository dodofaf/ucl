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

@WebServlet("/search")
public class SearchPageServlet extends HttpServlet {
     /**
     * Handles HTTP GET requests.
     * Fetches column names to populate the search criteria dropdown and forwards to the view.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model model = ModelFactory.getModel();
            List<String> columnNames = model.getColumnNames();
            request.setAttribute("columns", columnNames);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading search page: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}
