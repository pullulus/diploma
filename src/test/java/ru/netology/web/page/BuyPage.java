package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;


import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyPage {
    private SelenideElement heading = $(byText("Оплата по карте"));
    private SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000'] input");
    private SelenideElement month = $("[placeholder= '08'] input");
    private SelenideElement year = $("[placeholder= '22'] input");
    private SelenideElement cardholder = $$(".input__control").get(3);
    private SelenideElement cardValidationCode = $("[placeholder= '999'] input");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(withText("Операция одобрена Банком"));


    public BuyPage() {
        heading.shouldBe(Condition.visible);
    }

    public BuyPage buySuccesfully(DataHelper) {
        cardNumber.setValue(DataHelper.getApprovedCardInfo().getNumber());
        month.setValue(DataHelper.generateMonthNumber());
        year.setValue(DataHelper.generateYearNumber());
        cardholder.setValue();
        cardValidationCode.setValue();
        continueButton.click();
        successMessage.shouldBe(Condition.visible);
        return new BuyPage();
    }
}
