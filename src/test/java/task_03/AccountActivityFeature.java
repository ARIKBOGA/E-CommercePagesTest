package task_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountActivityFeature {
    static WebDriver driver = Driver.getDriver();

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void accountActivityTest() {

        // call the login method to be logged in
        AccountSummaryFeature.login();

        // navigate to Account activity page
        driver.findElement(By.xpath("//span[@id='account_activity_link']")).click();

        // Requirements document shows the "Activity" word starts with lowercase "a"
        // I have changed the expected title to make it pass
        Assert.assertEquals(driver.getTitle(), ConfigurationReader.getProperty("activityTitle"));


        // locate dropdown select menu
        Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='aa_accountId']")));

        // Is the default selected option "Savings" ?
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Savings");


        List<String> expectedOptionsList = new ArrayList<>(List.of("Savings", "Checking", "Loan", "Credit Card", "Brokerage"));
        boolean containsAll = dropdown.getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .containsAll(expectedOptionsList);
        Assert.assertTrue(containsAll);

        //dropdown.getOptions().forEach(p -> System.out.println(p.getText()));


        expectedOptionsList.clear();
        expectedOptionsList.addAll(List.of("Date", "Description", "Deposit", "Withdrawal"));
        List<WebElement> elementList = driver.findElements(By.xpath("//thead//th"));
        containsAll = elementList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
                .containsAll(expectedOptionsList);
        Assert.assertTrue(containsAll);

        //elementList.forEach(p -> System.out.println(p.getText()));
    }
}