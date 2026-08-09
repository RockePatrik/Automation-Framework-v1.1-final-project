package com.store.utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "dpFromCsv")
    public static Object[][] getUserCredentials() throws IOException {
        return CsvData.getDataFromCsv(System.getProperty("user.dir") + "/src/test/resources/users.csv");
    }
}
