package com.estetflora.web.bnb.testcases;

import com.estetflora.web.bnb.initialization.WebDriverInit;
import com.estetflora.web.bnb.pages.SearchResultPage;
import com.estetflora.web.bnb.pages.StartPage;
import com.estetflora.web.bnb.utils.PropertiesFileAccess;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class BnbCookiesAccepting {

    private WebDriverInit webDriverInit;
    private static List<WebDriver> drivers;
    private StartPage startPage = new StartPage();
    private SearchResultPage searchResultPage = new SearchResultPage();

    @BeforeTest
    public void getStartPage() {
        webDriverInit = new WebDriverInit();
        drivers = webDriverInit.initWebDriver();
        for (WebDriver driver : drivers) {
            driver.get(PropertiesFileAccess.getPropertiesFileValue("url"));
        }
    }

    @Test(priority = 1)
    public void CookiesAccept() {

        for (WebDriver driver : drivers) {
            startPage = startPage.acceptCookiesButton(driver);
        }
    }

    @Test(priority = 2)
    public void chooseDestPlace() {

        for (WebDriver driver : drivers) {
            startPage.getWhereAreYouGoingField(driver);
            startPage.chooseOfferDest(driver);
        }
    }

    @Test(priority = 3)
    public void chooseTripMonthTo() {
        for (WebDriver driver : drivers) {
            //startPage.whenButtonClick(driver);
            startPage.getMonth(driver, "октябрь 2022 г.");
        }
    }

    @Test(priority = 4)
    public void chooseTripDateTo() {
        String chooseDate = PropertiesFileAccess.getPropertiesFileValue("dateTo");
        for (WebDriver driver : drivers) {
            startPage.getTripDate(driver, chooseDate);
        }
    }

    @Test(priority = 5)
    public void chooseTripMonthFrom() {
        for (WebDriver driver : drivers) {
            //startPage.whenButtonClick(driver);
            startPage.getMonth(driver, "ноябрь 2022 г.");
        }
    }

    @Test(priority = 6)
    public void chooseTripDateFrom() {
        String chooseDate = PropertiesFileAccess.getPropertiesFileValue("dateFrom");
        for (WebDriver driver : drivers) {
            startPage.getTripDate(driver, chooseDate);
        }
    }

    @Test(priority = 7)
    public void chooseNumberOfGuests() {
        for (WebDriver driver : drivers) {
            startPage.getAddGuestsField(driver);
            startPage.getNumberOfTourists(driver);
        }
    }

    @Test(priority = 8)
    public void clickSearchButton() {
        for (WebDriver driver : drivers) {
            searchResultPage = startPage.pushSearchButton(driver);
        }
    }

    @Test(priority = 9)
    public void checkSearchResultPageAppearance() {

        for(WebDriver driver : drivers) {
            searchResultPage.getTitle(driver);
            searchResultPage.setPageText(driver);
        }
    }

    @AfterTest
    public void tearDown() {
        webDriverInit.tearDown();
    }
}
