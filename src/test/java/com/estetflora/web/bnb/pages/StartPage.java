package com.estetflora.web.bnb.pages;

import com.estetflora.web.bnb.utils.PropertiesFileAccess;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class StartPage {

    private WebDriver driver;

    public By cookiesAcceptingSection = By.className("_1dl71rl");
    private By whereAreYouGoingField = By.id("bigsearch-query-location-input");
    private By cookiesButton = By.className("_2f9tmt0");
    private By offerDest = By.className("_4za9uz");
    private By whenButton = By.className("_uh2dzp");
    private By monthList = By.xpath("//h2[@class='_116xafi']");
    private By monthFartherButtons = By.cssSelector("._6si5fw");
    private By dates = By.cssSelector("div._k5mfsv");
    private By flagDateSelected = By.cssSelector("div._1vo7d29j");
    private By addGuestsField = By.cssSelector("div._uh2dzp");
    private By guestsCountChoosePanel = By.cssSelector("div[data-testid='structured-search-input-field-guests-panel']");
    private By plusMinusButtons = By.cssSelector("._8bq7s4");
    private By searchButton = By.className("_14lk9e14");

    public void getWhereAreYouGoingField(WebDriver driver) {
        String value = PropertiesFileAccess.getPropertiesFileValue("destination");
        driver.findElement(whereAreYouGoingField).sendKeys(value);
    }

    private boolean checkCookiesSection(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2L));
        WebElement cookiesSection = driver.findElement(cookiesAcceptingSection);
        try {
            wait.until(ExpectedConditions.visibilityOf(cookiesSection));
            return true;

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public StartPage acceptCookiesButton(WebDriver driver) {
        if (checkCookiesSection(driver)) {
            driver.findElement(cookiesButton).click();
        }
        return new StartPage();
    }

    public void chooseOfferDest(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(offerDest));
        List<WebElement> destinations = driver.findElements(offerDest);
        destinations.get(0).click();
    }

    public void whenButtonClick(WebDriver driver) {
        driver.findElement(whenButton).click();
    }

    public void getMonth(WebDriver driver, String chooseMonth) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(monthList));
        List<WebElement> months = driver.findElements(monthList);
        WebElement fartherButton = driver.findElements(monthFartherButtons).get(1);
        List<WebElement> monthsNames = months.stream().filter(s -> !s.getText().isEmpty()).collect(Collectors.toList());
        int k = 0;
        String monthName = "";
        while (k < monthsNames.size()) {
            int flag = 0;
            while (flag < 2) {
                try {
                    monthName = monthsNames.get(k).getText();
                    break;
                } catch (StaleElementReferenceException e) {
                    flag++;
                }
            }

            if (!monthName.trim().equalsIgnoreCase(chooseMonth)) {
                int attempt = 0;
                while (attempt < 2) {
                    try {
                        fartherButton.click();
                        k++;
                        break;
                    } catch (StaleElementReferenceException e) {
                        attempt++;
                    }
                }
            } else {

                break;
            }
            if (k == monthsNames.size()) {
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(monthList));
                months = driver.findElements(monthList);
                monthsNames = months.stream().filter(s -> !s.getText().isEmpty()).collect(Collectors.toList());
                k = 0;
            }
        }
    }

    public void getTripDate(WebDriver driver, String chooseDate) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dates));
        List<WebElement> monthDays = driver.findElements(dates);
        //monthDays.stream().filter(s -> !s.getText().equalsIgnoreCase("4")).findAny().get().findElement(By.cssSelector("div._k5mfsv")).click();
        List<WebElement> notEmptyDays = new ArrayList<>();
        int flag = 0;
        while (flag < 3) {
            try {
                notEmptyDays = monthDays.stream().filter(s -> !s.getText().isEmpty())
                        .collect(Collectors.toList());
                break;

            } catch (StaleElementReferenceException e) {
                flag++;
            }
        }
        outer:
        for (WebElement day : notEmptyDays) {
            int attempt = 0;
            String chooseDay = day.getText();
            while (attempt < 2) {
                try {
                    if (chooseDay.equals(chooseDate)) {
//                        while (true) {
                            day.click();
//                            if (driver.findElement(flagDateSelected).isDisplayed()) {
                                break outer;
//                            }
//                        }
                    }
                } catch (StaleElementReferenceException e) {
                    attempt++;
                }
                break;
            }
        }
    }

    public void getAddGuestsField(WebDriver driver) {

        driver.findElement(addGuestsField).click();
    }

    public void getNumberOfTourists(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(guestsCountChoosePanel));
        List<WebElement> plusMinusGuestsCountButtons = driver.findElements(plusMinusButtons);
        List<Integer> guestNumberPlusMinus = guestNumberPlusMinus();
        for (int i = 0; i < plusMinusGuestsCountButtons.size(); i++) {
            int count = guestNumberPlusMinus.get(i);
            if (count > 0) {
                for (int j = 0; j < count; j++) {
                    plusMinusGuestsCountButtons.get(i).click();
                }
            }
        }
    }

    private List<Integer> guestNumberPlusMinus() {

        List<Integer> countsString = new ArrayList<>();
        int adultMinus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("adultMinus"));
        int adultPlus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("adultPlus"));
        int kidsMinus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("kidsMinus"));
        int kidsPlus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("kidsPlus"));
        int infantMinus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("infantMinus"));
        int infantPlus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("infantPlus"));
        int petMinus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("petMinus"));
        int petPlus = Integer.parseInt(PropertiesFileAccess.getPropertiesFileValue("petPlus"));

        countsString.add(adultMinus);
        countsString.add(adultPlus);
        countsString.add(kidsMinus);
        countsString.add(kidsPlus);
        countsString.add(infantMinus);
        countsString.add(infantPlus);
        countsString.add(petMinus);
        countsString.add(petPlus);

        return countsString;

    }

    public SearchResultPage pushSearchButton(WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement search = driver.findElement(searchButton);
        js.executeScript("arguments[0].click()", search);
        return new SearchResultPage();
    }
}
