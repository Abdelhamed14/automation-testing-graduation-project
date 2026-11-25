
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");

    private final By loginButton =
            By.cssSelector("button[type='submit']");

    private final By errorMessage =
            By.cssSelector(".oxd-alert-content-text");

    private final By requiredMessages =
            By.cssSelector(".oxd-input-field-error-message");

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Login Actions

    @Step("Enter username")
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    @Step("Click Login button")
    public void clickLogin() {
        click(loginButton);
    }

    @Step("Submit login form")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Validation Methods

    @Step("Read login error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step("Verify required messages under both login fields")
    public boolean areBothRequiredMessagesDisplayed() {

        List<WebElement> messages = wait.until(
                ExpectedConditions.numberOfElementsToBe(
                        requiredMessages, 2
                )
        );

        return messages.stream().allMatch(
                message -> message.isDisplayed()
                        && message.getText().equals("Required")
        );
    }
}
