package protei.task.tests;

import io.qameta.allure.*;
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
        app.getLoginHelper().successLogin();
    }

    @Test
    @Description("Неуспешный вход. Сообщение 'Неверный формат E-Mail'")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testWrongEmailFormat() {
        LoginHelper.login(app.getLoginHelper().url, "123", "test");
        app.getLoginHelper().checkMessageInLoginPagePresent("Неверный формат E-Mail");
        logger.info("Аутентификация не выполнена. Указанный email имеет неправильный формат");
    }

    @Test
    @Description("Неуспешный вход. Сообщение 'Неверный E-Mail или пароль'")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testWrongEmailAndPassword() {
        LoginHelper.login(app.getLoginHelper().url, "123@protei.ru", "123");
        app.getLoginHelper().checkMessageInLoginPagePresent("Неверный E-Mail или пароль");
        logger.info("Аутентификация не выполнена. Указанный email или пароль неверны");
    }

    @Test
    @Description("Успешный вход после неуспешного")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testSuccesLoginAfterFail() {
        LoginHelper.login(app.getLoginHelper().url, "", "");
        app.getLoginHelper().checkMessageInLoginPagePresent("Неверный формат E-Mail");
        logger.info("Аутентификация не выполнена. Указанный email имеет неправильный формат");
        LoginHelper.login(app.getLoginHelper().url, "test@protei.ru", "test");
        app.getLoginHelper().checkWhatInputPagePresent();
        logger.info("Аутентификация выполнена успешно");
    }

}
