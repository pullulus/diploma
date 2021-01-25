package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.StartingPage;

import static com.codeborne.selenide.Selenide.open;

public class BuyTest {

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
    void shouldBuySuccessfully() {
        val startingPage = new StartingPage();
        val buyPage = startingPage.shouldBuy();
        val newBuyPage = buyPage.buySuccesfully();
    }
}
