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

@WebServlet("/patient")
public class PatientDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientID = request.getParameter("id");

        try {
            Model model = ModelFactory.getModel();

            if (patientID == null || patientID.trim().isEmpty()) {
                request.setAttribute("errorMessage", "A patient with such ID doesn't exist.");
            } else {
                List<Map.Entry<String, String>>  patientsData = model.getPatientsData(patientID);
                request.setAttribute("patientdata", patientsData);
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/patientData.jsp");
            dispatch.forward(request, response);

        } catch (IOException e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}
