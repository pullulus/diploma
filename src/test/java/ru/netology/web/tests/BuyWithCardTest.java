package ru.netology.web.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.dbUtils.DbRequest;
import ru.netology.web.pages.StartingPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @AfterEach
    void shouldClearAll() {
        DbRequest.shouldDeleteAfterPayment();
    }

    @Test
    void shouldBuySuccessfullyWithApprovedCard() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = DataHelper.getApprovedCardNumber();
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitSuccessMessage();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("APPROVED", paymentInfo.getStatus());

    }

    @Test
    void shouldNotSellWithDeclinedCard() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = DataHelper.getDeclinedCardNumber();
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "0000 0000 0000 0000";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardNumberIsUnknown() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "5467 7398 2231 4567";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessage();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val number = "4567 3274 3100 04";
        buyWithCardPage.withCardNumber(number);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "00";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "5";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val monthNumber = "24";
        buyWithCardPage.withMonth(monthNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "00";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "7";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "27";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val yearNumber = "20";
        buyWithCardPage.withYear(yearNumber);
        buyWithCardPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = "L";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = "LXCVBNMLKJHGFDSAQWERTY NJITRUIOPLKJHGFDSAZXCVBNM";
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInLowcaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInUppercaseAndLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInUppercaseAndLowcaseLetters();
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCardPage.withCardholder(nameOfCardHolder);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        val paymentInfo = DbRequest.getPaymentInfo();
        assertEquals("DECLINED", paymentInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val cvc = "87";
        buyWithCardPage.withCardValidationCode(cvc);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        val cvc = "3";
        buyWithCardPage.withCardValidationCode(cvc);
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenAllFieldsAreEmpty() {
        val startingPage = new StartingPage();
        val buyWithCardPage = startingPage.buyWithCard();
        buyWithCardPage.emptyFields();
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
        buyWithCardPage.waitErrorMessageBecauseOfEmptyField();
        buyWithCardPage.waitErrorMessageAboutWrongFormat();
    }
}
