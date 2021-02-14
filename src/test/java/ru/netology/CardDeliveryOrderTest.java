package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {

    @Test
    void shouldDeliverCardFormAllFieldsFilledCorrectly() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
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
    void shouldDeliverCardFormWithDataOfMeetingMorePlus3Days() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
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
    void shouldNotDeliverCardFormWithDataOfMeetingLessThanInputData() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue("09.02.2021");
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $("[data-test-id=date] .input_invalid .input__sub").
                shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldDeliverCardFormWithDataOfMeetingMorePlus14Days() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(14);
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
    void shouldNotDeliverCardFormIfFieldCityIsEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=city] .input__sub").
                shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotDeliverCardFormIfCityIsNotFromRF() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("City");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=city] .input__sub").
                shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotDeliverCardFormIfFieldNameIsIncorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Ivan Petrov");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=name] .input__sub").shouldHave(Condition.
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotDeliverCardFormIfFieldNameIsEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=name] .input__sub").
                shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotDeliverCardFormIfFieldPhoneIsIncorrect() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("89009000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=phone] .input__sub").
                shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotDeliverCardFormIfFieldPhoneIsEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=phone] .input__sub").
                shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotDeliverCardFormIfCheckboxNotClick() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dataOfMeeting = LocalDate.now().plusDays(3);
        String inputData = dataOfMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(inputData);
        $("[data-test-id='name'] .input__control").setValue("Иван Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79009990000");
        $("[data-test-id='agreement'] .checkbox__box");
        $(".grid-col button").click();
        $(".input_invalid[data-test-id=agreement] .checkbox__text").shouldHave
                (Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}