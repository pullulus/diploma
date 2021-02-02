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

    @AfterEach
    void shouldClearAll() {
        DbRequest.shouldDeleteAfterPayment();
    }

    @Test
    void shouldBuySuccessfullyWithApprovedCard() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = DataHelper.getApprovedCardNumber();
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitSuccessMessage();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("APPROVED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWithDeclinedCard() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = DataHelper.getDeclinedCardNumber();
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessage();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenCardNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "0000 0000 0000 0000";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessage();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenCardNumberIsUnknown() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "2334 5698 4531 0076";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessage();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardNumberIsShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val number = "4532 7658 2031 97";
        buyWithCreditPage.withCardNumber(number);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "00";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenMonthNumberIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "3";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenMonthNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val monthNumber = "23";
        buyWithCreditPage.withMonth(monthNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsNulls() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "00";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsOneDigitalShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "4";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenYearNumberExceedsTheAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "28";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageAboutWrongDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenYearNumberIsLowerThanAllowed() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val yearNumber = "20";
        buyWithCreditPage.withYear(yearNumber);
        buyWithCreditPage.waitErrorMessageWithDateOfExpiry();
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyFirstName() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getOnlyUsersFirstName();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyLastName() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getOnlyUsersLastName();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsOnlyOneLetter() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = "N";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenNameOfCardholderHasLotsOfLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = "QWERTYUIOPASDFGHJKLZXCVBNM PLMNJKOIUHBVGYTFCXDRESZAQW";
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInLowcaseLetters();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus()) ;
    }

    @Test
    void shouldNotSellWhenNameOfCardholderInUppercaseAndLowercaseLetters() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInUppercaseAndLowcaseLetters();
        buyWithCreditPage.withCardholder(nameOfCardHolder);
       buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenNameOfCardholderIsInRussian() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val nameOfCardHolder = DataHelper.getFullUsersNameInRussian("ru");
        buyWithCreditPage.withCardholder(nameOfCardHolder);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        val paymentWithCreditInfo = DbRequest.getPaymentWithCreditInfo();
        assertEquals("DECLINED", paymentWithCreditInfo.getStatus());
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsOneDigitShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val cvc = "42";
        buyWithCreditPage.withCardValidationCode(cvc);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenCardValidationCodeIsTwoDigitsShort() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        val cvc = "8";
        buyWithCreditPage.withCardValidationCode(cvc);
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }

    @Test
    void shouldNotSellWhenAllFieldsAreEmpty() {
        val startingPage = new StartingPage();
        val buyWithCreditPage = startingPage.buyWithCredit();
        buyWithCreditPage.emptyFields();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
        buyWithCreditPage.waitErrorMessageBecauseOfEmptyField();
        buyWithCreditPage.waitErrorMessageAboutWrongFormat();
    }
}

