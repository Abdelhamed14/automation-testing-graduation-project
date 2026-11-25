
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {

    // Navigation Locators
    private final By userManagementMenu =
            By.xpath("//span[normalize-space()='User Management']");

    private final By usersMenu =
            By.xpath("//a[normalize-space()='Users']");

    private final By addButton =
            By.xpath("//button[normalize-space()='Add']");

    // Add User Page Locators
    private final By addUserHeader =
            By.xpath("//h6[normalize-space()='Add User']");

    private final By userRoleField =
            By.xpath(
                    "//label[normalize-space()='User Role']" +
                            "/ancestor::div[contains(@class,'oxd-input-group')]" +
                            "//div[contains(@class,'oxd-select-text')]"
            );

    private final By employeeNameField =
            By.xpath(
                    "//label[normalize-space()='Employee Name']" +
                            "/following::input[1]"
            );

    private final By usernameField =
            By.xpath(
                    "//label[normalize-space()='Username']" +
                            "/following::input[1]"
            );

    private final By passwordField =
            By.xpath(
                    "//label[normalize-space()='Password']" +
                            "/following::input[1]"
            );

    // Constructor
    public AdminPage(WebDriver driver) {
        super(driver);
    }

    // Navigation Methods

    @Step("Navigate to User Management > Users")
    public void openUsers() {
        click(userManagementMenu);
        click(usersMenu);
    }

    @Step("Click Add button to open Add User form")
    public void openAddUser() {
        click(addButton);
    }

    // Page Validation

    @Step("Verify Add User page is displayed")
    public boolean isAddUserPageDisplayed() {
        return isDisplayed(addUserHeader);
    }

    @Step("Verify User Role field is displayed")
    public boolean isUserRoleFieldDisplayed() {
        return isDisplayed(userRoleField);
    }

    @Step("Verify Employee Name field is displayed")
    public boolean isEmployeeNameFieldDisplayed() {
        return isDisplayed(employeeNameField);
    }

    @Step("Verify Username field is displayed")
    public boolean isUsernameFieldDisplayed() {
        return isDisplayed(usernameField);
    }

    @Step("Verify Password field is displayed")
    public boolean isPasswordFieldDisplayed() {
        return isDisplayed(passwordField);
    }
}
