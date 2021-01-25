package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class BuyPage {
    private SelenideElement heading = $(byText("Оплата по карте"));
    private SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder= '08']");
    private SelenideElement year = $("[placeholder= '22']");
    private SelenideElement cardholder = $$(".input__control").get(3);
    private SelenideElement cardValidationCode = $("[placeholder= '999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(withText("Операция одобрена Банком"));


    public BuyPage() {
        heading.shouldBe(Condition.visible);
    }

    public BuyPage buySuccesfully() {
        cardNumber.setValue(DataHelper.getApprovedCardInfo().getNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue(DataHelper.getUsersName("ru").getName());
        cardValidationCode.setValue(String.valueOf(DataHelper.getCVCNumber().getNumber()));
        continueButton.click();
        successMessage.waitUntil(Condition.visible, 15000);
        return new BuyPage();
    }
}