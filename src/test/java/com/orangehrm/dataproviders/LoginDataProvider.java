
package com.orangehrm.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;

public class LoginDataProvider {

    private static Object[][] getLoginData(String scenario) {

        try (InputStream input =
                     LoginDataProvider.class.getClassLoader()
                             .getResourceAsStream("testdata.json")) {

            if (input == null) {
                throw new RuntimeException(
                        "testdata.json file was not found!"
                );
            }

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(input);

            JsonNode loginData =
                    root.path("login").path(scenario);

            if (!loginData.isObject()
                    || !loginData.has("username")
                    || !loginData.has("password")) {

                throw new IllegalArgumentException(
                        "Missing login data for scenario: " + scenario
                );
            }

            String username = loginData.get("username").asText();
            String password = loginData.get("password").asText();

            return new Object[][]{
                    {username, password}
            };

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to read testdata.json", e
            );
        }
    }

    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() {
        return getLoginData("valid");
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return getLoginData("invalid");
    }

    @DataProvider(name = "emptyLoginData")
    public static Object[][] emptyLoginData() {
        return getLoginData("empty");
    }
}

