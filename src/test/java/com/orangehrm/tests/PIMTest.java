
package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.EmployeeDataProvider;
import com.orangehrm.dataproviders.LoginDataProvider;
import com.orangehrm.pages.AddEmployeePage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.pages.PersonalDetailsPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("OrangeHRM Automation Project")
@Feature("PIM Module")
public class PIMTest extends BaseTest {

    // TC04 - Search for Existing Employee Dynamically
    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Search for Existing Employee")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify searching for an existing employee using dynamically retrieved employee data.")
    public void verifySearchExistingEmployee(
            String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        dashboardPage.openPIM();

        PIMPage pimPage = new PIMPage(driver);

        String[] employee = pimPage.getFirstAvailableEmployee();

        String firstName = employee[0];
        String lastName = employee[1];

        String suggestionName = firstName + " " + lastName;

        System.out.println("Employee selected: " + suggestionName);

        pimPage.searchExistingEmployee(firstName, suggestionName);

        Assert.assertTrue(
                pimPage.isEmployeeDisplayed(firstName, lastName),
                "Expected employee was not found: " + suggestionName
        );
    }

    // TC05 - Search for Non-existing Employee
    @Test(
            dataProvider = "nonExistingEmployeeData",
            dataProviderClass = EmployeeDataProvider.class
    )
    @Story("Search for Non-existing Employee")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that searching for a non-existing employee displays No Records Found.")
    public void verifySearchNonExistingEmployee(
            String username,
            String password,
            String searchName) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        dashboardPage.openPIM();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.searchNonExistingEmployee(searchName);

        Assert.assertTrue(
                pimPage.isNoRecordsFoundDisplayed(),
                "No Records Found message was not displayed"
        );
    }

    // TC06 - Open Add Employee Page
    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    @Story("Open Add Employee Page")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the Add Employee page opens and displays First Name and Last Name fields.")
    public void verifyAddEmployeePage(
            String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        dashboardPage.openPIM();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.openAddEmployee();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/pim/addEmployee"),
                "Add Employee page URL is incorrect"
        );

        AddEmployeePage addEmployeePage =
                new AddEmployeePage(driver);

        Assert.assertTrue(
                addEmployeePage.isFirstNameFieldDisplayed(),
                "First Name field is not displayed"
        );

        Assert.assertTrue(
                addEmployeePage.isLastNameFieldDisplayed(),
                "Last Name field is not displayed"
        );
    }

    // TC07 - Add Employee with Empty First Name
    @Test(
            dataProvider = "missingFirstNameData",
            dataProviderClass = EmployeeDataProvider.class
    )
    @Story("Required First Name Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the system displays a Required validation message when First Name is empty.")
    public void verifyRequiredFirstNameValidation(
            String username,
            String password,
            String lastName) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        dashboardPage.openPIM();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.openAddEmployee();

        AddEmployeePage addEmployeePage =
                new AddEmployeePage(driver);

        addEmployeePage.enterLastName(lastName);
        addEmployeePage.clickSave();

        Assert.assertTrue(
                addEmployeePage.isFirstNameRequiredDisplayed(),
                "Required validation was not displayed under First Name"
        );
    }

    // TC08 - End-to-End Add Employee Successfully
    @Test(
            dataProvider = "newEmployeeData",
            dataProviderClass = EmployeeDataProvider.class
    )
    @Story("Add Employee Successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new employee can be created, saved, and found in Employee List.")
    public void verifyAddEmployeeSuccessfully(
            String username,
            String password,
            String firstName,
            String lastName) {

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        // Step 2: Navigate to Add Employee
        dashboardPage.openPIM();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.openAddEmployee();

        // Step 3: Enter Employee Information
        AddEmployeePage addEmployeePage =
                new AddEmployeePage(driver);

        addEmployeePage.enterFirstName(firstName);
        addEmployeePage.enterLastName(lastName);
        addEmployeePage.clickSave();

        // Step 4: Verify Personal Details Page
        PersonalDetailsPage personalDetailsPage =
                new PersonalDetailsPage(driver);

        Assert.assertTrue(
                personalDetailsPage.isPersonalDetailsPageDisplayed(),
                "Personal Details page was not opened"
        );

        // Step 5: Wait for Employee Details
        personalDetailsPage.waitForEmployeeDetails(
                firstName, lastName
        );

        // Step 6: Verify Saved Employee Information
        Assert.assertEquals(
                personalDetailsPage.getFirstName(),
                firstName,
                "Saved First Name does not match"
        );

        Assert.assertEquals(
                personalDetailsPage.getLastName(),
                lastName,
                "Saved Last Name does not match"
        );

        // Step 7: Navigate to Employee List
        personalDetailsPage.openEmployeeList();

        // Step 8: Search for the Newly Created Employee
        PIMPage employeeListPage = new PIMPage(driver);

        employeeListPage.searchExistingEmployee(
                firstName,
                firstName + " " + lastName
        );

        // Step 9: Verify Employee Appears in Search Results
        Assert.assertTrue(
                employeeListPage.isEmployeeDisplayed(firstName, lastName),
                "Newly created employee was not found in Employee List"
        );

        System.out.println(
                "Successfully created and verified employee: "
                        + firstName + " " + lastName
        );
    }
}
