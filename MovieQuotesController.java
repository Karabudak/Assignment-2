package com.example.moviequotesapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieQuotesController {
    @FXML
    private TextField movieTitleField;
    @FXML
    private Button getMovieButton;
    @FXML
    private Button getQuoteButton;
    @FXML
    private Label movieDescriptionLabel;
    @FXML
    private Label quoteLabel;
    @FXML
    private ImageView moviePosterView;

    private String movieTitle;
    private String apiKey = "8923671a";  // OMDB API key

    @FXML
    public void handleGetMovie() throws IOException, InterruptedException {
        movieTitle = movieTitleField.getText().trim().replace(" ", "+");
        String omdbUrl = "https://www.omdbapi.com/?t=" + movieTitle + "&apikey=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(omdbUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());

        if (jsonNode.has("Poster") && jsonNode.has("Plot")) {
            String posterUrl = jsonNode.get("Poster").asText();
            String plot = jsonNode.get("Plot").asText();
            movieDescriptionLabel.setText(plot);
            moviePosterView.setImage(new Image(posterUrl));
        } else {
            movieDescriptionLabel.setText("No movie found.");
            moviePosterView.setImage(null);
        }
    }

    @FXML
    public void handleGetQuote() throws IOException, InterruptedException {
        String quoteApiUrl = "https://api.quotable.io/random";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(quoteApiUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());

        if (jsonNode.has("content")) {
            String quote = jsonNode.get("content").asText();
            quoteLabel.setText(quote);
        } else {
            quoteLabel.setText("No quote found.");
        }
    }
}
