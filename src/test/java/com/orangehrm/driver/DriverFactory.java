
package com.orangehrm.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Locale;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void initializeDriver(String browser) {

        WebDriver webDriver;

        switch (browser.toLowerCase(Locale.ROOT)) {

            case "chrome":
                webDriver = new ChromeDriver();
                break;

            case "firefox":
                webDriver = new FirefoxDriver();
                break;

            case "edge":
                webDriver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }

        driver.set(webDriver);

        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {

        WebDriver currentDriver = driver.get();

        if (currentDriver == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialized!"
            );
        }

        return currentDriver;
    }

    public static void quitDriver() {

        WebDriver currentDriver = driver.get();

        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }
}
