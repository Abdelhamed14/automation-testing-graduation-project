
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PIMPage extends BasePage {

    private final By employeeNameField =
            By.xpath("//label[normalize-space()='Employee Name']/following::input[1]");

    private final By searchButton =
            By.cssSelector("button[type='submit']");

    private final By employeeRows =
            By.cssSelector(".oxd-table-card");

    private final By employeeCells =
            By.cssSelector(".oxd-table-cell");

    private final By noRecordsFound =
            By.xpath("//span[normalize-space()='No Records Found']");

    private final By addEmployeeTab =
            By.xpath("//a[normalize-space()='Add Employee']");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    private boolean isValidName(String name) {
        return name != null
                && name.matches("[\\p{L}][\\p{L} '-]*")
                && name.replaceAll("[^\\p{L}]", "").length() >= 2;
    }

    @Step("Read the first available employee with a suitable name from Employee List")
    public String[] getFirstAvailableEmployee() {

        wait.until(currentDriver ->
                !currentDriver.findElements(employeeRows).isEmpty()
        );

        List<WebElement> rows = driver.findElements(employeeRows);

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(employeeCells);

            if (cells.size() >= 4) {

                String firstName = cells.get(2).getText().trim();
                String lastName = cells.get(3).getText().trim();

                if (isValidName(firstName) && isValidName(lastName)) {
                    return new String[]{firstName, lastName};
                }
            }
        }

        throw new IllegalStateException(
                "No employee with suitable first and last names was found"
        );
    }

    @Step("Search for existing employee: {suggestionName}")
    public void searchExistingEmployee(
            String searchName,
            String suggestionName) {

        type(employeeNameField, searchName);

        By employeeSuggestion = By.xpath(
                "//div[contains(@class,'oxd-autocomplete-option')]" +
                        "[.//span[normalize-space()='" + suggestionName + "']]"
        );

        click(employeeSuggestion);
        click(searchButton);
    }

    @Step("Search for non-existing employee: {searchName}")
    public void searchNonExistingEmployee(String searchName) {

        type(employeeNameField, searchName);
        click(searchButton);
    }

    @Step("Verify employee {firstName} {lastName} appears in search results")
    public boolean isEmployeeDisplayed(
            String firstName,
            String lastName) {

        return wait.until(currentDriver ->
                currentDriver.findElements(employeeRows)
                        .stream()
                        .anyMatch(row -> {
                            String rowText = row.getText();
                            return rowText.contains(firstName)
                                    && rowText.contains(lastName);
                        })
        );
    }

    @Step("Verify No Records Found message is displayed")
    public boolean isNoRecordsFoundDisplayed() {
        return isDisplayed(noRecordsFound);
    }

    @Step("Open Add Employee page")
    public void openAddEmployee() {
        click(addEmployeeTab);
    }
}
