package com.estetflora.web.bnb.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertiesFileAccess {

    private static FileInputStream fileInputStream;
    private static Properties properties;
    private static String path = "./src/test/resources/bnb.properties";

    public static String getPropertiesFileValue(String name) {
        try {
            fileInputStream = new FileInputStream(path);
            properties = new Properties();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return properties.getProperty(name);

    }
}
