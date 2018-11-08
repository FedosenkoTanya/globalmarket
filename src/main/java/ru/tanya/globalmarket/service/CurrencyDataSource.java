package ru.tanya.globalmarket.service;

import ru.tanya.globalmarket.model.CurrencyNominal;

import java.time.LocalDate;

/**
 * Класс для получения данных о валюте
 */
public interface CurrencyDataSource {

    /**
     * Метод возвращает информацию о валюте за указанную дату
     */
    CurrencyNominal getCurrencyNominalByDate(LocalDate date) throws Exception;
}
