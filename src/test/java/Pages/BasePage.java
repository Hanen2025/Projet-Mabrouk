package Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public void setupBrowser(String browserName) {
        if (driver == null) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                default:
                    throw new IllegalArgumentException("Browser " + browserName + " is not supported.");
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        }
    }

    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
