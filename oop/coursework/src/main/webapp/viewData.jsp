<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>View All Data</title>
  <style>
    table { border-collapse: collapse; width: 100%; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
    th a { text-decoration: none; color: #333; display: block; }
    th a:hover { text-decoration: underline; color: #000; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="main">
  <h1>All Patient Data</h1>
  <p>Click on any column header to sort. Click again to change direction.</p>

  <%
      List<String> columns = (List<String>) request.getAttribute("columns");
      List<Map<String, String>> data = (List<Map<String, String>>) request.getAttribute("data");

      // Get the current sorting state
      String currentSort = (String) request.getAttribute("currentSort");
      String currentOrder = (String) request.getAttribute("currentOrder");

      if (columns != null && data != null && !data.isEmpty()) {
  %>
      <table>
        <thead>
          <tr>
            <%
               for (String col : columns) {
                   String nextOrder = "asc"; // Default next click to ascending
                   String indicator = "";    // Empty string if not currently sorted

                   // If this is the column currently being sorted, figure out the toggle
                   if (col.equals(currentSort)) {
                       if ("asc".equals(currentOrder)) {
                           nextOrder = "desc";
                           indicator = " &#9650;"; // Up arrow ▲
                       } else {
                           nextOrder = "asc";
                           indicator = " &#9660;"; // Down arrow ▼
                       }
                   }
            %>
                <th>
                    <a href="/viewData?sort=<%= col %>&order=<%= nextOrder %>">
                        <%= col %><%= indicator %>
                    </a>
                </th>
            <% } %>
          </tr>
        </thead>
        <tbody>
          <% for (Map<String, String> row : data) { %>
              <tr>
                <% for (String col : columns) { %>
                    <% if (col.equals("ID")) { %>
                        <td><a href="/patient?id=<%= row.get(col) %>"><%= row.get(col) %></a></td>
                    <% } else { %>
                        <td><%= row.get(col) %></td>
                    <% } %>
                <% } %>
              </tr>
          <% } %>
        </tbody>
      </table>
  <%
      } else {
  %>
      <p>No patient data found.</p>
  <%
      }
  %>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>