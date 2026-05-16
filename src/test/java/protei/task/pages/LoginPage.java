package protei.task.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="loginEmail")
    public static WebElement emailField;

    @FindBy(id="loginPassword")
    public static WebElement passwordField;

    @FindBy(id="authButton")
    public static WebElement authButton;

    public static void login(String url, String email, String password) {
        driver.get(url);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        authButton.click();
    }
}
