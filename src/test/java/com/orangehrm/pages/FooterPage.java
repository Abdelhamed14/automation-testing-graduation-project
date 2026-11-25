
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterPage extends BasePage {

    // Locators
    private final By footer =
            By.cssSelector(".oxd-layout-footer");

    private final By brandingLink =
            By.cssSelector(
                    ".oxd-layout-footer a[href*='orangehrm.com']"
            );

    // Constructor
    public FooterPage(WebDriver driver) {
        super(driver);
    }

    // Footer Validation

    @Step("Read footer text")
    public String getFooterText() {
        return waitForVisibility(footer).getText();
    }

    @Step("Verify OrangeHRM branding link is displayed")
    public boolean isBrandingLinkDisplayed() {
        return isDisplayed(brandingLink);
    }

    // Footer Actions

    @Step("Click OrangeHRM branding link")
    public void clickBrandingLink() {
        click(brandingLink);
    }
}
