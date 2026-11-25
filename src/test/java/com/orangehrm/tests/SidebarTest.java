
package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.LoginDataProvider;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.SidebarPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("OrangeHRM Automation Project")
@Feature("Sidebar Module")
public class SidebarTest extends BaseTest {

    // TC11 - Verify Sidebar Menu UI

    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Verify Sidebar Menu Items")
    @Severity(SeverityLevel.MINOR)
    @Description(
            "Verify that all nine expected navigation items "
                    + "are displayed in the OrangeHRM sidebar."
    )
    public void verifySidebarMenuItems(
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

        // Step 3: Verify Sidebar Menu Items
        SidebarPage sidebarPage = new SidebarPage(driver);

        String[] expectedMenuItems = {
                "Admin",
                "PIM",
                "Leave",
                "Time",
                "Recruitment",
                "My Info",
                "Performance",
                "Dashboard",
                "Directory"
        };

        SoftAssert softAssert = new SoftAssert();

        for (String menuItem : expectedMenuItems) {

            boolean isDisplayed =
                    sidebarPage.isMenuItemDisplayed(menuItem);

            softAssert.assertTrue(
                    isDisplayed,
                    "Sidebar menu item is not displayed: " + menuItem
            );

            System.out.println(
                    menuItem + " : "
                            + (isDisplayed
                            ? "Displayed"
                            : "Not Displayed")
            );
        }

        // Report all assertion failures together
        softAssert.assertAll();
    }
}
