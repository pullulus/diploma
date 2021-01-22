package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartingPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement buyInCreditButton = $(byText("Купить в кредит"));

    public BuyPage shouldBuy() {
        buyButton.click();
        return new BuyPage();
    }
}
