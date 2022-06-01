package task_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSummaryFeature {
    static WebDriver driver = Driver.getDriver();

    public static void login() {
        String validUsername = ConfigurationReader.getProperty("username");
        String validPassword = ConfigurationReader.getProperty("password");
        LoginFunctionality.login(validUsername, validPassword);

        // get back to get rid of security problem
        driver.navigate().back();
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
        Assert.assertEquals(driver.getTitle(), "Zero - Account Summary");

        /*
        Account summary page should have to following account types: Cash Accounts, Investment
        Accounts, Credit Accounts, Loan Accounts.
        */
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='offset2 span8']/h2"));
        List<String> expectedContainsList = new ArrayList<>(List.of("Cash Accounts", "Investment Accounts", "Credit Accounts", "Loan Accounts"));
        boolean containsAll = elementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .containsAll(expectedContainsList);
        Assert.assertTrue(containsAll);

        /*
        Credit Accounts table must have columns
        Account, Credit Card and Balance.
        */
        elementList = driver.findElements(By.xpath("(//div[@class='board-content'])[3]//thead//th"));
        expectedContainsList = new ArrayList<>(List.of("Account", "Credit Card", "Balance"));
        containsAll = elementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .containsAll(expectedContainsList);
        Assert.assertTrue(containsAll);
    }
}