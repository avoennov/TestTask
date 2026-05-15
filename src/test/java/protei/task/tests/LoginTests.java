package protei.task.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import protei.task.LoginHelper;
import protei.task.TestBase;

public class LoginTests extends TestBase {

    @Test
    public void testSuccessLogin() {
        LoginHelper.login(url, "test@protei.ru", "test");
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
    }

    @Test
    public void testWrongEmailFormat() {
        LoginHelper.login(url, "123", "test");
        Assert.assertTrue(driver.findElement(By.id("emailFormatError")).isDisplayed());
    }

    @Test
    public void testWrongEmailAndPassword() {
        LoginHelper.login(url, "123@protei.ru", "123");
        Assert.assertTrue(driver.findElement(By.id("invalidEmailPassword")).isDisplayed());
    }

}
