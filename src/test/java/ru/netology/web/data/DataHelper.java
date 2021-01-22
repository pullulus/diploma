package ru.netology.web.data;

import lombok.Value;

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
}
