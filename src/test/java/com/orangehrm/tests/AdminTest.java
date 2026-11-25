
package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.LoginDataProvider;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("OrangeHRM Automation Project")
@Feature("Admin Module")
public class AdminTest extends BaseTest {

    // TC09 - Verify Admin Add User Page

    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Verify Add User Form Fields")
    @Severity(SeverityLevel.NORMAL)
    @Description(
            "Verify that the Add User page opens successfully "
                    + "and displays User Role, Employee Name, Username, and Password fields."
    )
    public void verifyAddUserFormFields(
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

        // Step 3: Navigate to Admin
        dashboardPage.openAdmin();

        AdminPage adminPage = new AdminPage(driver);

        // Step 4: Navigate to User Management > Users
        adminPage.openUsers();

        // Step 5: Open Add User Page
        adminPage.openAddUser();

        // Step 6: Verify Add User Page
        Assert.assertTrue(
                adminPage.isAddUserPageDisplayed(),
                "Add User page was not displayed"
        );

        // Step 7: Verify Required Form Fields
        Assert.assertTrue(
                adminPage.isUserRoleFieldDisplayed(),
                "User Role field is not displayed"
        );

        Assert.assertTrue(
                adminPage.isEmployeeNameFieldDisplayed(),
                "Employee Name field is not displayed"
        );

        Assert.assertTrue(
                adminPage.isUsernameFieldDisplayed(),
                "Username field is not displayed"
        );

        Assert.assertTrue(
                adminPage.isPasswordFieldDisplayed(),
                "Password field is not displayed"
        );
    }
}
