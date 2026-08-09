package com.store.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY) {
            count++;
            return true;
        }
        return false;
    }

    public boolean isLastAttempt() {
        return count >= MAX_RETRY;
    }

    public int getCurrentAttempt() {
        return count + 1;
    }

    public int getMaxRetry() {
        return MAX_RETRY;
    }
}
