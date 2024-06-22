package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomerPage {

    private final WebDriverWait wait;
    private final WebDriver driver;
    By userSelectFieldLocator = By.xpath("//select[@id='userSelect']");
    By userHarryPotterLocator = By.xpath("//option[text()='Harry Potter']");
    By buttonLoginLocator = By.xpath("//button[text()='Login']");

    public CustomerPage(WebDriverWait wait, WebDriver driver) {
        this.driver = driver;
        this.wait = wait;
    }

    public CustomerPage clickUserSelectField() {
        wait.until(ExpectedConditions.elementToBeClickable(userSelectFieldLocator)).click();
        return this;
    }

    public CustomerPage selectUserHarryPotter() {
        wait.until(ExpectedConditions.elementToBeClickable(userHarryPotterLocator)).click();
        return this;
    }

    public void clickButtonLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonLoginLocator)).click();
        new AccountPage(wait, driver);
    }
}
