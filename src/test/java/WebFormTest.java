import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

// java -jar ./artifacts/app-order.jar &


public class WebFormTest {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.headless = true; // можно выключить при локальном запуске
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000;
    }

    @Test
    void testLocalhost() {
        // 1. Открываем веб-страницу формы, которая должна быть доступна по адресу http://localhost:9999
        open("http://localhost:9999");

        // 2. Вводим имя в поле ввода с атрибутом name='name'
        $("input[name='name']").setValue("Иванов Иван");

        // 3. Вводим номер телефона в поле с атрибутом name='phone'
        $("input[name='phone']").setValue("+79998887766");

        // 4. Ставим галочку на чекбоксе согласия (по data-test-id)
        $("[data-test-id='agreement']").click();

        // 5. Нажимаем кнопку отправки формы (класс button)
        $("button.button").click();

        // 6. Проверяем, что появилось сообщение об успешной отправке заявки
        $("[data-test-id='order-success']")
                .shouldBe(visible) // Элемент должен быть видимым
                .shouldHave(text("Ваша заявка успешно отправлена!")); // И содержать ожидаемый текст
    }
}