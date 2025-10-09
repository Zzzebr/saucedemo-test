package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.time.Duration;


public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        //WebDriverManager.chromedriver().setup();
        //ChromeOptions options = new ChromeOptions();
// options.addArguments("--headless=new"); // раскомментируйте, если нужен headless
        //options.addArguments("--window-size=1920,1080");
        //driver = new ChromeDriver(options);
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // используем явные ожидания
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}