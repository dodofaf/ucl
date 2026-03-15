<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
  %>
  <ul>
    <%
      // Cast the attribute to the correct List of Map Entries
      List<Map.Entry<String, String>> patients = (List<Map.Entry<String, String>>) request.getAttribute("patientdata");

      if (patients != null && !patients.isEmpty())
      {
        for (Map.Entry<String, String> entry : patients)
        {
          // Extract the key (Name) and value (Data/ID)
          String name = entry.getKey();
          String detail = entry.getValue();
    %>
          <li>
            <a><strong><%= name %></strong>: <%= detail %></a>
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
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>