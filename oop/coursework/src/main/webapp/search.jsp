<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App - Search</title>
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">
  <h1>Search Patients</h1>
  <form method="GET" action="/runsearch">

    <label for="column">Search By:</label>
    <select name="column" id="column">
      <option value="all">All Data</option>
      <%
        // Simply retrieve the list passed by the SearchPageServlet
        List<String> columns = (List<String>) request.getAttribute("columns");

        if (columns != null) {
            for (String col : columns) {
      %>
              <option value="<%= col %>"><%= col %></option>
      <%
            }
        }
      %>
    </select>

    <input type="text" name="searchstring" placeholder="Enter search keyword here" required/>
    <input type="submit" value="Search"/>
  </form>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>