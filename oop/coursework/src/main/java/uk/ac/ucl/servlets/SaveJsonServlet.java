package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/saveJson")
public class SaveJsonServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"patients_data.json\"");

        try (PrintWriter out = response.getWriter()) {
            ModelFactory.getModel().exportJSON(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
