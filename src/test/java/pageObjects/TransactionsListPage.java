package pageObjects;

import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static utils.Utils.formatterTransactionDate;
import static utils.Utils.parseTable;

public class TransactionsListPage {

    private final WebDriverWait wait;
    private final WebDriver driver;
    By allTransactionsListLocator = By.xpath("//table//tbody/tr");
    By transactionDateLocator = By.xpath("td[1]");
    By transactionAmountLocator = By.xpath("td[2]");
    By transactionTypeLocator = By.xpath("td[3]");

    public TransactionsListPage(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        this.driver = driver;
    }

    public  List<HashMap<String, String>> getListAllTransactions() throws ParseException {
        List<WebElement> table = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allTransactionsListLocator));
        List<HashMap<String, String>> allTransactions = new ArrayList<>();

        for (int i = 1; i <= table.size(); i++) {
            WebElement transaction = driver.findElement(By.xpath(String.format("//table//tbody/tr[%s]", i)));

            String transactionDate = formatterTransactionDate(transaction.findElement(transactionDateLocator).getText());
            String transactionAmount = transaction.findElement(transactionAmountLocator).getText();
            String transactionType = transaction.findElement(transactionTypeLocator).getText();

            HashMap<String, String> oneTransaction = parseTable(transactionDate, transactionAmount, transactionType);
            allTransactions.add(oneTransaction);
        }
        return allTransactions;
    }

    @Attachment(value = "CSV File")
    public byte[] sendTransactionDataToAllureReport(String fileName) throws Exception {
        File file = new File(fileName);
        return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }
}
