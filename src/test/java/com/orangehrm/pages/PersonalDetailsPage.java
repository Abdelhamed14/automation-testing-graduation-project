
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalDetailsPage extends BasePage {

    // Locators
    private final By firstNameField =
            By.cssSelector("input[placeholder='First Name']");

    private final By lastNameField =
            By.cssSelector("input[placeholder='Last Name']");

    private final By employeeListTab =
            By.xpath("//a[normalize-space()='Employee List']");

    // Constructor
    public PersonalDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Page Validation

    @Step("Verify Personal Details page is displayed")
    public boolean isPersonalDetailsPageDisplayed() {

        return wait.until(currentDriver ->
                currentDriver.getCurrentUrl()
                        .contains("/pim/viewPersonalDetails/empNumber/")
        );
    }

    // Wait for Employee Information

    @Step("Wait until employee details are loaded correctly")
    public void waitForEmployeeDetails(
            String expectedFirstName,
            String expectedLastName) {

        wait.until(currentDriver -> {

            String actualFirstName =
                    currentDriver.findElement(firstNameField)
                            .getAttribute("value");

            String actualLastName =
                    currentDriver.findElement(lastNameField)
                            .getAttribute("value");

            return expectedFirstName.equals(actualFirstName)
                    && expectedLastName.equals(actualLastName);
        });
    }

    // Read Employee Information

    @Step("Read saved employee first name")
    public String getFirstName() {
        return waitForVisibility(firstNameField)
                .getAttribute("value");
    }

    @Step("Read saved employee last name")
    public String getLastName() {
        return waitForVisibility(lastNameField)
                .getAttribute("value");
    }

    // Navigation

    @Step("Navigate back to Employee List")
    public void openEmployeeList() {
        click(employeeListTab);
    }
}
