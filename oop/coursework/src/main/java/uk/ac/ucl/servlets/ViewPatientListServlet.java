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

@WebServlet("/patientList")
public class ViewPatientListServlet extends HttpServlet
{
     /**
     * Handles HTTP GET requests.
     * Fetches the patient names and IDs from the model and forwards to the list view.
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try {
            Model model = ModelFactory.getModel();
            List<Map.Entry<String, String>> patientNames = model.getPatientNames();
            request.setAttribute("patientNames", patientNames);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
            dispatch.forward(request, response);
        } catch (IOException e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

     /**
     * Handles HTTP POST requests by routing them directly to the GET logic.
     * * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
