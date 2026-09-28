package object_2; // Моя папка для тестов

import org.example.object_1.MainPage; // Подтягиваем главную страницу из первой папки
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) // Наш тест будет параметризованным
public class MainPageTest {

    private WebDriver driver;
    private final int index;          // Сюда по очереди будут подставляться номера строк от 0 до 7
    private final String expectedText; // Сюда будет подставляться правильный текст ответа

    public MainPageTest(int index, String expectedText) {
        this.index = index;
        this.expectedText = expectedText;
    }

    // Набор тестовых данных
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    @Before
    public void setUp() {

        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("https://praktikum-services.ru");
    }

    @Test
    public void testAccordionAnswers() {
        MainPage mainPage = new MainPage(driver); // Создаем объект главной страницы
        mainPage.acceptCookies();                // Сразу убираем плашку куки через наш JS-метод

        mainPage.clickAccordionHeader(index);    // Раскрываем нужную строчку вопроса
        String actualText = mainPage.getAccordionAnswerText(index); // Забираем текст ответа

        // Сверяем текст с сайта с моим из таблицы
        assertEquals("Текст ответа в аккордеоне не совпадает!", expectedText, actualText);
    }

    @After
    public void tearDown() {
        driver.quit(); //  закрываем браузер
    }
}
