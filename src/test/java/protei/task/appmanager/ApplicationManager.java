package protei.task.appmanager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class ApplicationManager {

    public static WebDriver driver;
    public WebDriverWait wait;
    private final Browser browser = Browser.CHROME;  //Можно указать CHROME, FIREFOX или EDGE для запуска в соответствующем браузере
    private FillFormHelper fillFormHelper;
    private LoginHelper loginHelper;

    public void stop() {
        driver.quit();
        driver = null;
    }

    public void init() {
        if (browser.equals(Browser.FIREFOX)) {
            driver = new FirefoxDriver();

        } else if (browser.equals(Browser.CHROME)) {
            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);

        } else if (browser.equals(Browser.EDGE)) {
            driver = new EdgeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        fillFormHelper = new FillFormHelper(driver);
        fillFormHelper = new FillFormHelper(wait);
        loginHelper = new LoginHelper(driver);
    }

    public FillFormHelper getFillFormHelper() {
        return fillFormHelper;
    }

    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
