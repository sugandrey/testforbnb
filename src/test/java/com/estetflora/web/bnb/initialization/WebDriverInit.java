package com.estetflora.web.bnb.initialization;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebDriverInit {

    private List<WebDriver> drivers;


    public List<WebDriver> initWebDriver() {
        List<WebDriver> initDrivers = new ArrayList<>();
        List<WebDriver> driverList = getBrowsersForTest();
        for (WebDriver driver : driverList) {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
            driver.manage().window().maximize();
            initDrivers.add(driver);
        }
        return initDrivers;
    }

    private List<WebDriver> getBrowsersForTest() {
        WebDriver driver;
        drivers = new ArrayList<>();
        String[] browserNames = getBrowserNameFromFile();
        for (String browserName : browserNames) {
            String browser = browserName.trim();
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                drivers.add(driver);
                log.info("ChromeDriver is initialized");
            }
            if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                drivers.add(driver);
                log.info("FirefoxDriver is initialized");
            }
//            if (browser.equalsIgnoreCase("edge")) {
//                WebDriverManager.edgedriver().setup();
//                driver = new EdgeDriver();
//                drivers.add(driver);
//                log.info("EdgeDriver is initialized");
//            }
            if (browser.equalsIgnoreCase("opera")) {
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                drivers.add(driver);
                log.info("OperaDriver is initialized");

            }
//            if (browser.equalsIgnoreCase("ie")) {
//                WebDriverManager.iedriver().setup();
//                driver = new InternetExplorerDriver();
//                drivers.add(driver);
//                log.info("IEDriver is initialized");
//            }
        }
        return drivers;
    }


    private String[] getBrowserNameFromFile() {

        File browsers = new File("./src/test/resources/browsers.txt");
        String browserName = null;
        BufferedReader reader = null;
        String[] names = null;

        try {
            reader = new BufferedReader(new FileReader(browsers));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        while (true) {
            try {
                if (!((browserName = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            names = browserName.split(",");
        }
        return names;
    }

    public void tearDown() {
        for(WebDriver driver : drivers) {
            driver.quit();
        }
    }
}
