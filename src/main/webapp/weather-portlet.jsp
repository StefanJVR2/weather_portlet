<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Weather Portlet</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
<div class="weather-portlet fade-in visible">
    <div class="weather-header">
        <h2>Weather in <%= "Cape Town" %>
        </h2>
    </div>
    <div class="weather-icon">
        <img id="icon" src="http://openweathermap.org/img/wn/<%= request.getAttribute("iconCode") %>@2x.png" alt="Weather Icon">
    </div>
    <div id="weather-info" class="fade-in visible">
        <div class="temperature">
            <span id="temperature"><%= request.getAttribute("temperature") %></span>
        </div>
        <div class="details">
            <p id="weather-description">Weather: <%= request.getAttribute("description") %>
            </p>
            <p id="humidity">Humidity: <%= request.getAttribute("humidity") %>%</p>
            <p id="updated"></p>
        </div>
    </div>
</div>
<script>
    // Create a global variable to store the resource URL
    const resourceURL = "<%= renderResponse.createResourceURL() %>";
</script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>

</body>
</html>
