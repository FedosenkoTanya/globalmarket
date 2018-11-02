package ru.tanya.market;

import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyFileReader {

    public static final String API_KEY = "5XPwGVnlanL7m4LKAnMNTJdptuKVyK8Q";

    private CurrencyConverter read(String path, String date) throws IOException {
        File file = new File(path + date + ".txt");
        Scanner sc = null;

        try {

            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
//.trim() осуществляет обрезание пробелов
                String[] arraylist = line.split(",");

                CurrencyConverter currencyConverter = new CurrencyConverter(
                        Double.parseDouble(arraylist[0].trim()),
                        Double.parseDouble(arraylist[1].trim()),
                        Double.parseDouble(arraylist[2].trim()));


                return currencyConverter;
            }
            throw new RuntimeException("Нет данных в файле");
        }
        catch (FileNotFoundException e) {

            String url = "https://forex.1forge.com/1.0.3/quotes?pairs=USDEUR,USDRUB,AUD.." + API_KEY;

            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonParser parser = new JsonParser();
            JsonElement tradeel = parser.parse(response.toString());
            JsonArray trade = tradeel.getAsJsonArray();

            double eurBid = 0;
            double rubBid = 0;
            for (int i = 0; i < trade.size(); i++) {
                if (trade.get(i).getAsJsonObject().get("symbol").getAsString().equals("USDEUR")) {
                    eurBid = trade.get(i).getAsJsonObject().get("price").getAsDouble();
                    System.out.println(eurBid);
                } else if (trade.get(i).getAsJsonObject().get("symbol").getAsString().equals("USDRUB")) {
                    rubBid = trade.get(i).getAsJsonObject().get("price").getAsDouble();
                    System.out.println(rubBid);
                }
            }

            CurrencyConverter converter = new CurrencyConverter(1, rubBid, eurBid);

//запись в файл



            File newfile = new File(path + date + ".txt");
            if (newfile.exists()){
                System.out.println("Файл уже существует");
            }
            else {
                newfile.createNewFile();
            }

            FileWriter filew = new FileWriter(newfile);
            BufferedWriter buff = new BufferedWriter(filew);

            for (JsonElement item:trade) {
                System.out.println(item.getAsJsonObject().get("price"));
                buff.write(String.valueOf(item.getAsJsonObject().get("price")));
            }
            buff.close();

            return converter;
        }
        finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

    private String pathToCurrencyDirectory;

    public CurrencyFileReader(String pathToCurrencyDirectory) {
        this.pathToCurrencyDirectory = pathToCurrencyDirectory;
    }

    public CurrencyConverter readDate(CurrencyFileReader currencyFileReader, String date) throws IOException {
        return currencyFileReader.read(pathToCurrencyDirectory, date);
    }


}
