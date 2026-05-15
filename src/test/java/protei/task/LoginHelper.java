package protei.task;

import org.openqa.selenium.By;

public class LoginHelper extends TestBase {

    public static void login(String url, String email, String password) {
        driver.get(url);
        driver.findElement(By.id("loginEmail")).sendKeys(email);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.id("authButton")).click();
    }
}
