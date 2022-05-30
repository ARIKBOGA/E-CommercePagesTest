package task_01;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.Driver;
import utilities.HandleWait;

public class AmazonTest {
    static WebDriver driver;

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void amazonTest() {
        driver = Driver.getDriver();

        // 1.	Go to https://www.amazon.com
        driver.get("https://www.amazon.com");

        // 2.	Search for "hats for men" (Call from Configuration.properties file)
        String searchValue = ConfigurationReader.getProperty("searchValue");
        WebElement mainSearchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        mainSearchBox.sendKeys(searchValue + Keys.ENTER);


        // 3.	Add the first hat appearing to Cart with quantity 2
        // 3.1 Click to first appearing product picture to get in its page
        double firstProductPrice = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText());
        double firstProductPricedecimal = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-fraction'])[1]")).getText());
        firstProductPrice += firstProductPricedecimal / 100;
        driver.findElement(By.xpath("//div[@class='s-main-slot s-result-list s-search-results sg-row']/div[2]//a[@class='a-link-normal s-no-outline']")).click();
        // 3.2 locate dropdown menu
        WebElement dropdown = driver.findElement(By.xpath("//select[@id='quantity']"));
        Select select = new Select(dropdown);
        select.selectByValue("2");

        // 3.3 add to cart
        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();


        // 4.	Open cart and assert that the total price and quantity are correct
        driver.findElement(By.xpath("//span[@id='sw-gtc']")).click();
        select = new Select(driver.findElement(By.xpath("//select[@id='quantity']")));
        String actualQuantity = select.getFirstSelectedOption().getText();
        String expectedQuantity = "2";

        double actualTotalPrice = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText());
        double actulaTotalPriceDecimal = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-fraction'])[1]")).getText()) / 100;
        actualTotalPrice += actulaTotalPriceDecimal;
        double expectedTotalPrice = firstProductPrice * 2;

        Assert.assertEquals(actualQuantity, expectedQuantity);
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);


        // 5.	Reduce the quantity from 2 to 1 in Cart for the item selected in the step 3
        select.selectByValue("1");


        // 6.	Assert that the total price and quantity has been correctly changed
        expectedQuantity = "1";
        actualQuantity = select.getFirstSelectedOption().getText();

        actualTotalPrice = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText());
        actulaTotalPriceDecimal = Double.parseDouble(driver.findElement(By.xpath("(//span[@class='a-price-fraction'])[1]")).getText()) / 100;
        actualTotalPrice += actulaTotalPriceDecimal;
        expectedTotalPrice = firstProductPrice * 2;

        Assert.assertEquals(actualQuantity, expectedQuantity);
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);

        HandleWait.staticWait(5);

    }
}
