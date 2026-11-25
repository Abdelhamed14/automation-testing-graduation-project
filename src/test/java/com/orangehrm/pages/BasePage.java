
package com.orangehrm.pages;

import com.orangehrm.config.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {

        this.driver = Objects.requireNonNull(
                driver, "WebDriver cannot be null"
        );

        int waitSeconds =
                ConfigReader.getIntProperty("explicit.wait");

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(waitSeconds)
        );
    }

    protected WebElement waitForVisibility(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected WebElement waitForClickability(By locator) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    protected void click(By locator) {

        waitForClickability(locator).click();
    }

    // Retry element lookup when the page re-renders
    protected void type(By locator, String text) {

        wait.until(currentDriver -> {

            try {

                WebElement element =
                        currentDriver.findElement(locator);

                if (!element.isDisplayed() || !element.isEnabled()) {
                    return false;
                }

                element.clear();
                element.sendKeys(text);

                return true;

            } catch (StaleElementReferenceException e) {

                return false;
            }
        });
    }

    protected String getText(By locator) {

        return waitForVisibility(locator).getText();
    }

    protected boolean isDisplayed(By locator) {

        try {

            return waitForVisibility(locator).isDisplayed();

        } catch (TimeoutException e) {

            return false;
        }
    }
}
