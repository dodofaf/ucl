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
      List<Map.Entry<String, String>> patients = (List<Map.Entry<String, String>>) request.getAttribute("patientNames");
      if (patients != null)
      {
        for (Map.Entry<String, String> patient : patients)
        {
    %>
    <li><a href="/patient?id=<%= patient.getKey() %>"><%=patient.getValue()%></a>
    </li>
    <%  }
      }
    %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
