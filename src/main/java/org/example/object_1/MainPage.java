package org.example.object_1; // Моя папка для описания страниц

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private final WebDriver driver;


    // Кнопка заказа в самом верху страницы
    private final By topOrderButton = By.className("Button_Button__ra12g");

    // Кнопка заказа где-то посередине страницы
    private final By bottomOrderButton = By.className("Button_Middle__1atDu");

    // Кнопка куки "да все привыкли"
    private final By cookieButton = By.id("rcc-confirm-button");

    // Метод делает локатор для стрелочки вопроса
    private By getAccordionHeaderLocator(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Метод делает локатор для панельки с ответом
    private By getAccordionPanelLocator(int index) {
        return By.id("accordion__panel-" + index);
    }

    // Конструктор, чтобы передавать браузер в этот класс
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Закрываем куки, чтобы баннер ушёл
    public void acceptCookies() {
        if (driver.findElements(cookieButton).size() > 0) {
            WebElement cookieElement = driver.findElement(cookieButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cookieElement);
        }
    }

    //  кликаем на верхнюю кнопку заказа
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Скроллим до нижней кнопки заказа по центру и нажимаем на неё
    public void clickBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Скроллим до нужного вопроса и нажимаем на него
    public void clickAccordionHeader(int index) {
        WebElement element = driver.findElement(getAccordionHeaderLocator(index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Ждём 3 секунды, пока появится текст ответа
    public String getAccordionAnswerText(int index) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(getAccordionPanelLocator(index)));
        return driver.findElement(getAccordionPanelLocator(index)).getText();
    }
}
