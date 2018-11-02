package ru.tanya.market;

/**
 * Created by Stud on 27.09.2018.
 */
public class Money {
    public Money(double value, Currency moneyCurrency) {
    this.value = value;
    this.moneyCurrency = moneyCurrency;
}

    public double value;
    public Currency moneyCurrency;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getMoneyCurrency() {
        return moneyCurrency;
    }

    public void setMoneyCurrency(Currency moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    @Override
    public String toString() {
        return  value +
                ", " +
                 moneyCurrency;
    }
}
