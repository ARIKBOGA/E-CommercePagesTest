package task_03;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;

import java.io.File;
import java.io.IOException;

public class LoginFunctionality {
    private static final WebDriver driver = Driver.getDriver();

    @AfterClass
    private void tearDown() {
        driver.quit();
    }

    @AfterMethod // get screenshot if the test is failed
    private void failureRecord(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("resources/screenShots" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void login(String username, String password) {
        // go to http://zero.webappsecurity.com/
        driver.get(ConfigurationReader.getProperty("env_03"));

        // click to sign in button
        driver.findElement(By.xpath("//button[@id='signin_button']")).click();

        // enter username //input[@id='user_password']
        if (username != null) {
            WebElement usernameInputbox = driver.findElement(By.xpath("//input[@id='user_login']"));
            usernameInputbox.sendKeys(username);
        }

        // enter password
        if (password != null) {
            WebElement passwordInputbox = driver.findElement(By.xpath("//input[@id='user_password']"));
            passwordInputbox.sendKeys(password);
        }
        // click to sign in button
        driver.findElement(By.xpath("//input[@value='Sign in']")).click();
    }

    private void checkAlertMessage() {
        String actualAlertMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[1]")).getText();
        String expectedAlertMessage = ConfigurationReader.getProperty("expectedAlertMessage");
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
    }

    private void checkUrlIfCorrect() {
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://zero.webappsecurity.com/bank/account-summary.html";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    @Test(priority = 2)
    public void loginWithValidCredentials() {
        /*
         According to requirements document, Account summary page should be displayed
         after logging in and getting back with step-back button,
         but it doesn't work like that.
         So, test fails.
         */
        login(ConfigurationReader.getProperty("username"), ConfigurationReader.getProperty("password"));

        // get back to get rid of the security problem
        driver.navigate().back();

        checkUrlIfCorrect();
    }

    @Test
    public void loginWithInvalidUsernameValidPassword() {
        login(ConfigurationReader.getProperty("invalidUsername"), ConfigurationReader.getProperty("password"));
        checkAlertMessage();
    }

    @Test
    public void loginWithValidUsernameInvalidPassword() {
        login(ConfigurationReader.getProperty("username"), ConfigurationReader.getProperty("invalidPassword"));
        checkAlertMessage();
    }

    @Test
    public void loginWithValidUsernameBlankPassword() {
        login(ConfigurationReader.getProperty("username"), null);
        checkAlertMessage();
    }

    @Test
    public void loginWithBlankUsernameValidPassword() {
        login(null, ConfigurationReader.getProperty("password"));
        checkAlertMessage();
    }
}