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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        request.setAttribute("columns", model.getColumnNames());

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/addPatient.jsp");
        dispatch.forward(request, response);
    }

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
