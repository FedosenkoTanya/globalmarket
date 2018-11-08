package ru.tanya.globalmarket.service;

import ru.tanya.globalmarket.model.CurrencyNominal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Класс для получения данных о валюте из файлового источника данных
 */
public class FileDataSource implements CurrencyDataSource {

    private final String pathToFiles;

    public FileDataSource(String pathToFiles) {
        this.pathToFiles = pathToFiles;
    }

    private static final String COMMA_SEPARATOR = ",";
    private static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public CurrencyNominal getCurrencyNominalByDate(LocalDate date) throws Exception {
        System.out.println("Получаем информацию о валюте из файла");

        String fileName = getFilePathByDate(date);

        try (Stream<String> fileStream = Files.lines(Paths.get(fileName))) {

            String[] currencyAsStringArray = fileStream.findFirst().get().split(COMMA_SEPARATOR);

            double unitUSD = Double.parseDouble(currencyAsStringArray[0]);
            double unitEUR = Double.parseDouble(currencyAsStringArray[1]);
            double unitRUB = Double.parseDouble(currencyAsStringArray[1]);

            CurrencyNominal returnNominal = new CurrencyNominal(unitUSD, unitEUR, unitRUB);

            return returnNominal;
        }
    }

    /**
     * Функция проверяет существует ли файл с информацией о валюте
     */
    public boolean isFileWithCurrencyExists(LocalDate date) {
        File file = new File(getFilePathByDate(date));
        return file.exists();
    }

    /**
     * Метод сохраняет валюту в файле, имя файла состовляется по дате
     */
    public void saveCurrencyNominal(LocalDate date, CurrencyNominal currencyNominal) throws IOException {
        String fileName = getFilePathByDate(date);
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String currencyText = currencyNominal.unitUSD + COMMA_SEPARATOR + currencyNominal.unitEUR +
                    COMMA_SEPARATOR + currencyNominal.unitRUB;

            fileWriter.write(currencyText);
            fileWriter.flush();
        }
    }

    /**
     * Метод генерирует путь до файла с валютой по дате
     */
    private String getFilePathByDate(LocalDate date) {
        String formattedDate = date.format(DATE_FORMATER);
        return pathToFiles + File.separator + formattedDate + ".txt";
    }
}
