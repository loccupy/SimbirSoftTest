package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage {

    private final WebDriverWait wait;
    private final WebDriver driver;
    By buttonDepositLocator = By.xpath("//button[@ng-click='deposit()']");
    By buttonWithDrawnLocator = By.xpath("//button[@ng-click='withdrawl()']");
    By buttonTransactionsLocator = By.xpath("//button[@ng-click='transactions()']");
    By depositInputFieldLocator = By.xpath("//label[text()='Amount to be Deposited :']//following-sibling::input");
    By withdrawnInputFieldLocator = By.xpath("//label[text()='Amount to be Withdrawn :']//following-sibling::input");
    By submitLocator = By.xpath("//button[@type='submit']");
    By amountOnBalanceLocator = By.xpath("//div[@ng-hide='noAccount']//strong[2]");

    public AccountPage(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        this.driver = driver;
    }

    public AccountPage clickButtonDeposit() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonDepositLocator)).click();
        return this;
    }

    public AccountPage clickButtonWithDrawn() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonWithDrawnLocator)).click();
        return this;
    }

    public void clickButtonTransactions() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonTransactionsLocator)).click();
    }

    public AccountPage sendAmountInFieldDeposit(String amount) {
        wait.until(ExpectedConditions.presenceOfElementLocated(depositInputFieldLocator))
                .sendKeys(amount);
        return this;
    }

    public AccountPage sendAmountInFieldWithDrawn(String amount) {
        wait.until(ExpectedConditions.presenceOfElementLocated(withdrawnInputFieldLocator))
                .sendKeys(amount);
        return this;
    }

    public AccountPage clickSubmit() {
        driver.findElement(submitLocator).click();
        return this;
    }

    public Integer getBalance() {
        return Integer.parseInt(driver.findElement(amountOnBalanceLocator).getText());
    }
}
