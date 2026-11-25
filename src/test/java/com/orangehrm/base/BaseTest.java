
package com.orangehrm.base;

import com.orangehrm.config.ConfigReader;
import com.orangehrm.driver.DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        String browser = ConfigReader.getProperty("browser");

        DriverFactory.initializeDriver(browser);

        driver = DriverFactory.getDriver();

        driver.get(
                ConfigReader.getProperty("base.url") + "auth/login"
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}
