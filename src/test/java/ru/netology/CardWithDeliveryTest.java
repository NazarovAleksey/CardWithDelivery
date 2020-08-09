package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class CardWithDeliveryTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String date = formatter.format(LocalDateTime.now().plusDays(3));
    SelenideElement form;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        form = $(".form");
    }

    @Test
    void shouldCardWithDeliverySuccess() {
        form.$("[data-test-id=city] input").setValue("Екатеринбург");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована на")).waitUntil(visible, 15000);
    }

    @Test
    void shouldTheCityFieldBeChecked() {
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $(".input_invalid[data-test-id=city]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTheDateFieldBeChecked(){
        form.$("[data-test-id=city] input").setValue("Екатеринбург");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $(".calendar-input__custom-control").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldTheFIOFieldBeChecked(){
        form.$("[data-test-id=city] input").setValue("Екатеринбург");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id=name]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTheTelephoneFieldBeChecked(){
        form.$("[data-test-id=city] input").setValue("Екатеринбург");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        $("[data-test-id=phone]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCheckboxFieldIsRequired(){
        form.$("[data-test-id=city] input").setValue("Екатеринбург");
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$$("[role=button]").find(exactText("Забронировать")).click();
        form.$("[data-test-id=agreement]").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));;
    }
}