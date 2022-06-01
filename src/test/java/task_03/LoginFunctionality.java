package task_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;

public class LoginFunctionality {
    static WebDriver driver = Driver.getDriver();

    @AfterClass
    public void tearDown() {
        driver.close();
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

    @Test(priority = 2)
    public void loginWithValidCredentials() {

        /*
         According to requirements document after logging in and getting back with step-back button
         Account summary page should be displayed, but it doesn't work like that. So, test fails.
         */

        String username = ConfigurationReader.getProperty("username");
        String password = ConfigurationReader.getProperty("password");
        login(username, password);
        driver.navigate().back();
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://zero.webappsecurity.com/bank/account-summary.html";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    @Test
    public void loginWithInvalidUsernameValidPassword() {
        String username = ConfigurationReader.getProperty("invalidUsername");
        String password = ConfigurationReader.getProperty("password");
        login(username, password);
        String actualAlertMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[1]")).getText();
        String expectedAlertMessage = ConfigurationReader.getProperty("expectedAlertMessage");
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
    }

    @Test
    public void loginWithValidUsernameInvalidPassword() {
        String username = ConfigurationReader.getProperty("username");
        String password = ConfigurationReader.getProperty("invalidPassword");
        login(username, password);
        String actualAlertMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[1]")).getText();
        String expectedAlertMessage = ConfigurationReader.getProperty("expectedAlertMessage");
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
    }

    @Test
    public void loginWithValidUsernameBlankPassword() {
        String username = ConfigurationReader.getProperty("username");
        login(username, null);
        String actualAlertMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[1]")).getText();
        String expectedAlertMessage = ConfigurationReader.getProperty("expectedAlertMessage");
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
    }

    @Test
    public void loginWithBlankUsernameValidPassword() {
        String password = ConfigurationReader.getProperty("password");
        login(null, password);
        String actualAlertMessage = driver.findElement(By.xpath("//form[@id='login_form']/div[1]")).getText();
        String expectedAlertMessage = ConfigurationReader.getProperty("expectedAlertMessage");
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
    }
}