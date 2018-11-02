package ru.tanya.market;

/**
 * Created by Stud on 27.09.2018.
 */
public class Product {
    private String productName;
    public Money price;

    public Product(String productName, Money price) {
        this.productName = productName;
        this.price = price;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return productName + '\'' +
                ", price=" + price +
                '}';
    }
}
