package org.example.object_1; // Моя папка для описания страниц

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {

    private final WebDriver driver;


    // Первое окошко: личные данные
    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.className("select-search__input");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Второе окошко: про аренду
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//button[text()='Заказать']");

    // Финальные
    private final By confirmOrderButton = By.xpath(".//button[text()='Да']");
    private final By orderSuccessHeader = By.className("Order_ModalHeader__3FdaJ");

    // Конструктор
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполняем первую страничку с личными данными
    public void fillFirstOrderForm(String firstName, String lastName, String address, String metroName, String phone) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);

        // выбор метро через клавиатуру
        driver.findElement(metroStationField).click();
        driver.findElement(metroStationField).sendKeys(metroName);
        driver.findElement(metroStationField).sendKeys(Keys.DOWN, Keys.ENTER);

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполняем вторую страничку про аренду
    public void fillSecondOrderForm(String date, String period, String color, String comment) {

        // Ждём, пока страничка переключится и появится поле даты
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(dateField));
        driver.findElement(dateField).sendKeys(date, Keys.ENTER);

        // Выбираем срок аренды из списка
        driver.findElement(rentalPeriodDropdown).click();
        By periodOption = By.xpath(".//div[@class='Dropdown-option' and text()='" + period + "']");
        driver.findElement(periodOption).click();

        // Выбираем цвет
        if (color != null && !color.isEmpty()) {
            By colorCheckbox = By.id(color);
            driver.findElement(colorCheckbox).click();
        }

        driver.findElement(commentField).sendKeys(comment);

        // Кликаем по кнопке Заказать
        WebElement orderBtnElement = driver.findElement(orderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", orderBtnElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderBtnElement);
    }

    // Нажимаем Да, в окне подтверждения
    public void clickConfirmOrder() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        driver.findElement(confirmOrderButton).click();
    }

    // Проверяем,что заказ готов
    public boolean isOrderSuccessModalDisplayed() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
        return driver.findElement(orderSuccessHeader).getText().contains("Заказ оформлен");
    }
}
