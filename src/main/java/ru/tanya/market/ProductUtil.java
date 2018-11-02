package ru.tanya.market;




import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ProductUtil {

    private String pathToCurencies;

    public ProductUtil(String pathToCurencies) {
        this.pathToCurencies = pathToCurencies;
    }

    public double calculateCosts(Date date, List<Product> productList) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String newstr = formatter.format(date);

        float USD;
        float RUB;
        float EUR;
        ArrayList a = new ArrayList();

        BufferedReader br = null;
        File file1 = new File(pathToCurencies + newstr + ".txt");

        if (! file1.exists()){
            file1.createNewFile();
        }

        br = new BufferedReader(new FileReader(file1));

        String line;
        while ((line = br.readLine()) != null) {
            a.add(Float.parseFloat(line));

        }


        USD = (float) a.get(0);
        RUB = (float) a.get(1);
        EUR = (float) a.get(2);

        //создаем конвертер валют с курсом на текущий день
        CurrencyConverter currencyConvertor = new CurrencyConverter(USD, RUB, EUR);

        double summ = 0;
        for (Product i : productList) {
            System.out.println("product: " + i);
            summ += currencyConvertor.convertByCurrencty(i.price, Currency.RUB).value;
            //System.out.println(summ);
        }


        return summ;

    }
}
