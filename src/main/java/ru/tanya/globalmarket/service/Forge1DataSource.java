package ru.tanya.globalmarket.service;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.tanya.globalmarket.model.CurrencyNominal;
import ru.tanya.globalmarket.model.forge1.Forge1CurrencyInfo;
import ru.tanya.globalmarket.model.forge1.Forge1Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для получения данных о валюте из источника данных 1Forge.com
 */
public class Forge1DataSource implements CurrencyDataSource {

    /**
     * Ключ от АПИ 1Forge.com
     */
    private static final String API_KEY = "iMHOTYq5yg7KzQtjDHgjqQ89oxbP9hEt";

    /**
     * ULR до АПИ 1Forge.com
     */
    private static final String API_URL =  "https://forex.1forge.com/1.0.3/quotes?pairs=USDRUB,USDEUR&api_key="
            + API_KEY;

    private static final String USDRUB_PAIR_SYMBOL = "USDRUB";
    private static final String USDUER_PAIR_SYMBOL = "USDEUR";

    public CurrencyNominal getCurrencyNominalByDate(LocalDate date) throws UnirestException {
        System.out.println("Получаем информацию о валюте из Forge1");

        JsonNode jsonNode = Unirest.get(API_URL).asJson().getBody();

        Forge1Response response = parseJsonResponse(jsonNode);

        double unitUSD = 1;
        double unitEUR = 0;
        double unitRUB = 0;
        for (Forge1CurrencyInfo currencyInfo : response.currencyInfoList) {
            switch (currencyInfo.symbol) {
                case USDRUB_PAIR_SYMBOL: {
                    unitRUB = currencyInfo.price;
                    break;
                }
                case USDUER_PAIR_SYMBOL: {
                    unitEUR = currencyInfo.price;
                    break;
                }
                default: {
                    throw new RuntimeException("Ответ forex1 не содержит искомых валют. " +
                            "Проверьте правильность запроса");
                }
            }
        }

        CurrencyNominal currencyNominal = new CurrencyNominal(unitUSD, unitEUR, unitRUB);

        return currencyNominal;
    }

    /**
     * Требуется распарсить следующую json строку
     * [
     * {
     * symbol: "USDEUR",
     * bid: 0.878688,
     * ask: 0.878681,
     * price: 0.878681,
     * timestamp: 1540832478
     * },
     * {
     * symbol: "USDRUB",
     * bid: 65.763,
     * ask: 65.7727,
     * price: 65.7679,
     * timestamp: 1540832478
     * }
     * ]
     * @param jsonResponse
     * @return объект Forge1Response
     */
    private Forge1Response parseJsonResponse(JsonNode jsonResponse) {
        JSONArray jsonArray = jsonResponse.getArray();

        List<Forge1CurrencyInfo> currencyInfoList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonElement = jsonArray.getJSONObject(i);

            String symbol = jsonElement.getString("symbol");
            double bid = jsonElement.getDouble("bid");
            double ask = jsonElement.getDouble("ask");
            double price = jsonElement.getDouble("price");
            long timestamp = jsonElement.getLong("timestamp");

            currencyInfoList.add(new Forge1CurrencyInfo(symbol, bid, ask, price, timestamp));
        }

        Forge1Response forge1Response = new Forge1Response(currencyInfoList);

        return forge1Response;
    }
}
