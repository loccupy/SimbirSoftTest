package org.example;

import baseTest.TestConfig;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.AccountPage;
import pageObjects.CustomerPage;
import pageObjects.LoginPage;
import pageObjects.TransactionsListPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Utils.getAmountWithFibonacci;
import static utils.Utils.writeFileCsv;

@Feature("Тестирование по заданию SimbirSoft")
public class GlobalsQaTests extends TestConfig {

    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;
    CustomerPage customerPage;
    AccountPage accountPage;
    String amount;

    @BeforeEach
    void before() {
        driver = getDriver();
        wait = getWait();

        step("Открыть первую страницу сайта", () ->
                driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login;"));

        loginPage = new LoginPage(wait, driver);
        step("Нажать кнопку Customer Login", () ->
                loginPage
                        .clickButtonCustomerLogin());

        customerPage = new CustomerPage(wait, driver);
        step("Залогиниться как Harry Potter ", () ->
                customerPage
                        .clickUserSelectField()
                        .selectUserHarryPotter()
                        .clickButtonLogin());

        accountPage = new AccountPage(wait, driver);
        amount = getAmountWithFibonacci();
        step("Перейти в аккаунт, выполнить пополнение и снятие", () ->
                accountPage
                        .clickButtonDeposit()
                        .sendAmountInFieldDeposit(amount)
                        .clickSubmit()
                        .clickButtonWithDrawn()
                        .sendAmountInFieldWithDrawn(amount)
                        .clickSubmit());
    }

    @Test
    @DisplayName("Проверка баланса после проведения пополнения и снятия")
    public void checkBalanceTest() {
        int balance = step("Получить значение баланса после проведения пополнения и снятия", () ->
                accountPage.getBalance());

        step("Сравнить значения баланса с ожидаемым результатом", () -> {
            assertThat(balance)
                    .as("Баланс не равен нулю")
                    .isEqualTo(0);
        });
    }

    @Test
    @DisplayName("Проверка таблицы с проведенными транзакциями")
    public void checkTransactionsTableTest() throws Exception {
        SoftAssertions assertions = new SoftAssertions();
        String expectedDate = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru")));

        step("Перейти на страницу с таблицей проведенных транзакций", () ->
                accountPage.clickButtonTransactions());

        TransactionsListPage transactionsListPage = new TransactionsListPage(wait, driver);

        List<HashMap<String, String>> actualValue = step("Получить список проведенных транзакций",
                transactionsListPage::getListAllTransactions);

        step("Создать csv файл со всеми транзакциями", () ->
                writeFileCsv(actualValue));

        int numberOfTransactions = step("Получить количество транзакций", actualValue::size);
        String typeFirstTransaction = step("Получить тип первой транзакции", () ->
                actualValue.get(0).get("type"));
        String amountFirstTransaction = step("Получить сумму первой транзакции", () ->
                actualValue.get(0).get("amount"));
        String typeSecondTransaction = step("Получить тип второй транзакции", () ->
                actualValue.get(1).get("type"));
        String amountSecondTransaction = step("Получить сумму второй транзакции", () ->
                actualValue.get(1).get("amount"));
        String dataFirstTransaction = step("Получить дату первой транзакции", () ->
                        actualValue.get(0).get("date"));
        String dataSecondTransaction = step("Получить дату второй транзакции", () ->
                actualValue.get(1).get("date"));

        step("Сравнить значения в таблице с ожидаемым результатом", () -> {
            assertions
                    .assertThat(numberOfTransactions)
                    .as("Количество транзакций не соответствует")
                    .isEqualTo(2);
            assertions
                    .assertThat(amountFirstTransaction)
                    .as("Сумма первой транзакции не соответствует")
                    .isEqualTo(amount);
            assertions
                    .assertThat(amountSecondTransaction)
                    .as("Сумма второй транзакции не соответствует")
                    .isEqualTo(amount);
            assertions
                    .assertThat(typeFirstTransaction)
                    .as("Тип первой транзакции не соответствует")
                    .isEqualTo("Credit");
            assertions
                    .assertThat(typeSecondTransaction)
                    .as("Тип второй транзакции не соответствует")
                    .isEqualTo("Debit");
            assertions
                    .assertThat(dataFirstTransaction)
                    .as("Дата транзакции не соответствует")
                    .contains(expectedDate);
            assertions
                    .assertThat(dataSecondTransaction)
                    .as("Дата транзакции не соответствует")
                    .contains(expectedDate);
        });
        assertions.assertAll();
        transactionsListPage.getCsvFileInReporter("src/test/resources/new.csv");
    }
}
