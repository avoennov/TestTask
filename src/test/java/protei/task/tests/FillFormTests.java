package protei.task.tests;

import org.testng.annotations.Test;
import protei.task.TestBase;

import javax.naming.InvalidNameException;

public class FillFormTests extends TestBase {

    /* TODO:
    x - проверка данных в таблице, переделать на массивы
    - больше ассертов
    - софт ассерты
    - pageObject
    - параметризованный тест (?)
    - добавить логи
    - добавить Allure отчёт
    - добавить Allure аннотации
    */


    @Test
    public void testFillForm() throws InvalidNameException {
        login();
        typeEmail("maria@mail.com");
        typeName("Мария");
        selectGender("Женский");
        selectCheckbox("Вариант 1.1");
        selectCheckbox("Вариант 1.2");
        selectRadioBtn("Вариант 2.1");
        clickSubmitBtn();
        clickOkBtn();
        checkTableData_v3("maria@mail.com", "Мария", "Женский", "1.1, 1.2", "2.1");
    }
}