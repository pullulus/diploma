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

public class BuyWithCardTest {

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
        val buyWithCardPage = startingPage.buyWithCard();
        val number = DataHelper.getApprovedCardNumber();
        buyWithCardPage.withoutCardNumber(number);
        buyWithCardPage.getSuccessMessage();
    }

    @Test
    void shouldNotSellWithDeclinedCard() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = DataHelper.getDeclinedCardNumber();
        buyWithCardPage.withoutCardNumber(number);
        buyWithCardPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "0000 0000 0000 0000";
        buyWithCardPage.withoutCardNumber(number);
        buyWithCardPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsUnknown() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "5467 7398 2231 4567";
        buyWithCardPage.withoutCardNumber(number);
        buyWithCardPage.getErrorMessage();
    }

    @Test
    void shouldNotSellWhenCardNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "4567 3274 3100 04";
        buyWithCardPage.withoutCardNumber(number);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "00";
        buyWithCardPage.withoutMonth(monthNumber);
        buyWithCardPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "5";
        buyWithCardPage.withoutMonth(monthNumber);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "24";
        buyWithCardPage.withoutMonth(monthNumber);
        buyWithCardPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "00";
        buyWithCardPage.withoutYear(yearNumber);
        buyWithCardPage.getErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "7";
        buyWithCardPage.withoutYear(yearNumber);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "27";
        buyWithCardPage.withoutYear(yearNumber);
        buyWithCardPage.getErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberLowerThanAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "20";
        buyWithCardPage.withoutYear(yearNumber);
        buyWithCardPage.getErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = "L";
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = "LXCVBNMLKJHGFDSAQWERTY NJITRUIOPLKJHGFDSAZXCVBNM";
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInLowcaseLetters();
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInUppercaseAndLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInUppercaseAndLowcaseLetters();
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCardPage.withoutCardholder(nameOfCardHolder);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val cvc = "87";
        buyWithCardPage.withoutCardValidationCode(cvc);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val cvc = "3";
        buyWithCardPage.withoutCardValidationCode(cvc);
        buyWithCardPage.getErrorMessageAboutWrongFormat();
    }
}
