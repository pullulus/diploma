package ru.netology.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class BuyWithCardPage {
    private SelenideElement heading = $(byText("Оплата по карте"));
    private SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder= '08']");
    private SelenideElement year = $("[placeholder= '22']");
    private SelenideElement cardholder = $$(".input__control").get(3);
    private SelenideElement cardValidationCode = $("[placeholder= '999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(withText("Операция одобрена Банком"));
    private SelenideElement errorMessage = $(withText("Банк отказал в проведении операции"));
    private SelenideElement errorMessageAboutWrongFormat = $(byText("Неверный формат"));
    private SelenideElement errorMessageAboutWrongDateOfExpiry = $(byText("Неверно указан срок действия карты"));
    private SelenideElement errorMessageWithDateOfExpiry = $(byText("Истёк срок действия карты"));
    private SelenideElement errorMessageBecauseOfEmptyField = $(byText("Поле обязательно для заполнения"));

    public BuyWithCardPage() {
        heading.shouldBe(Condition.visible);
    }

    public void withoutCardNumber(String number) {
        cardNumber.setValue(number);
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withoutMonth(String monthNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(monthNumber);
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withoutYear(String yearNumber) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(yearNumber);
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withoutCardholder(String nameOfCardholder) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(nameOfCardholder);
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber()));
        continueButton.click();
    }

    public void withoutCardValidationCode(String cvc) {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getFullUsersName());
        cardValidationCode.setValue(cvc);
        continueButton.click();
    }

    public void emptyFields() {
        continueButton.click();
    }

    public void getSuccessMessage() {
        successMessage.waitUntil(Condition.visible, 15000);
    }

    public void getErrorMessage() {
        errorMessage.waitUntil(Condition.visible, 15000);
    }

    public void getErrorMessageAboutWrongFormat() {
        errorMessageAboutWrongFormat.waitUntil(Condition.visible, 5000);
    }

    public void getErrorMessageAboutWrongDateOfExpiry() {
        errorMessageAboutWrongDateOfExpiry.waitUntil(Condition.visible, 5000);
    }

    public void getErrorMessageWithDateOfExpiry() {
        errorMessageWithDateOfExpiry.waitUntil(Condition.visible, 5000);
    }

    public void getErrorMessageBecauseOfEmptyField() {
        errorMessageBecauseOfEmptyField.waitUntil(Condition.visible, 10000);
    }
}
