package ru.netology.web.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.StartingPage;

import static com.codeborne.selenide.Selenide.open;

public class BuyWithCreditTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:8080");
    }

    @Test
    void shouldBuySuccessfullyWithApprovedCard() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = DataHelper.getApprovedCardNumber();
        buyWithCreditPage.withoutCardNumber(number);
        buyWithCreditPage.getSuccessMessage();
    }

    @Test
    void shouldNotSellWithDeclinedCard() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = DataHelper.getDeclinedCardNumber();
        buyWithCreditPage.withoutCardNumber(number);
        buyWithCreditPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "0000 0000 0000 0000";
        buyWithCreditPage.withoutCardNumber(number);
        buyWithCreditPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsUnknown() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "2334 5698 4531 0076";
        buyWithCreditPage.withoutCardNumber(number);
        buyWithCreditPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "4532 7658 2031 97";
        buyWithCreditPage.withoutCardNumber(number);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "00";
        buyWithCreditPage.withoutMonth(monthNumber);
        buyWithCreditPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "3";
        buyWithCreditPage.withoutMonth(monthNumber);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "23";
        buyWithCreditPage.withoutMonth(monthNumber);
        buyWithCreditPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "00";
        buyWithCreditPage.withoutYear(yearNumber);
        buyWithCreditPage.getErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "4";
        buyWithCreditPage.withoutYear(yearNumber);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "28";
        buyWithCreditPage.withoutYear(yearNumber);
        buyWithCreditPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberLowerThanAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "20";
        buyWithCreditPage.withoutYear(yearNumber);
        buyWithCreditPage.getErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = "N";
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = "QWERTYUIOPASDFGHJKLZXCVBNM PLMNJKOIUHBVGYTFCXDRESZAQW";
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInLowcaseLetters();
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInUppercaseAndLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInUppercaseAndLowcaseLetters();
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCreditPage.withoutCardholder(nameOfCardHolder);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val cvc = "42";
        buyWithCreditPage.withoutCardValidationCode(cvc);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val cvc = "8";
        buyWithCreditPage.withoutCardValidationCode(cvc);
        buyWithCreditPage.getErrorMessageAboutWrongFormat();
    }
}

