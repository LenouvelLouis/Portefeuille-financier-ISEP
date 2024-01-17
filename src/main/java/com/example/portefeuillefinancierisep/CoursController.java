package com.example.portefeuillefinancierisep;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoursController {

    @FXML
    private LineChart<String, Number> chart;
    @FXML
    private ComboBox<String> selectionComboBox;
    @FXML
    private Label msg_error;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKeyAlphaVantage = "4M7JZQ4XZVKBP74N"; // Votre clé API Alpha Vantage
    private final String apiKeyCoinGecko = "CG-eJ4aiypJUFhNRRuDo9Aecuxj"; // Votre clé API CoinGecko

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
        String apiUrl = "https://finnhub.io/api/v1/quote?symbol=AAPL,MSFT,GOOGL,TSLA,AMZN,NVDA,NFLX,INTC,AMD&token=" + apiKeyAlphaVantage;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateChart)
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

    private void updateChart(String responseBody) {
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

    private Void handleError(Throwable throwable) {
        Platform.runLater(() -> {
            msg_error.setText("Erreur : " + throwable.getMessage());
        });
        return null;
    }
}
