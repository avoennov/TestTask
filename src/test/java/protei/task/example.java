package protei.task;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class example {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("http://localhost:8000/qa-test.html");
        driver.findElement(By.id("loginEmail")).click();
        driver.findElement(By.id("loginPassword")).click();
        driver.findElement(By.id("authButton")).click();
        driver.findElement(By.xpath("//div[@id='emailFormatError']/a")).click();
        driver.findElement(By.id("loginEmail")).click();
        driver.findElement(By.id("loginEmail")).click();
        driver.findElement(By.id("loginEmail")).clear();
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).click();
        driver.findElement(By.id("loginPassword")).clear();
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
        driver.findElement(By.id("dataEmail")).click();
        driver.findElement(By.id("dataEmail")).clear();
        driver.findElement(By.id("dataEmail")).sendKeys("123@mail.com");
        driver.findElement(By.id("dataName")).click();
        driver.findElement(By.id("dataName")).clear();
        driver.findElement(By.id("dataName")).sendKeys("123");
        driver.findElement(By.id("dataGender")).click();
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataCheck12")).click();
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSelect22")).click();
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSend")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Мужской'])[2]/following::div[4]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Данные добавлены.'])[1]/following::button[1]")).click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

}
