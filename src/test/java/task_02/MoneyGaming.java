package task_02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.Driver;

public class MoneyGaming {
    static WebDriver driver;

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void task_2Test() {

        driver = Driver.getDriver();

        // 1. Navigate to: https://moneygaming.qa.gameaccount.com/
        driver.get("https://moneygaming.qa.gameaccount.com/");


        // 2. Click the JOIN NOW button to open the registration page
        driver.findElement(By.xpath("//a[contains(text(),'Join Now!')]")).click();


        // 3. Select a title value from the dropdown
        Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='title']")));
        dropdown.selectByValue("Mr");


        // 4. Enter your first name and surname in the form
        driver.findElement(By.xpath("//input[@id='forename']")).sendKeys("Edward");
        driver.findElement(By.xpath("//input[@name='map(lastName)']")).sendKeys("Thorp");


        // 5. Check the tick box with text 'I accept the Terms and Conditions and certify that I am over the age of 18.'
        driver.findElement(By.xpath("(//input[@id='checkbox'])[3]")).click();


        // 6. Submit the form by clicking the JOIN NOW button
        driver.findElement(By.xpath("//input[@id='form']")).click();


        // 7. Validate that a validation message with text ‘ This field is required’ appears under the date of birth box
        String actualText = driver.findElement(By.xpath("//select[@id='dobYear']/following-sibling::label[1]")).getText();
        String expectedText = "This field is required";

        Assert.assertEquals(actualText, expectedText);
    }

}