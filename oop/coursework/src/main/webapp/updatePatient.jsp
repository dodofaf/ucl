<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Update Patient</title>
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">
  <h1>Update Patient Data</h1>

  <%
      String patientId = (String) request.getAttribute("id");
      List<Map.Entry<String, String>> patientData = (List<Map.Entry<String, String>>) request.getAttribute("patientdata");

      if (patientData != null && !patientData.isEmpty()) {
  %>
      <form method="POST" action="/updatePatient">
        <input type="hidden" name="id" value="<%= patientId %>" />

        <%
            for (Map.Entry<String, String> entry : patientData) {
                String colName = entry.getKey();
                String currentValue = entry.getValue();
        %>
                <div style="margin-bottom: 10px;">
                    <label for="<%= colName %>" style="display:inline-block; width:100px;"><%= colName %>:</label>

                    <% if (colName.equals("ID")) { %>
                        <input type="text" name="<%= colName %>" id="<%= colName %>" value="<%= currentValue %>" readonly style="background-color: #eee;" />
                    <% } else { %>
                        <input type="text" name="<%= colName %>" id="<%= colName %>" value="<%= currentValue %>" />
                    <% } %>
                </div>
        <%
            }
        %>
        <br>
        <input type="submit" value="Save Changes"/>
        <a href="/patient?id=<%= patientId %>" style="margin-left: 15px;">Cancel</a>
      </form>
  <%
      } else {
  %>
      <p>Error loading patient data for update.</p>
  <%
      }
  %>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>