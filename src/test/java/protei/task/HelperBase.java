package protei.task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelperBase {
    public static final Logger logger = LoggerFactory.getLogger(HelperBase.class);

    public static WebDriver driver;
    public static WebDriverWait wait;

    public static final String url = "http://localhost:8000/qa-test.html";

    public HelperBase(WebDriver driver) {
        this.driver = driver;
    }

    public HelperBase(WebDriverWait wait) {
        this.wait = wait;
    }

    protected static void click(By locator) {
        driver.findElement(locator).click();
        logger.debug("Клик на элементе с локатором: '" + locator + "'");
    }

    protected static void type(By locator, String text) {
        HelperBase.click(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        logger.debug("Ввод текста: '" + text + "' в поле с локатором: '" + locator + "'");
    }
}
