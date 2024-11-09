package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherPortlet extends GenericPortlet {

    private static final String API_KEY = ""; // Replace with your API key
    private static final String CITY = "Cape%20Town"; // Replace with your preferred city

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        response.setContentType("text/html");

        fetchWeatherData(request);

        getPortletContext().getRequestDispatcher("/weather-portlet.jsp").include(request, response);
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException {
        // Set content type for the response
        response.setContentType("text/plain");

        // Fetch the weather data
        String weatherInfo = fetchWeatherData(null);

        // Write the response
        PrintWriter out = response.getWriter();
        out.write(weatherInfo != null ? weatherInfo : "Error fetching data");
        out.close();
    }

    private String fetchWeatherData(RenderRequest renderRequest) {
        String weatherData = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder jsonData = new StringBuilder();

                while (scanner.hasNext()) {
                    jsonData.append(scanner.nextLine());
                }
                scanner.close();

                weatherData = parseWeatherData(jsonData.toString(), renderRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    private String parseWeatherData(String jsonData, RenderRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonData);
        String description = root.path("weather").get(0).path("description").asText();
        String temperature = root.path("main").path("temp").asText();
        String humidity = root.path("main").path("humidity").asText();
        String iconCode = root.path("weather").get(0).path("icon").asText();
        if (request != null) {
            request.setAttribute("iconCode", iconCode);
            request.setAttribute("humidity", humidity);
            request.setAttribute("description", description);
            request.setAttribute("temperature", temperature + "°C");
        }


        return jsonData;
    }
}
