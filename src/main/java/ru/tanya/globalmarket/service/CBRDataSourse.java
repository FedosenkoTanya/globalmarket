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


public class CBRDataSourse implements CurrencyDataSource {

    @Override
    public CurrencyNominal getCurrencyNominalByDate(LocalDate data) throws Exception {
        Double k[] = new Double[3];

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
            //System.out.println(stringBuilder);

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
                    //EUR
                    if (id.equals("R01239")) {
                        k[1] = Double.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent()
                                .replace(",", "."));
                    }
                    //USD
                    if (id.equals("R01235")) {
                        k[2] = Double.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent()
                                .replace(",", "."));
                    }
                }
            }
            k[0] = 1.;

        }

        CurrencyNominal currencyNominal = new CurrencyNominal(k[0], k[2], k[2]/k[1]);
        //System.out.println(currencyNominal);
        return currencyNominal;
    }


}
