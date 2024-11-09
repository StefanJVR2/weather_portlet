console.log("scripts.js is loaded");

// Function to update weather data with fade-in effect
const updateWeatherWithFade = () => {
    const weatherInfo = document.getElementById("weather-info");

    // Remove the 'visible' class to start the fade-out
    weatherInfo.classList.remove("visible");

    fetch(resourceURL)
        .then(response => response.json())
        .then(data => {

            // Get the current date and time
            const now = new Date();
            const formattedDate = now.toLocaleString(); // Formats according to the user's locale
            setTimeout(() => {
                // Display the result in an HTML element with ID "weather-info"
                document.getElementById("icon").src = "http://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png";
                document.getElementById("weather-description").textContent = "Weather: " + data.weather[0].description;
                document.getElementById("temperature").textContent = data.main.temp + "\u00B0C";
                document.getElementById("humidity").textContent = "Humidity: " + data.main.humidity + "%";
                document.getElementById("updated").textContent = "Updated at: " + formattedDate;
                weatherInfo.classList.add("visible");
            }, 1000);

        })
        .catch(error => {
            console.error("Error fetching weather data:", error)
            setTimeout(() => {
                weatherInfo.classList.add("visible");
            }, 100);
        });
};

// Refresh the weather data every 60 seconds
setInterval(updateWeatherWithFade, 20000);
