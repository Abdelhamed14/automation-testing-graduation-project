
package com.orangehrm.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SidebarPage extends BasePage {

    // Constructor
    public SidebarPage(WebDriver driver) {
        super(driver);
    }

    // Locate Sidebar Menu Item
    private By menuItem(String menuName) {
        return By.xpath(
                "//span[normalize-space()='" + menuName + "']"
        );
    }

    // Verify Menu Item Visibility
    @Step("Verify sidebar menu item is displayed: {menuName}")
    public boolean isMenuItemDisplayed(String menuName) {
        return isDisplayed(menuItem(menuName));
    }
}
