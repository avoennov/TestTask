package protei.task.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import protei.task.LoginHelper;
import protei.task.TestBase;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Objects;

public class FillFormTests extends TestBase {

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

        checkAddedRowsCount(1);
        viewTableContent();
        checkTableData("maria@mail.com", "Мария", "Женский", "1.1, 1.2", "2.1");



    }

    private void checkTableData(String emailExp, String nameExp, String genderExp, String select1Exp, String select2Exp){
        WebElement table = driver.findElement(By.id("dataTable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        boolean isDataFound = false;

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));

            if(cols.size() >= 5) {
                String emailAct, nameAct, genderAct, select1Act, select2Act;
                emailAct = cols.get(0).getText();
                nameAct = cols.get(1).getText();
                genderAct = cols.get(2).getText();
                select1Act = cols.get(3).getText();
                select2Act = cols.get(4).getText();

                if(emailAct.equals(emailExp) && nameAct.equals(nameExp) && genderAct.equals(genderExp) && select1Act.equals(select1Exp) && select2Act.equals(select2Exp)) {
                    System.out.println("Строка найдена. Данные корректны.");
                    isDataFound = true;
                    break;
                }
            }
        }
        if (!isDataFound) {
            System.out.println("Строка не найдена в таблице");
        }


    }

    private void viewTableContent(){
        WebElement table = driver.findElement(By.id("dataTable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (WebElement cell : columns) {
                String cellText = cell.getText();
                System.out.print(cellText + "\t"); // Print values separated by tab
            }
            System.out.println(); // New line after each row
        }
    }

    private void checkAddedRowsCount(int expectedRowCount) {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='dataTable']/tbody/tr"));
        Assert.assertEquals(rows.size(), expectedRowCount);
    }

    private void clickOkBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='uk-margin uk-modal-content']"))); //Ожидание модального диалога "Данные добавлены"
        driver.findElement(By.xpath("//button[normalize-space()='Ok']")).click(); //Нажатие кнопки ОК в модальном диалоге
    }

    private static void clickSubmitBtn() {
        driver.findElement(By.id("dataSend")).click();
    }

    private static void selectRadioBtn(String rbtnValue) throws InvalidNameException {
        if (Objects.equals(rbtnValue, "Вариант 2.1")) {
            driver.findElement(By.id("dataSelect21")).click();
        } else if (Objects.equals(rbtnValue, "Вариант 2.2")) {
            driver.findElement(By.id("dataSelect22")).click();
        } else if (Objects.equals(rbtnValue, "Вариант 2.3")) {
            driver.findElement(By.id("dataSelect23")).click();
        } else {
            throw new InvalidNameException("Указано неправильное имя радиокнопки");
        }
    }

    private static void selectCheckbox(String chbValue) throws InvalidNameException {
        if (Objects.equals(chbValue, "Вариант 1.1")) {
            driver.findElement(By.id("dataCheck11")).click();
        } else if (Objects.equals(chbValue, "Вариант 1.2")) {
            driver.findElement(By.id("dataCheck12")).click();
        } else {
            throw new InvalidNameException("Указано неправильное имя чекбоска");
        }
    }

    private static void selectGender(String gender) {
        driver.findElement(By.id("dataGender")).click();
        new Select(driver.findElement(By.id("dataGender"))).selectByVisibleText(gender);
    }

    private static void typeName(String name) {
        driver.findElement(By.id("dataName")).sendKeys(name);
    }

    private static void typeEmail(String email) {
        driver.findElement(By.id("dataEmail")).sendKeys(email);
    }

    private void login() {
        LoginHelper.login(url, "test@protei.ru", "test");
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
    }
}