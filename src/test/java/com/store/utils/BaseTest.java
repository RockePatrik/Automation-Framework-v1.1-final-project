package com.store.utils;

import com.store.pages.HomePage;
import com.store.pages.LoginPage;
import com.store.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.store.pages.ForgotPasswordPage;
import com.store.pages.ProductPage;
import com.store.pages.CartPage;
import com.store.pages.CheckoutPage;

import java.time.Duration;

public class BaseTest {

    // Driver que controla el navegador durante las pruebas
    public WebDriver driver;

    // Tiempo máximo de espera para localizar los elementos
    protected int TIME_OUT = 10;

    // URL del ambiente local de Magento levantado con Docker
    protected String URL = "http://localhost:8080";

    // Objetos de las páginas que utilizaremos en las pruebas
    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginPage loginPage;
    protected ForgotPasswordPage forgotPasswordPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    // Clase utilizada para realizar validaciones en la base de datos
    protected DBValidation dbValidation;
    protected CheckoutPage checkoutPage;


    /*
     * Pausa opcional para observar la ejecución. Utilizar únicamente durante demostraciones o depuración.
     */
    protected void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);

        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        dbValidation = new DBValidation();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            driver = null;
        }
    }


}