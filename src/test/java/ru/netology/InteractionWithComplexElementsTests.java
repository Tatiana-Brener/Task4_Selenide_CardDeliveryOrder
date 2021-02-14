package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class InteractionWithComplexElementsTests {

    @Test
    void shouldDeliverCardFormIfFieldCityIsFilledWithTwoLetters1 () {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ка");
        $$(" .menu-item__control").find(Condition.exactText("Казань")).click();
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
    void shouldDeliverCardFormIfDateOfMeetingIsChooseUsingCalendarThisMounth() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(" .menu-item__control").find(Condition.exactText("Санкт-Петербург")).click();
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(5);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".calendar-input__custom-control [type=button]").click();
        SelenideElement blockCalendar = $(".popup__container .calendar");
        blockCalendar.$(".popup__container .calendar__day[data-day='1613682000000']").click();
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputData));
    }

    @Test
    void shouldDeliverCardFormIfDateOfMeetingIsChooseUsingCalendarNextMounth() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(" .menu-item__control").find(Condition.exactText("Санкт-Петербург")).click();
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(19);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(".calendar-input__custom-control [type=button]").click();
        SelenideElement blockCalendar = $(".popup__container .calendar");
        blockCalendar.$("[data-step='1']").click();
        blockCalendar.$(".calendar__name").shouldHave(Condition.exactText("Март 2021"));
        blockCalendar.$(".popup__container .calendar__day[data-day='1614891600000']").click();
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputData));
    }
}
