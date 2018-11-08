package ru.tanya.globalmarket.service;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.tanya.globalmarket.model.CurrencyNominal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

public class Reader {
    public CurrencyNominal getCurr(String filename, String path) {
        try {
            Double k[] = new Double[3];
            File file = new File(path, filename);

            System.out.println("File path: " + file.getAbsolutePath());
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                String nextLine = scanner.nextLine();
                Scanner num = new Scanner(nextLine);
                k[0] = num.nextDouble();
                k[1] = num.nextDouble();
                k[2] = num.nextDouble();
                CurrencyNominal currencyNominal = new CurrencyNominal(k[0], k[1], k[2]);
                //System.out.println(currencyNominal);
                return currencyNominal;
            }
        } catch (Throwable e) {
        }
        return null;
    }


    public CurrencyNominal getCurrencies_from_site(LocalDate data) throws Throwable {
        Double k[] = new Double[3];
        //File file = new File(path, filename);
        URL url = new URL("http://www.cbr.ru/scripts/XML_daily_eng.asp?date_req=" + String.format("%02d", data.getDayOfMonth()) + "/" + data.getMonthValue() + "/" + data.getYear());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 200) {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = inputStream.readLine()) != null) {
                stringBuilder.append(s);
            }
            System.out.println(stringBuilder);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            ByteArrayInputStream input = new ByteArrayInputStream(
                    stringBuilder.toString().getBytes("UTF-8"));
            Document doc = builder.parse(input);
            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("Valute");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getAttribute("ID");
                    if (id.equals("R01239")) {
                        k[1] = Double.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent()
                                .replace(",", "."));
                    }
                    if (id.equals("R01235")) {
                        k[2] = Double.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent()
                                .replace(",", "."));
                    }
                }
            }
            k[0] = 1.;
            //FileWriter fileWriter = new FileWriter(path + filename);
            //fileWriter.write("1 " + k[1].toString().replace(".", ",") + " " + k[2].toString().replace(".", ","));
            //fileWriter.close();
        }
        CurrencyNominal currencyNominal = new CurrencyNominal(k[0], k[1], k[2]);

        return currencyNominal;
    }

}
