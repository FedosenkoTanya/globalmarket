package ru.tanya.globalmarket.model.forge1;

/**
 * Модель данных с forge1 хранящая информацию о валютной пары
 */
public class Forge1CurrencyInfo {
    public final String symbol;
    public final double bid;
    public final double ask;
    public final double price;
    public final long timestamp;

    public Forge1CurrencyInfo(String symbol, double bid, double ask, double price, long timestamp) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.price = price;
        this.timestamp = timestamp;
    }
}