package ru.tanya.market;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalculateCost {

    private String pathToCurrencyDirectory;

    public CalculateCost(String pathToCurrencyDirectory) {
        this.pathToCurrencyDirectory = pathToCurrencyDirectory;
    }

    /**
     * Функция возращают сумму всех товаров в задданой валюте, по текущему курсу
     *
     * @param productList список продуктов, для которых нужно посчитать стоимость
     * @param costsCurrency валюта в которой требуется вернуть сумму
     * @return
     */
    public double calculateCosts(List<Product> productList, Currency costsCurrency, LocalDate javaDate) throws IOException {

        String date = javaDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CurrencyFileReader readFile = new CurrencyFileReader(pathToCurrencyDirectory);

        CurrencyConverter currencyConverter = readFile.readDate(readFile, date);


//ru.nikita.market.CurrencyConverter currencyConverter = new ru.nikita.market.CurrencyConverter(readFile.getUnitUSD(),readFile.getUnitRUB(), readFile.getUnitEUR());

        double summ = 0;
        for (Product i : productList) {
            summ += currencyConverter.convertByCurrencty(i.price, costsCurrency).value;
        }
        System.out.println("summ:" + summ);
        return summ;
    }
}