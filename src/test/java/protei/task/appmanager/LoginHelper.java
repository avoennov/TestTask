package protei.task.appmanager;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import protei.task.HelperBase;

public class LoginHelper extends HelperBase {

    public LoginHelper(WebDriver driver) {
        super(driver);
    }

    @Step
    public static void login(String url, String email, String password) {
        driver.get(url);
        type(By.id("loginEmail"), email);
        type(By.id("loginPassword"), password);
        click(By.id("authButton"));
        logger.info("Аутентификация выполняется с email: '" + email +"' и паролем: '" + password + "'");
    }

    public void successLogin() {
        LoginHelper.login(url, "test@protei.ru", "test");
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
        logger.info("Аутентификация выполнена успешно");
    }

    @Step
    public void checkWhatInputPagePresent() {
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
        logger.info("Страница 'inputsPage' присутствует");
    }

    @Step("Проверка что сообщение присутствует")
    public void checkMessageInLoginPagePresent(String message){
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='" + message + "']")).isDisplayed());
        logger.info("Сообщение '" + message + "' присутствует");
    }

}