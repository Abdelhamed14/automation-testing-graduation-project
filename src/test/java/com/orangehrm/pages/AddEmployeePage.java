
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddEmployeePage extends BasePage {

    // Locators
    private final By firstNameField =
            By.cssSelector("input[placeholder='First Name']");

    private final By lastNameField =
            By.cssSelector("input[placeholder='Last Name']");

    private final By saveButton =
            By.cssSelector("button[type='submit']");

    private final By formLoader =
            By.cssSelector(".oxd-form-loader");

    private final By firstNameRequiredMessage =
            By.xpath(
                    "//input[@placeholder='First Name']" +
                            "/ancestor::div[contains(@class,'oxd-input-group')]" +
                            "//span[contains(@class,'oxd-input-field-error-message')" +
                            " and normalize-space()='Required']"
            );

    // Constructor
    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    // Employee Information Actions

    @Step("Enter employee first name: {firstName}")
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    @Step("Enter employee last name: {lastName}")
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    @Step("Wait for form loading to finish and click Save")
    public void clickSave() {

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(formLoader)
        );

        click(saveButton);
    }

    // Validation Methods

    @Step("Verify First Name field is displayed")
    public boolean isFirstNameFieldDisplayed() {
        return isDisplayed(firstNameField);
    }

    @Step("Verify Last Name field is displayed")
    public boolean isLastNameFieldDisplayed() {
        return isDisplayed(lastNameField);
    }

    @Step("Verify Required validation appears under First Name")
    public boolean isFirstNameRequiredDisplayed() {
        return isDisplayed(firstNameRequiredMessage);
    }

    // Read Employee Information

    @Step("Read entered employee first name")
    public String getFirstNameValue() {
        return waitForVisibility(firstNameField)
                .getAttribute("value");
    }

    @Step("Read entered employee last name")
    public String getLastNameValue() {
        return waitForVisibility(lastNameField)
                .getAttribute("value");
    }
}
