package ru.tanya.market;

/**
 * Created by Stud on 27.09.2018.
 */
public class CurrencyConverter {
    private final double unitUSD;
    private final double unitRUB;
    private final double unitEUR;

    public CurrencyConverter(double unitUSD, double unitRUB, double unitEUR) {
        this.unitUSD = unitUSD;
        this.unitRUB = unitRUB;
        this.unitEUR = unitEUR;
    }

    public Money convertByCurrencty(Money money, Currency convertToCurrency) {

        if (money.moneyCurrency != convertToCurrency) {

            Money returnMoney = new Money(0, convertToCurrency);
            double unitValue = 0;

            switch (convertToCurrency) {
                case RUB: {
                    unitValue = money.value * unitRUB;
                    break;
                }
                case EUR: {
                    unitValue = money.value * unitEUR;
                    break;
                }
                case USD: {
                    unitValue = money.value * unitUSD;
                    break;
                }
            }


            returnMoney.value = unitValue;
            return returnMoney;
        } else {

            return money;
        }
    }


   public double getUnitUSD() {
       return unitUSD;
   }

    public double getUnitRUB() {
        return unitRUB;
    }

    public double getUnitEUR() {
        return unitEUR;
    }

    // @Override
    //public String toString() {
    //return "ru.nikita.market.CurrencyConverter{" +
    //"unitUSD=" + unitUSD +
    // ", unitRUB=" + unitRUB +
    // ", unitEUR=" + unitEUR +
    // '}';
    // }
}
