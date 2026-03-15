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

@WebServlet("/updatePatient")
public class UpdatePatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("id");
        Model model = ModelFactory.getModel();

        List<Map.Entry<String, String>> patientData = model.getPatientsData(patientId);
        request.setAttribute("patientdata", patientData);
        request.setAttribute("id", patientId);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/updatePatient.jsp");
        dispatch.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("id");
        Model model = ModelFactory.getModel();
        List<String> columns = model.getColumnNames();
        Map<String, String> updatedData = new HashMap<>();

        for (String colName : columns) {
            String value = request.getParameter(colName);
            if (value != null) {
                updatedData.put(colName, value);
            }
        }

        model.updatePatient(patientId, updatedData);

        response.sendRedirect("/patient?id=" + patientId);
    }
}
