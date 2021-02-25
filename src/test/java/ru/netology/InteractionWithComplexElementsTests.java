package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class InteractionWithComplexElementsTests {

    @Test
    void shouldDeliverCardFormWithInteractionWithComplexElements() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(" .menu-item__control").find(Condition.exactText("Санкт-Петербург")).click();
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeetingDefault = LocalDate.now().plusDays(3);
        LocalDate dataOfMeetingNotDefault = LocalDate.now().plusDays(7);
        String inputData = dataOfMeetingNotDefault.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int inputDayOfMounth = dataOfMeetingNotDefault.getDayOfMonth();
        if (dataOfMeetingDefault.getMonthValue() == dataOfMeetingNotDefault.getMonthValue()) {
            $$(".popup__container .calendar .calendar__day").
                    find(Condition.exactText(String.valueOf(inputDayOfMounth))).click();
        }
        if (dataOfMeetingDefault.getMonthValue() != dataOfMeetingNotDefault.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
            $$(".popup__container .calendar .calendar__day").
                    find(Condition.exactText(String.valueOf(inputDayOfMounth))).click();
        }
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=notification] .notification__content").
                shouldBe(Condition.visible, Duration.ofMillis(15000)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + inputData));
    }
}

