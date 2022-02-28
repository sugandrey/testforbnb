package com.estetflora.web.bnb.test;

import com.estetflora.web.bnb.pages.StartPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    private static WebDriver driver;
    private static StartPage page;

    public static void main(String[] args) throws InterruptedException {
        page = new StartPage();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.airbnb.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated((page.cookiesAcceptingSection)));
        page.acceptCookiesButton(driver);
        driver.findElement(By.id("bigsearch-query-location-input")).sendKeys("Moscow");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_4za9uz")));
        List<WebElement> destinations = driver.findElements(By.className("_4za9uz"));
        destinations.get(0).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h2[@class='_116xafi']")));
        List<WebElement> months = driver.findElements(By.xpath("//h2[@class='_116xafi']"));
        WebElement fartherButton = driver.findElements(By.cssSelector("._6si5fw")).get(1);
        months.stream().filter(s -> !s.getText().isEmpty())
                .map(s -> s.getText()).forEach(s -> System.out.println("*******************" + s));
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

            if (!monthName.trim().equalsIgnoreCase("октябрь 2022 г.")) {
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
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h2[@class='_116xafi']")));
                months = driver.findElements(By.xpath("//h2[@class='_116xafi']"));
                monthsNames = months.stream().filter(s -> !s.getText().isEmpty()).collect(Collectors.toList());
                k = 0;
            }
        }
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div._k5mfsv")));
        List<WebElement> monthDays = driver.findElements(By.cssSelector("div._k5mfsv"));
        //monthDays.stream().filter(s -> !s.getText().equalsIgnoreCase("4")).findAny().get().findElement(By.cssSelector("div._k5mfsv")).click();
        List<WebElement> notEmptyDays = new ArrayList<>();
        int flag = 0;
        while (flag < 3) {
            try {
                notEmptyDays = monthDays.stream().filter(s -> !s.getText().isEmpty())
                        .collect(Collectors.toList());
                break;

            }catch (StaleElementReferenceException e) {
                Thread.sleep(5000);
                flag++;
            }
        }



        for (WebElement day : notEmptyDays) {
            int attempt = 0;
            String chooseDay = day.getText();
            while (attempt < 2) {
                try {
                    if (chooseDay.equals("1")) {
                        while (true) {
                            day.click();
                            if (driver.findElement(By.cssSelector("div._1vo7d29j")).isDisplayed()) {
                                break;
                            }
                        }
                    }
                    break;
                } catch (StaleElementReferenceException e) {
                    attempt++;
                }
            }
        }
    }
}