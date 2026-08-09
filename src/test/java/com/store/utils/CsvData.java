package com.store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvData {
    public static Object[][] getDataFromCsv(String path) throws IOException {
        List<Object[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        // Saltar header
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            String username = values[0];
            String password = values[1];
            data.add(new Object[]{username, password});
        }
        br.close();
        return data.toArray(new Object[0][]);
    }
}
