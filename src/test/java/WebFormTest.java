import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;
// java -jar ./artifacts/app-order.jar &

public class WebFormTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Настройка ChromeDriver через WebDriverManager
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // фоновый режим
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // закрываем браузер
        }
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        driver.get("http://localhost:9999");

        // Ввод имени
        WebElement nameInput = driver.findElement(By.cssSelector("input[name='name']"));
        nameInput.sendKeys("Иванов Иван");

        // Ввод телефона
        WebElement phoneInput = driver.findElement(By.cssSelector("input[name='phone']"));
        phoneInput.sendKeys("+79991234567");

        // Установка галочки
        WebElement agreementCheckbox = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        agreementCheckbox.click();

        // Клик по кнопке отправки
        WebElement submitButton = driver.findElement(By.cssSelector("button.button"));
        submitButton.click();

        // Проверка успешного сообщения
        WebElement successMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        String actualText = successMessage.getText().trim();

        assertTrue(actualText.contains("Ваша заявка успешно отправлена"), "Успешное сообщение не найдено!");
    }
}
