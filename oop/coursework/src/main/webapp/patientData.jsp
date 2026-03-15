<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Profile</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patient Profile:</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    // Grab the ID from the URL parameters so we know who to update/delete
    String patientId = request.getParameter("id");

    if (errorMessage != null) {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>
  <ul>
    <%
      List<Map.Entry<String, String>> patients = (List<Map.Entry<String, String>>) request.getAttribute("patientdata");

      if (patients != null && !patients.isEmpty()) {
        for (Map.Entry<String, String> entry : patients) {
          String name = entry.getKey();
          String detail = entry.getValue();
    %>
          <li>
            <strong><%= name %></strong>: <%= detail %>
          </li>
    <%
        }
      } else if (errorMessage == null) {
    %>
      <li>No patient data found.</li>
    <%
      }
    %>
  </ul>

  <% if (patients != null && !patients.isEmpty() && patientId != null) { %>
      <div style="margin-top: 20px;">
          <form action="/updatePatient" method="GET" style="display:inline;">
              <input type="hidden" name="id" value="<%= patientId %>">
              <input type="submit" value="Update Patient">
          </form>

          <form action="/deletePatient" method="POST" style="display:inline;">
              <input type="hidden" name="id" value="<%= patientId %>">
              <input type="submit" value="Delete Patient" onclick="return confirm('Are you sure you want to delete this patient? This cannot be undone.');">
          </form>
      </div>
  <% } %>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>