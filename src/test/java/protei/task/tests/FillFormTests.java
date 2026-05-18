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
    x - поправить логи
    - софт ассерты
    - pageObject
    - параметризованный тест (?)
    - тест с добавлением нескольких пользователей
    x - добавить логи
    x - добавить Allure отчёт
    x - добавить Allure аннотации
    x - скриншоты в случае фейла
    */


    @Test
    @Description("Успешное добавление пользователя с параметрами")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testSuccessFillForm() throws InvalidNameException {
        app.getLoginHelper().successLogin();
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

    @Test
    @Description("Неуспешное добавление пользователя без email")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testFailFillFormWithoutEmail() throws InvalidNameException {
        app.getLoginHelper().successLogin();
        app.getFillFormHelper().typeName("Иван");
        app.getFillFormHelper().clickSubmitBtn();
        app.getFillFormHelper().checkMessageInInputPagePresent("Неверный формат E-Mail");
        app.getFillFormHelper().checkWhatTableDataIsEmpty();
        logger.info("Добавление пользователя не выполнено. Указанный email имеет неправильный формат");
    }

    @Test
    @Description("Неуспешное добавление пользователя без имени")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testFailFillFormWithoutName() throws InvalidNameException {
        app.getLoginHelper().successLogin();
        app.getFillFormHelper().typeEmail("ivan@mail.com");
        app.getFillFormHelper().clickSubmitBtn();
        app.getFillFormHelper().checkMessageInInputPagePresent("Поле имя не может быть пустым");
        app.getFillFormHelper().checkWhatTableDataIsEmpty();
        logger.info("Добавление пользователя не выполнено. Пустое поле 'Имя'");
    }

    @Test
    @Description("Успешное добавление пользователя без параметров")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testSuccessFillWithoutSelects() throws InvalidNameException {
        app.getLoginHelper().successLogin();
        app.getFillFormHelper().typeEmail("maria@mail.com");
        app.getFillFormHelper().typeName("Мария");
        app.getFillFormHelper().selectGender("Женский");
        app.getFillFormHelper().clickSubmitBtn();
        app.getFillFormHelper().clickOkBtnModaldlg();
        app.getFillFormHelper().checkTableData("maria@mail.com", "Мария", "Женский", "Нет", "");
        logger.info("Тест выполнен успешно");
    }
}