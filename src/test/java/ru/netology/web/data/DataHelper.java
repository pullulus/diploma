package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static String generateMonthNumber() {
        String[] monthNumbers = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int itemIndex = (int) (Math.random() * monthNumbers.length);
        return monthNumbers[itemIndex];
    }

    public static String generateYearNumber() {
        String[] yearNumbers = new String[]{"21", "22", "23", "24", "25", "26"};
        int itemIndex = (int) (Math.random() * yearNumbers.length);
        return yearNumbers[itemIndex];
    }

    @Value
    public static class CardInfo {
        String number;
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("4444 4444 4444 4441");
    }

    @Value
    public static class UsersName {
        String name;
    }

    public static UsersName getUsersName(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return new UsersName(
                faker.name().name().toUpperCase(Locale.ROOT));
    }

    @Value
    public static class CVCNumber {
        int number;
    }

    public static CVCNumber getCVCNumber() {
        Faker faker = new Faker();
        return new CVCNumber(
                faker.number().numberBetween(001, 999));
    }
}
