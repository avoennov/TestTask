package protei.task.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import protei.task.LoginHelper;
import protei.task.TestBase;

public class FillFormTests extends TestBase {

    @Test
    public void testFillForm(){
        login();

        typeEmail("123@mail.com");

        typeName("123");

        driver.findElement(By.id("dataGender")).click();
        new Select(driver.findElement(By.id("dataGender"))).selectByVisibleText("Женский");

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

    private static void typeName(String name) {
        driver.findElement(By.id("dataName")).sendKeys(name);
    }

    private static void typeEmail(String email) {
        driver.findElement(By.id("dataEmail")).sendKeys(email);
    }

    private void login() {
        LoginHelper.login(url, "test@protei.ru", "test");
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
    }
}
