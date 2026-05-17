package protei.task.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

import javax.naming.InvalidNameException;

@Epic("Тестовое задание")
@Feature("Форма добавления пользователей")
@Story("Добавление новых пользователей с данными")
public class FillFormTests extends TestBase {

    /* TODO:
    x - проверка данных в таблице, переделать на массивы
    - больше ассертов
    - софт ассерты
    - pageObject
    - параметризованный тест (?)
    x - добавить логи
    x - добавить Allure отчёт
    x - добавить Allure аннотации
    x - скриншоты в случае фейла
    */


    @Test
    @Description("Успешное добавление пользователя с параметрами")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")
    public void testFillForm() throws InvalidNameException {
        app.getLoginHelper().login();
        app.getFillFormHelper().typeEmail("maria@mail.com");
        app.getFillFormHelper().typeName("Мария");
        app.getFillFormHelper().selectGender("Женский");
        app.getFillFormHelper().selectCheckbox("Вариант 1.1");
        app.getFillFormHelper().selectCheckbox("Вариант 1.2");
        app.getFillFormHelper().selectRadioBtn("Вариант 2.1");
        app.getFillFormHelper().clickSubmitBtn();
        app.getFillFormHelper().clickOkBtnModaldlg();
        app.getFillFormHelper().checkTableData("maria@mail.com", "Мария", "Женский", "1.1, 1.2", "2.1");
        logger.info("Тест выполнен успешно");
    }
}