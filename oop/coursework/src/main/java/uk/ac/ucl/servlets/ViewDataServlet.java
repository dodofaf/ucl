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

@WebServlet("/viewData")
public class ViewDataServlet extends HttpServlet
{
     /**
     * Handles HTTP GET requests.
     * Extracts sorting preferences, fetches the corresponding sorted data from the Model,
     * and forwards it to the view.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sort");
        String sortOrder = request.getParameter("order");

        if (sortOrder == null) {
            sortOrder = "asc";
        }

        try {
            Model model = ModelFactory.getModel();
            List<String> columns = model.getColumnNames();
            List<Map<String, String>> data = model.getAllPatients(sortBy, sortOrder);

            request.setAttribute("columns", columns);
            request.setAttribute("data", data);
            request.setAttribute("currentSort", sortBy);
            request.setAttribute("currentOrder", sortOrder);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/viewData.jsp");
            dispatch.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}
