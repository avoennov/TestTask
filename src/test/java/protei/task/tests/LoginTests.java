package protei.task.tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import protei.task.appmanager.LoginHelper;

@Epic("Тестовое задание")
@Feature("Страница входа в систему")
@Story("Аутентификация и авторизация пользователя в стстеме")

public class LoginTests extends TestBase {

    @Test
    @Description("Успешный вход в систему")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testSuccessLogin() {
        app.getLoginHelper().login();
    }

    @Test
    @Description("Неуспешный вход. Сообщение 'Неверный формат E-Mail'")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testWrongEmailFormat() {
        LoginHelper.login(app.getLoginHelper().url, "123", "test");
        Assert.assertTrue(app.getFillFormHelper().driver.findElement(By.id("emailFormatError")).isDisplayed());
        logger.info("Аутентификация не выполнена. Указанный email имеет неправильный формат");
    }

    @Test
    @Description("Неуспешный вход. Сообщение 'Неверный E-Mail или пароль'")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testWrongEmailAndPassword() {
        LoginHelper.login(app.getLoginHelper().url, "123@protei.ru", "123");
        Assert.assertTrue(app.getFillFormHelper().driver.findElement(By.id("invalidEmailPassword")).isDisplayed());
        logger.info("Аутентификация не выполнена. Указанный email или пароль неверны");
    }

}
