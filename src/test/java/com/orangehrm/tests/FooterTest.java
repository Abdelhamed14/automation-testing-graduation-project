
package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.config.ConfigReader;
import com.orangehrm.dataproviders.LoginDataProvider;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.FooterPage;
import com.orangehrm.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("OrangeHRM Automation Project")
@Feature("Footer Module")
public class FooterTest extends BaseTest {

    // TC10 - Verify OrangeHRM Footer / Branding Link

    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Verify OrangeHRM Footer Branding Link")
    @Severity(SeverityLevel.NORMAL)
    @Description(
            "Verify OrangeHRM branding text, open the branding link "
                    + "in a new browser tab, and validate the destination URL."
    )
    public void verifyOrangeHRMFooterLink(
            String username,
            String password) {

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // Step 2: Verify Dashboard
        DashboardPage dashboardPage =
                new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        // Step 3: Read Footer Text
        FooterPage footerPage = new FooterPage(driver);

        String footerText = footerPage.getFooterText();

        Assert.assertTrue(
                footerText.contains("OrangeHRM, Inc"),
                "OrangeHRM branding text was not found in footer"
        );

        // Step 4: Verify Branding Link
        Assert.assertTrue(
                footerPage.isBrandingLinkDisplayed(),
                "OrangeHRM branding link is not displayed"
        );

        // Step 5: Save Current Window Handle
        String originalWindow = driver.getWindowHandle();

        // Step 6: Click Branding Link
        footerPage.clickBrandingLink();

        // Step 7: Wait for New Tab
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getIntProperty("explicit.wait")
                )
        );

        wait.until(
                ExpectedConditions.numberOfWindowsToBe(2)
        );

        // Step 8: Switch to New Tab
        String newWindow = driver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(originalWindow))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "New browser tab was not opened"
                ));

        driver.switchTo().window(newWindow);

        // Step 9: Verify New Tab URL
        wait.until(
                ExpectedConditions.urlContains("orangehrm.com")
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("orangehrm.com"),
                "New tab URL does not contain orangehrm.com"
        );

        // Step 10: Close New Tab and Return to Original Tab
        driver.close();

        driver.switchTo().window(originalWindow);
    }
}
