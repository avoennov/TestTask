package protei.task.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import protei.task.data.UserInputDataPojo;
import protei.task.data.UserOutputDataPojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static protei.task.appmanager.ApplicationManager.driver;

@Epic("Тестовое задание")
@Feature("Форма добавления пользователей")
@Story("Добавление новых пользователей с данными")
public class FillFormTests extends TestBase {


    @Test
    @Description("Успешное добавление пользователя с параметрами")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testSuccessFillForm() {
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

    public void testFailFillFormWithoutEmail() {
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

    public void testFailFillFormWithoutName() {
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

    public void testSuccessFillWithoutSelects() {
        app.getLoginHelper().successLogin();
        app.getFillFormHelper().typeEmail("maria@mail.com");
        app.getFillFormHelper().typeName("Мария");
        app.getFillFormHelper().selectGender("Женский");
        app.getFillFormHelper().clickSubmitBtn();
        app.getFillFormHelper().clickOkBtnModaldlg();
        app.getFillFormHelper().checkTableData("maria@mail.com", "Мария", "Женский", "Нет", "");
        logger.info("Тест выполнен успешно");
    }

    private final Gson gson = new Gson();
    private <T> List<T> readJsonFile(String path, Type typeToken) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, typeToken);
        }
    }

    @Test
    @Description("Успешное добавление нескольких пользователей с параметрами")
    @Link(name = "Документация", url = "https://example.com")
    @Owner("Военнов Алексей")

    public void testFillFormSubmitAndTable() throws IOException, InterruptedException {
        //Чтение данных из обоих JSON файлов
        Type formType = new TypeToken<List<UserInputDataPojo>>(){}.getType();
        List<UserInputDataPojo> inputDataList = readJsonFile("src/test/resources/userInputData.json", formType);

        Type tableType = new TypeToken<List<UserOutputDataPojo>>(){}.getType();
        List<UserOutputDataPojo> expectedDataList = readJsonFile("src/test/resources/userOutputData.json", tableType);

        app.getLoginHelper().successLogin();

        //Цикл заполнения формы всеми объектами из первого файла
        for (UserInputDataPojo user : inputDataList) {
            app.getFillFormHelper().typeEmail(user.getEmail());
            app.getFillFormHelper().typeName(user.getName());
            app.getFillFormHelper().selectGender(user.getGender());
            app.getFillFormHelper().selectCheckbox(user.getCheckbox());
            app.getFillFormHelper().selectRadioBtn(user.getRadioBtn());
            app.getFillFormHelper().clickSubmitBtn();
            app.getFillFormHelper().clickOkBtnModaldlg();

            WebElement form = driver.findElement(By.xpath("//*[@id='inputsPage']/form"));

            //Очистка формы перед введением новых данных
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].reset();", form);
        }

        //Цикл проверки созданных строк в таблице по второму файлу
        for (int i = 0; i < expectedDataList.size(); i++) {
            UserOutputDataPojo expected = expectedDataList.get(i);

            //Номер строки в XPath начинается с 1, поэтому i + 1
            int targetRow = i + 1;

            //Считывание актуальных данных из таблицы
            String emailAct = app.getFillFormHelper().getCellText(targetRow, 1);
            String nameAct = app.getFillFormHelper().getCellText(targetRow, 2);
            String genderAct = app.getFillFormHelper().getCellText(targetRow, 3);
            String checkboxAct = app.getFillFormHelper().getCellText(targetRow, 4);
            String radioBtnAct = app.getFillFormHelper().getCellText(targetRow, 5);

            //Сравнение актуальных данных с ожидаемыми
            Assert.assertEquals(emailAct, expected.getEmailExp(),
                    "Несовпадение значения в колонке E-Mail " + targetRow);
            Assert.assertEquals(nameAct, expected.getNameExp(),
                    "Несовпадение значения в колонке Имя " + targetRow);
            Assert.assertEquals(genderAct, expected.getGenderExp(),
                    "Несовпадение значения в колонке Пол " + targetRow);
            Assert.assertEquals(checkboxAct, expected.getCheckboxExp(),
                    "Несовпадение значения в колонке Выбор 1 " + targetRow);
            Assert.assertEquals(radioBtnAct, expected.getRadioBtnExp(),
                    "Несовпадение значения в колонке Выбор 2 " + targetRow);
        }
    }
}