package com.example.portefeuillefinancierisep;

import Modele.TransactionModele;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

    @FXML
    Button auth;


    private static final String ALPHA_VANTAGE_API_KEY = "BVUP0OE76EXEVVHJ";
    private static final String ALPHA_VANTAGE_API_URL = "https://www.alphavantage.co/query";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Méthode qui permet de changer de fenêtre pour aller sur la page de connexion
     */
    @FXML
    protected void ConnexionButtonClick() {
        loadCryptoData();
        getAllStockPrices();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("auth-view.fxml")); // On récupère le fichier FXML
            Scene scene = new Scene(loader.load()); // On crée une nouvelle scène
            Stage stage = new Stage(); // On crée une nouvelle fenêtre
            stage.setTitle("Connexion");
            stage.setScene(scene);
            ((Stage) this.auth.getScene().getWindow()).close(); // On ferme la fenêtre actuelle
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Méthode qui permet de changer de fenêtre pour aller sur la page d'inscription
     */
    @FXML
    protected void InscriptionButtonClick() {
        loadCryptoData();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("inscription-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Inscription");
            stage.setScene(scene);
            ((Stage) this.auth.getScene().getWindow()).close();
            stage.show(); // On affiche la nouvelle fenêtre
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCryptoData() {
        String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false&ids=bitcoin,ethereum,ripple,litecoin,cardano,polkadot,binancecoin,chainlink,stellar";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateCryptoData)
                .exceptionally(this::handleError);
    }

    private void updateCryptoData(String responseBody) {
            Platform.runLater(() -> {
                JSONArray jsonArray = new JSONArray(responseBody);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject cryptoData = jsonArray.getJSONObject(i);
                    String name = cryptoData.getString("name");
                    double price = cryptoData.getDouble("current_price");
                    switch (i) {
                        case 0:
                            name = "Bitcoin";
                            break;
                        case 1:
                            name = "Ethereum";
                            break;
                        case 2:
                            name = "Ripple";
                            break;
                        case 3:
                            name = "Litecoin";
                            break;
                        case 4:
                            name = "Cardano";
                            break;
                        case 5:
                            name = "Polkadot";
                            break;
                        case 6:
                            name = "Binancecoin";
                            break;
                        case 7:
                            name = "Chainlink";
                            break;
                        case 8:
                            name = "Stellar";
                            break;
                    }
                    TransactionModele.updateCryptoPrice(name,price);
                }
            });
    }

    public String getStockData(String symbol) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        String endpoint = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", ALPHA_VANTAGE_API_URL, symbol, ALPHA_VANTAGE_API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofMinutes(2))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Double> getAllStockPrices() {
        Map<String, Double> prices = new HashMap<>();
        String[] symbols = {"AAPL", "GOOGL", "TSLA"};

        for (String symbol : symbols) {
            String rawData = getStockData(symbol);
            if (rawData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(rawData);
                    if (!jsonObject.has("Time Series (Daily)")) {
                        System.err.println("Time Series (Daily) not found for symbol: " + symbol);
                        continue;
                    }
                    JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
                    String latestDate = timeSeries.keys().next(); // Obtenez la date la plus récente disponible
                    JSONObject latestData = timeSeries.getJSONObject(latestDate);
                    double price = latestData.getDouble("4. close");
                    switch (symbol) {
                        case "AAPL":
                            symbol = "Apple";
                            break;
                        case "GOOGL":
                            symbol = "Google";
                            break;
                        case "TSLA":
                            symbol = "Tesla";
                            break;
                    }
                    TransactionModele.updateActionPrice(symbol,price);
                } catch (JSONException e) {
                    System.err.println("Invalid JSON format for symbol: " + symbol);
                    e.printStackTrace();
                }
            }
        }
        return prices;
    }

    public double getCurrentStockPrice(String symbol) {
        try {
            String rawData = getStockData(symbol);
            if (rawData != null) {
                JSONObject jsonObject = new JSONObject(rawData);
                if (!jsonObject.has("Time Series (Daily)")) {
                    return -1;
                }
                JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");

                // Get the latest available date
                String latestDate = timeSeries.keys().next();

                // Get the latest close price directly
                double latestClosePrice = timeSeries.getJSONObject(latestDate).getDouble("4. close");

                System.out.println("Latest close price for " + symbol + ": " + latestClosePrice); // Add this line if needed
                return latestClosePrice;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private Void handleError(Throwable throwable) {
        Platform.runLater(() -> {
        });
        return null;
    }

}
