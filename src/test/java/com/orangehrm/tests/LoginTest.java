
package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.LoginDataProvider;
import com.orangehrm.pages.DashboardPage;
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
@Feature("Login Module")
public class LoginTest extends BaseTest {

    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Login with Valid Credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify successful login using valid credentials.")
    public void verifyValidLogin(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        // Wait until Dashboard URL is loaded
        boolean dashboardUrlLoaded = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        ).until(
                ExpectedConditions.urlContains("/dashboard/index")
        );

        Assert.assertTrue(
                dashboardUrlLoaded,
                "Dashboard URL was not loaded"
        );

        // Verify Dashboard header
        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard header is not displayed"
        );
    }

    @Test(
            dataProvider = "invalidLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Login with Invalid Credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the system rejects invalid login credentials.")
    public void verifyInvalidLogin(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Invalid credentials",
                "Unexpected login error message"
        );
    }

    @Test(
            dataProvider = "emptyLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Login with Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify required field validation when username and password are empty.")
    public void verifyEmptyLogin(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.areBothRequiredMessagesDisplayed(),
                "Required validation messages were not displayed under both fields"
        );
    }
}
