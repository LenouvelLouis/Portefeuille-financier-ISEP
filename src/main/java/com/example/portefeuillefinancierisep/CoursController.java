package com.example.portefeuillefinancierisep;

import Modele.TransactionModele;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CoursController {

    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private ComboBox<String> selectionComboBox;
    @FXML
    private Label msg_error;

    TransactionModele transactionModele= new TransactionModele();
    private static final String ALPHA_VANTAGE_API_URL = "https://www.alphavantage.co/query";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKeyAlphaVantage = "VBOME4JJV3R705Z0"; // Votre clé API Alpha Vantage

    @FXML
    public void initialize() {
        selectionComboBox.setOnAction(event -> {
            String selectedValue = selectionComboBox.getValue();
            if (selectedValue != null) {
                if (selectedValue.equals("Actions")) {
                    loadActionData();
                } else if (selectedValue.equals("Cryptomonnaies")) {
                    loadCryptoData();
                }
            }
        });
        selectionComboBox.getItems().addAll("Actions", "Cryptomonnaies");
    }

    private void loadActionData() {
        String apiUrl = "https://finnhub.io/api/v1/quote?symbol=AAPL,GOOGL,TSLA,AMZN,NVDA,NFLX,INTC,AMD&token=" + apiKeyAlphaVantage;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updatePriceAction)
                .exceptionally(this::handleError);
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

    private void updatePriceAction(String responseBody) {
        Platform.runLater(() -> {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray dataPoints = jsonObject.getJSONArray("data");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Cours");

            for (int i = 0; i < dataPoints.length(); i++) {
                JSONObject dataPoint = dataPoints.getJSONObject(i);
                String symbol = dataPoint.getString("symbol");
                Number value = dataPoint.getDouble("price");

                series.getData().add(new XYChart.Data<>(symbol, value));
            }

            chart.getData().clear();
            chart.getData().add(series);
            msg_error.setText("");
        });
    }

    private void updateCryptoData(String responseBody) {
        Platform.runLater(() -> {
            ObservableList<String> cryptoNames = FXCollections.observableArrayList();
            ObservableList<Number> cryptoPrices = FXCollections.observableArrayList();

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
                cryptoNames.add(name);
                cryptoPrices.add(price);
            }

            chart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Cours");

            for (int i = 0; i < cryptoNames.size(); i++) {
                series.getData().add(new XYChart.Data<>(cryptoNames.get(i), cryptoPrices.get(i)));
            }

            chart.getData().add(series);
            msg_error.setText("");
        });
    }

    public String getStockData(String symbol) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        String endpoint = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", ALPHA_VANTAGE_API_URL, symbol, apiKeyAlphaVantage);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofMinutes(2))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("Response for " + symbol + ": " + responseBody); // Ajoutez cette ligne
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Double> getAllStockPrices() {
        Map<String, Double> prices = new HashMap<>();
        String[] symbols = {"AAPL", "MSFT", "GOOGL", "TSLA", "AMZN", "NVDA", "NFLX", "INTC", "AMD"};

        for (String symbol : symbols) {
            String rawData = getStockData(symbol);
            if (rawData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(rawData);
                    // Vérifiez si la clé "Time Series (Daily)" existe
                    if (!jsonObject.has("Time Series (Daily)")) {
                        System.err.println("Time Series (Daily) not found for symbol: " + symbol);
                        continue; // Passez au symbole suivant si la clé n'existe pas
                    }
                    JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
                    String latestDate = timeSeries.keys().next(); // Obtenez la date la plus récente disponible
                    JSONObject latestData = timeSeries.getJSONObject(latestDate);
                    double price = latestData.getDouble("4. close");
                    prices.put(symbol, price);
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
                String latestDate = timeSeries.keys().next();
                JSONObject latestData = timeSeries.getJSONObject(latestDate);
                return latestData.getDouble("4. close");
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    private Void handleError(Throwable throwable) {
        Platform.runLater(() -> {
            msg_error.setText("Erreur : " + throwable.getMessage());
        });
        return null;
    }
}
