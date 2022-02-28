package com.estetflora.web.bnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {

    private By pageText = By.cssSelector("h1._78tyg5");

    public void getTitle(WebDriver driver) {
        driver.getTitle();
    }
    public void setPageText(WebDriver driver) {
        String text = driver.findElement(pageText).getText();
        System.out.println("The text is ================ " + text);
    }
}
