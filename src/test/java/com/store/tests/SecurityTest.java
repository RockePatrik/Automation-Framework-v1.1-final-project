package com.store.tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

public class SecurityTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that the application is protected against SQL injection and script injection")
    @Description("Verify that the application is protected against SQL injection and script injection")
    public void verifyLoginWithSQLInjection(){

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that the application is protected against SQL injection and script injection")
    @Description("Verify that the application is protected against SQL injection and script injection")
    public void verifyRegisterWithScriptInjection(){

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that password policy is enforced during registration")
    @Description("Verify that password policy is enforced during registration")
    public void verifyPasswordPolicyEnforced(){

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that input sanitization prevents XSS attacks on search")
    @Description("Verify that input sanitization prevents XSS attacks on search")
    public void verifyInputSanitizationOnSearch(){

    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that user session times out after a period of inactivity")
    @Description("Verify that user session times out after a period of inactivity")
    public void verifySessionTimeout(){

    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Epic("Ecommerce App")
    @Feature("Security")
    @Story("Verify that the application enforces HTTPS connection")
    @Description("Verify that the application enforces HTTPS connection")
    public void verifySecureConnection(){

    }
}
