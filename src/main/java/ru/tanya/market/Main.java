package ru.tanya.market;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
//составляем список продуктов
        List<Product> productList = new ArrayList<>();

        Money newZeland = new Money(0, Currency.USD);
        productList.add(new Product("Велингтон", newZeland));

        Money amsterdam = new Money(1, Currency.EUR);
        productList.add(new Product("Амстердам", amsterdam));

        Money novosibirsk = new Money(0, Currency.RUB);
        productList.add(new Product("Новосибирск", novosibirsk));




        String dateString = "2018-09-20";
        LocalDate date1 = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));



        CalculateCost calculateCost = new CalculateCost("C:\\Users\\User\\IdeaProjects\\Bender\\src\\target\\");

        double costs28 = calculateCost.calculateCosts(productList, Currency.RUB, date1);
        printPrice(costs28);


    }


    public static void printPrice(double price) {
        NumberFormat formatter = new DecimalFormat("#0.00");

        System.out.println("Price: " + formatter.format(price));
    }
}


