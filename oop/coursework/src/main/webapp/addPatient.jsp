<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Add New Patient</title>
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">
  <h1>Add New Patient</h1>

  <form method="POST" action="/addPatient">
    <%
        List<String> columns = (List<String>) request.getAttribute("columns");
        if (columns != null) {
            for (String col : columns) {
    %>
              <div style="margin-bottom: 10px;">
                  <label for="<%= col %>" style="display:inline-block; width:100px;"><%= col %>:</label>

                  <% if(col.equals("ID")) { %>
                      <input type="text" name="<%= col %>" id="<%= col %>" required />
                  <% } else { %>
                      <input type="text" name="<%= col %>" id="<%= col %>" />
                  <% } %>
              </div>
    <%
            }
        }
    %>
    <br>
    <input type="submit" value="Save Patient"/>
  </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>