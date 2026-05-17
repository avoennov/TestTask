package protei.task.appmanager;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import protei.task.HelperBase;

import javax.naming.InvalidNameException;
import java.util.*;

public class FillFormHelper extends HelperBase {

    public FillFormHelper(WebDriver driver) {
        super(driver);
    }

    public FillFormHelper(WebDriverWait wait) {
        super(wait);
    }

    @Step("Нажатие кнопки 'Добавить'")
    public void clickSubmitBtn() {
        click(By.id("dataSend"));
    }

    @Step("Нажатие на радиокнопку/кнопки")
    public void selectRadioBtn(String rbtnValue) throws InvalidNameException {
        if (Objects.equals(rbtnValue, "Вариант 2.1")) {
            click(By.id("dataSelect21"));
        } else if (Objects.equals(rbtnValue, "Вариант 2.2")) {
            click(By.id("dataSelect22"));
        } else if (Objects.equals(rbtnValue, "Вариант 2.3")) {
            click(By.id("dataSelect23"));
        } else {
            throw new InvalidNameException("Указано неправильное имя радиокнопки");
        }
    }

    @Step("Выбор чекбокса")
    public void selectCheckbox(String chbValue) throws InvalidNameException {
        if (Objects.equals(chbValue, "Вариант 1.1")) {
            click(By.id("dataCheck11"));
        } else if (Objects.equals(chbValue, "Вариант 1.2")) {
            click(By.id("dataCheck12"));
        } else {
            throw new InvalidNameException("Указано неправильное имя чекбоска");
        }
    }

    @Step("Выбор пола")
    public void selectGender(String gender) {
        click(By.id("dataGender"));
        new Select(driver.findElement(By.id("dataGender"))).selectByVisibleText(gender);
    }


    @Step("Ввод имени")
    public void typeName(String name) {
        type(By.id("dataName"), name);
    }

    @Step("Ввод email")
    public void typeEmail(String email) {
        type(By.id("dataEmail"), email);
    }


    @Step("Проверка данных из таблицы")
    public void checkTableData(String emailExp, String nameExp, String genderExp, String select1Exp, String select2Exp) {

    List<Map<String, String>> actualTableData = getTableData("//table[@id='dataTable']");

    // Создаем ожидаемые данные (используем LinkedHashMap для сохранения порядка)
    List<Map<String, String>> expectedTableData = new ArrayList<>();

    Map<String, String> row1 = new LinkedHashMap<>();
        row1.put("E-Mail", emailExp);
        row1.put("Имя", nameExp);
        row1.put("Пол", genderExp);
        row1.put("Выбор 1", select1Exp);
        row1.put("Выбор 2", select2Exp);
        expectedTableData.add(row1);

/*    Map<String, String> row2 = new LinkedHashMap<>();
        row2.put("E-Mail", emailExp);
        row2.put("Имя", nameExp);
        row2.put("Пол", genderExp);
        row2.put("Выбор 1", select1Exp);
        row2.put("Выбор 2", select2Exp);
        expectedTableData.add(row2);*/

    // Сравниваем таблицы через TestNG
        Assert.assertEquals(actualTableData, expectedTableData, "Данные в таблице не соответствуют ожидаемым!");
}

    // Метод для парсинга таблицы
    public List<Map<String, String>> getTableData(String tableXPath) {
        List<Map<String, String>> tableData = new ArrayList<>();

        // Находим заголовки (Headers)
        List<WebElement> headers = driver.findElements(By.xpath(tableXPath + "//thead//th"));
        List<String> headerNames = new ArrayList<>();
        for (WebElement header : headers) {
            headerNames.add(header.getText().trim());
        }

        // Находим строки (Rows)
        List<WebElement> rows = driver.findElements(By.xpath(tableXPath + "//tbody//tr"));
        for (WebElement row : rows) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            // Находим все ячейки в текущей строке
            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (int i = 0; i < headers.size(); i++) {
                // Заполняем Map: ключ = заголовок, значение = текст ячейки
                String cellValue = (i < cells.size()) ? cells.get(i).getText().trim() : "";
                rowMap.put(headerNames.get(i), cellValue);
            }
            tableData.add(rowMap);
        }
        return tableData;
    }


    @Step("Нажатие кнопки OK в диалоговом окне \"Данные добавлены\"")
    public void clickOkBtnModaldlg() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='uk-margin uk-modal-content']"))); //Ожидание модального диалога "Данные добавлены"
        click(By.xpath("//button[normalize-space()='Ok']")); //Нажатие кнопки ОК в модальном диалоге
    }
}
