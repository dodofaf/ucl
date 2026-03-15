package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/deletePatient")
public class DeletePatientServlet extends HttpServlet {
     /**
     * Handles HTTP POST requests.
     * Deletes the requested patient from the Model and redirects to the home page.
     * @param request  The HttpServletRequest object containing the patient ID.
     * @param response The HttpServletResponse object used for redirection.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("id");

        if (patientId != null && !patientId.trim().isEmpty()) {
            ModelFactory.getModel().deletePatient(patientId);
        }

        response.sendRedirect("index.html");
    }
}
