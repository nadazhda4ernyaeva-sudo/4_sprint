package object_2; // Моя папка для тестов

import org.example.object_1.MainPage;  // Подтягиваем первую страницу
import org.example.object_1.OrderPage; // Подтягиваем вторую страницу
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderPageTest {

    private WebDriver driver;

    // Переменные для параметров заказа
    private final String buttonLocation;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroName;
    private final String phone;
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    // Конструктор для закидывания тестовых данных в переменные
    public OrderPageTest(String buttonLocation, String firstName, String lastName, String address,
                         String metroName, String phone, String date, String period, String color, String comment) {
        this.buttonLocation = buttonLocation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroName = metroName;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    // Тестовые данные
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                // Тест 1: Верхняя кнопка, Надежда Леусенко, Чистые пруды, семеро суток, серый самокат
                {"top", "Надежда", "Леусенко", "ул. Ленина, д. 18", "Чистые пруды", "12345678901", "24.09.2026", "семеро суток", "grey", "Позвонить за час"},
                // Тест 2: Нижняя кнопка, Светлана Иванова, Сокольники, пятеро суток, серый самокат
                {"bottom", "Светлана", "Иванова", "Сталина, 15", "Сокольники", "71112225566", "30.09.2026", "пятеро суток", "grey", "Не звонить"}
        };
    }

    @Before
    public void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // Включаем обычный режим с окном и ставим нормальный размер экрана, чтобы рассмотреть ввод данных
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.get("https://praktikum-services.ru");
    }

    @Test
    public void testScooterOrderFlow() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies(); // Принимаем куки

        //  Выбираем, на какую из двух кнопок «Заказать» нажать на главной
        if ("top".equals(buttonLocation)) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        //  Заполняем первую форму с личными данными
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstOrderForm(firstName, lastName, address, metroName, phone);

        // Заполняем вторую форму про аренду и цвет
        orderPage.fillSecondOrderForm(date, period, color, comment);

        // Нажимаем кнопку подтверждения заказа «Да»
        orderPage.clickConfirmOrder();

        // Проверяем, выскочило ли окно успеха
        boolean isSuccess = orderPage.isOrderSuccessModalDisplayed();
        assertTrue("Модальное окно успешного заказа не отобразилось!", isSuccess);
    }

    @After
    public void tearDown() {
        driver.quit(); //  закрываем браузер
    }
}
