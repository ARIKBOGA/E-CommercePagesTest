package task_02;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.Driver;

public class Task2 {
    static WebDriver driver;

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void task_2Test(){

        driver = Driver.getDriver();

        // 1. Navigate to: https://moneygaming.qa.gameaccount.com/
        driver.get("https://moneygaming.qa.gameaccount.com/");


        // 2. Click the JOIN NOW button to open the registration page





        // 3. Select a title value from the dropdown
        // 4. Enter your first name and surname in the form
        // 5. Check the tick box with text 'I accept the Terms and Conditions and certify that I am over
        //the age of 18.'
        // 6. Submit the form by clicking the JOIN NOW button
        // 7. Validate that a validation message with text ‘ This field is required’
        // appears under the date of birth box
    }

}