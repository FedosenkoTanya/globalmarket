package ru.tanya.globalmarket.service;

import ru.tanya.globalmarket.model.Currency;
import ru.tanya.globalmarket.model.CurrencyNominal;
import ru.tanya.globalmarket.model.Money;

/**
 * Класс для конвертации денег из одной валюты в другую
 */
public class CurrencyConverter {

    private CurrencyNominal currencyNominal;

    public CurrencyConverter(CurrencyNominal currencyNominal) {
        this.currencyNominal = currencyNominal;
    }

    public Money transformMoneyCurrency(Money money, Currency convertToCurrency) {
        //проверяем на совпадение исходной валюты и валюты, в которую конвертируем
        if (money.getMoneyCurrency() != convertToCurrency) {
            //если конвертируемая валюта не равна заданной тогда расчитываем стоимость в заданной валюте

            Money returnMoney = new Money(0, convertToCurrency);
            double unitValue = 0;

            switch (convertToCurrency) {
                case RUB: {
                    unitValue = money.getValue() * currencyNominal.unitRUB;
                    System.out.println(unitValue);
                    break;
                }
                case EUR: {
                    unitValue = money.getValue() * currencyNominal.unitEUR;
                    break;
                }
                case USD: {
                    unitValue = money.getValue() * currencyNominal.unitUSD;
                    break;
                }
            }

            //записываем вычисленную сумму и возращаем результат
            returnMoney.setValue(unitValue);
            return returnMoney;
        } else {
            //возращаем сумму в исходной валюте т.к. конвертируемая валюта равна исходной
            return money;
        }
    }
}
