
package com.orangehrm.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;

public class EmployeeDataProvider {

    private static JsonNode readTestData() {

        try (InputStream input =
                     EmployeeDataProvider.class.getClassLoader()
                             .getResourceAsStream("testdata.json")) {

            if (input == null) {
                throw new RuntimeException(
                        "testdata.json file was not found!"
                );
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read testdata.json", e
            );
        }
    }

    private static String getText(JsonNode node, String field) {

        if (!node.has(field)) {
            throw new IllegalArgumentException(
                    "Missing test data field: " + field
            );
        }

        return node.get(field).asText();
    }

    // TC04 - Existing Employee
    @DataProvider(name = "existingEmployeeData")
    public static Object[][] existingEmployeeData() {

        JsonNode root = readTestData();

        JsonNode login = root.path("login").path("valid");
        JsonNode employee = root.path("employeeSearch").path("existing");

        String username = getText(login, "username");
        String password = getText(login, "password");

        String searchName = getText(employee, "searchName");
        String firstName = getText(employee, "expectedFirstName");
        String lastName = getText(employee, "expectedLastName");

        String suggestionName = firstName + " " + lastName;

        return new Object[][]{
                {username, password, searchName,
                        suggestionName, firstName, lastName}
        };
    }

    // TC05 - Non-existing Employee
    @DataProvider(name = "nonExistingEmployeeData")
    public static Object[][] nonExistingEmployeeData() {

        JsonNode root = readTestData();

        JsonNode login = root.path("login").path("valid");
        JsonNode employee = root.path("employeeSearch").path("nonExisting");

        String username = getText(login, "username");
        String password = getText(login, "password");
        String searchName = getText(employee, "searchName");

        return new Object[][]{
                {username, password, searchName}
        };
    }

    // TC07 - Missing First Name
    @DataProvider(name = "missingFirstNameData")
    public static Object[][] missingFirstNameData() {

        JsonNode root = readTestData();

        JsonNode login = root.path("login").path("valid");
        JsonNode employee = root.path("addEmployee")
                .path("missingFirstName");

        String username = getText(login, "username");
        String password = getText(login, "password");
        String lastName = getText(employee, "lastName");

        return new Object[][]{
                {username, password, lastName}
        };
    }

    // TC08 - Add New Employee
    @DataProvider(name = "newEmployeeData")
    public static Object[][] newEmployeeData() {

        JsonNode root = readTestData();

        JsonNode login = root.path("login").path("valid");
        JsonNode employee = root.path("addEmployee").path("valid");

        String username = getText(login, "username");
        String password = getText(login, "password");

        String firstNamePrefix =
                getText(employee, "firstNamePrefix");

        String lastName =
                getText(employee, "lastName");

        // Generate a unique First Name for every execution
        String firstName =
                firstNamePrefix + System.currentTimeMillis();

        return new Object[][]{
                {username, password, firstName, lastName}
        };
    }
}
