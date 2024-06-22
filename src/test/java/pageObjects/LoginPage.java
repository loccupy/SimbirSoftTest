package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriverWait wait;
    By buttonCustomerLoginLocator = By.xpath("//button[text()='Customer Login']");

    public LoginPage(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
    }

    public void clickButtonCustomerLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonCustomerLoginLocator)).click();
    }
}
