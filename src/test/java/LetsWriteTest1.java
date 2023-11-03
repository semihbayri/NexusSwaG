
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class LetsWriteTest1 {
    WebDriver driver;
    WebDriverWait wait;

    String URL = "https://www.saucedemo.com/";

    String userName;
    String password;

        /*
    Login with problem_user, password : secret_sauce
    Add items to cart and if any items fails to get added in cart print the name
    as an example we have 6 items in inventory,
    then only 2 items fails to get added in cart then print the name of those 2 items
    * */

    public void login(String userName, String password) {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--window-size=1480,1080");
        chromeOptions.addArguments("--no-sandbox");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);

        driver.get(URL);
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();

    }

    @Test
    public void addItemsToCartVerify() throws InterruptedException {
        userName = "problem_user";
        password = "secret_sauce";

        login(userName, password);

        String expectedTitle = "Products";
        String actualTitle = driver.findElement(By.xpath("//span[@class='title']")).getText();

        Assert.assertEquals(expectedTitle, actualTitle);

        List<String> productTitleListsText =new ArrayList<>();

        for (int i = 1; i <=6 ; i++) {
            productTitleListsText.add(driver.findElement(By.xpath("(//*[@class='inventory_item_name '])[" + i + "]")).getText());


        }  System.out.println(productTitleListsText);

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.xpath("//button[@data-test='add-to-cart-test.allthethings()-t-shirt-(red)']")).click();
        driver.findElement(By.xpath("(//*[@id='shopping_cart_container'])//a")).click();

        String productNumber= driver.findElement(By.xpath("//*[@class='shopping_cart_badge']")).getText();
        int pNumber= Integer.parseInt(productNumber);

        for (int i = 1; i <= pNumber; i++) {

            productTitleListsText.remove(driver.findElement(By.xpath("(//*[@class='inventory_item_name'])[" + i + "]")).getText());

        }
        Thread.sleep(3000);
        System.out.println(productTitleListsText);

       driver.quit();
    }

    @Test
     /* Login with --> standard_user,  password : secret_sauce
        Verify that user successfully logged in
        sort the list - choose any option in DropDown.
        verify the list is sorted correctly
    * */
    public void verifyIsLoginSuccessful() throws InterruptedException {
        userName= "standard_user";
        password= "secret_sauce";

        login(userName,password);
        String expectedTitle="Products";
        String actualTitle= driver.findElement(By.xpath("//span[@class='title']")).getText();

        Thread.sleep(2000);

        Assert.assertEquals(expectedTitle,actualTitle);

        driver.findElement(By.xpath("//*[@class='product_sort_container']")).click();

        driver.findElement(By.xpath("//*[@value='lohi']")).click();

        String firstItemsPrice= driver.findElement(By.xpath("(//*[@class='inventory_item_price'])[1]")).getText();

        String secondItemsPrice= driver.findElement(By.xpath("(//*[@class='inventory_item_price'])[2]")).getText();

        String thirdItemsPrice= driver.findElement(By.xpath("(//*[@class='inventory_item_price'])[3]")).getText();

        String firstPrice=firstItemsPrice.substring(1);
        String secondPrice=secondItemsPrice.substring(1);
        String thirdPrice=thirdItemsPrice.substring(1);

        System.out.println(firstPrice+secondPrice+thirdPrice);
        System.out.println(Double.parseDouble(firstPrice));

        Assert.assertTrue(Double.parseDouble(secondPrice)>Double.parseDouble(firstPrice));
        Assert.assertTrue(Double.parseDouble(thirdPrice)>Double.parseDouble(secondPrice));

        driver.quit();

    }

}