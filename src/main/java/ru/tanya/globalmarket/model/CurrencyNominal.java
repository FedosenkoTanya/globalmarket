package ru.tanya.globalmarket.model;

/**
 * TODO: add doc!
 */
public class CurrencyNominal {

    public final double unitUSD;
    public final double unitRUB;
    public final double unitEUR;

    public CurrencyNominal(double unitUSD, double unitRUB, double unitEUR) {
        this.unitUSD = unitUSD;
        this.unitRUB = unitRUB;
        this.unitEUR = unitEUR;
    }

    @Override
    public String toString() {
        return "CurrencyNominal{" +
                "unitUSD=" + unitUSD +
                ", unitRUB=" + unitRUB +
                ", unitEUR=" + unitEUR +
                '}';
    }
}
