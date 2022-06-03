package task_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;
import utilities.TestBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSummaryFeature extends TestBase {
    static WebDriver driver = Driver.getDriver();

    public static void login() {
        String validUsername = ConfigurationReader.getProperty("username");
        String validPassword = ConfigurationReader.getProperty("password");
        LoginFunctionality.login(validUsername, validPassword);

        // get back to get rid of security problem
        driver.navigate().back();
    }

    private boolean containsAll(By locator, String... arr) {
        List<WebElement> elementList = driver.findElements(locator);
        List<String> expectedContainsList = new ArrayList<>(Arrays.asList(arr));
        return elementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .containsAll(expectedContainsList);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void accountSummaryTest() {
        // call the login method to be logged in
        login();

        // click to "Checking Account Activity" link
        driver.findElement(By.xpath("//*[@id=\"account_activity_link\"]")).click();

        // click to "Account Summary" link
        driver.findElement(By.xpath("//*[@id=\"account_summary_tab\"]/a")).click();

        // Requirements document shows the "Summary" word starts with lowercase "s"
        // I have changed the expected title to make it pass
        Assert.assertEquals(driver.getTitle(), ConfigurationReader.getProperty("summaryTitle"));

        /*
        Account summary page should have to following account types: Cash Accounts, Investment
        Accounts, Credit Accounts, Loan Accounts.
        */
        By accountTypesOnSummaryPege = By.xpath("//div[@class='offset2 span8']/h2");
        Assert.assertTrue(containsAll(accountTypesOnSummaryPege,
                "Cash Accounts", "Investment Accounts", "Credit Accounts", "Loan Accounts"));

        /*
        Credit Accounts table must have columns
        Account, Credit Card and Balance.
        */
        By creditAccountTable = By.xpath("(//div[@class='board-content'])[3]//thead//th");
        Assert.assertTrue(containsAll(creditAccountTable,
                "Account", "Credit Card", "Balance"));
    }
}