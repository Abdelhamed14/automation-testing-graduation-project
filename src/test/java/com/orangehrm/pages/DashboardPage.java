
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // Locators
    private final By dashboardHeader =
            By.xpath("//h6[normalize-space()='Dashboard']");

    private final By pimMenu =
            By.xpath("//span[normalize-space()='PIM']");

    private final By adminMenu =
            By.xpath("//span[normalize-space()='Admin']");

    // Constructor
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Validation Methods

    @Step("Verify Dashboard page is displayed")
    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader);
    }

    // Navigation Methods

    @Step("Navigate to PIM module")
    public void openPIM() {
        click(pimMenu);
    }

    @Step("Navigate to Admin module")
    public void openAdmin() {
        click(adminMenu);
    }
}
