package protei.task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.naming.InvalidNameException;
import java.time.Duration;
import java.util.*;

public class TestBase {

    public static WebDriver driver;
    public WebDriverWait wait;
    public String url = "http://localhost:8000/qa-test.html";
    private final Browser browser = Browser.CHROME;  //Так же можно указать FIREFOX or EDGE для запуска в соответствующем браузере

    @BeforeMethod(alwaysRun = true)
    public void start () {
        if (browser.equals(Browser.FIREFOX)) {
            driver = new FirefoxDriver();

        } else if (browser.equals(Browser.CHROME)) {
            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);

        } else if (browser.equals(Browser.EDGE)) {
            driver = new EdgeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod(alwaysRun = true)
    public void stop() {
        driver.quit();
        driver = null;
    }

    protected static void clickSubmitBtn() {
        driver.findElement(By.id("dataSend")).click();
    }

    protected static void selectRadioBtn(String rbtnValue) throws InvalidNameException {
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

    protected static void selectCheckbox(String chbValue) throws InvalidNameException {
        if (Objects.equals(chbValue, "Вариант 1.1")) {
            driver.findElement(By.id("dataCheck11")).click();
        } else if (Objects.equals(chbValue, "Вариант 1.2")) {
            driver.findElement(By.id("dataCheck12")).click();
        } else {
            throw new InvalidNameException("Указано неправильное имя чекбоска");
        }
    }

    protected static void selectGender(String gender) {
        driver.findElement(By.id("dataGender")).click();
        new Select(driver.findElement(By.id("dataGender"))).selectByVisibleText(gender);
    }

    protected static void typeName(String name) {
        driver.findElement(By.id("dataName")).sendKeys(name);
    }

    protected static void typeEmail(String email) {
        driver.findElement(By.id("dataEmail")).sendKeys(email);
    }



    protected void checkTableData_v3(String emailExp, String nameExp, String genderExp, String select1Exp, String select2Exp) {

    List<Map<String, String>> actualTableData = getTableData("//table[@id='dataTable']");

    // 2. Создаем ожидаемые данные (используем LinkedHashMap для сохранения порядка)
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

    // 3. Сравниваем таблицы через TestNG
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

    protected void clickOkBtn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='uk-margin uk-modal-content']"))); //Ожидание модального диалога "Данные добавлены"
        driver.findElement(By.xpath("//button[normalize-space()='Ok']")).click(); //Нажатие кнопки ОК в модальном диалоге
    }

    protected void login() {
        LoginHelper.login(url, "test@protei.ru", "test");
        Assert.assertTrue(driver.findElement(By.id("inputsPage")).isDisplayed());
    }
}