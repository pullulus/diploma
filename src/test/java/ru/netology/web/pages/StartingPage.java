package ru.netology.web.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartingPage {
    private SelenideElement buyWithCardButton = $(byText("Купить"));
    private SelenideElement buyWithCreditButton = $(byText("Купить в кредит"));

    public BuyWithCardPage buyWithCard() {
        buyWithCardButton.click();
        return new BuyWithCardPage();
    }

    public BuyWithCreditPage buyWithCredit() {
        buyWithCreditButton.click();
        return new BuyWithCreditPage();
    }
}
