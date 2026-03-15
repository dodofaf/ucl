<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %> <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search Result</h1>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }

    // Updated cast to expect Map.Entry pairs
    List<Map.Entry<String, String>> patients = (List<Map.Entry<String, String>>) request.getAttribute("result");

    if (patients != null && patients.size() != 0)
    {
    %>
    <ul>
      <%
        for (Map.Entry<String, String> patient : patients)
        {
      %>
      <li><a href="/patient?id=<%= patient.getKey() %>"><%= patient.getValue() %></a></li>
     <% }
    } else if (errorMessage == null)
    {%>
      <p>Nothing found</p>
  <%}%>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>