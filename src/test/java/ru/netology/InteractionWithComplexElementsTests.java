package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class InteractionWithComplexElementsTests {
    LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
    String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldDeliverCardFormIfFieldCityIsFilledWithTwoLetters1 () {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ка");
        $$(" .menu-item__control").find(Condition.exactText("Казань")).click();
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputData));
    }

    @Test
    void shouldDeliverCardFormIfFieldCityIsFilledWithTwoLetters2() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(" .menu-item__control").find(Condition.exactText("Санкт-Петербург")).click();
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputData));
    }

    @Test
    void shouldDeliverCardFormIfDateOfMeetingIsChooseUsingCalendar() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(" .menu-item__control").find(Condition.exactText("Санкт-Петербург")).click();
        $(".calendar-input__custom-control [type=button]").click();
        SelenideElement blockCalendar = $(".popup__container .calendar");
        blockCalendar.$$(".calendar__day").find(Condition.exactText("16")).click();
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + "16.02.2021"));
    }
}
