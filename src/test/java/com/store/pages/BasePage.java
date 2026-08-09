package com.store.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class BasePage {

    // Driver utilizado para interactuar con la página
    protected WebDriver driver;

    // Espera explícita para controlar la aparición de los elementos
    protected WebDriverWait wait;

    // Tiempo máximo de espera para los elementos
    private static final int TIME_OUT = 10;

    /*
     * Recibe el mismo driver que fue inicializado en BaseTest.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(TIME_OUT)
        );
    }

    /*
     * Espera hasta que el elemento sea visible y lo devuelve.
     */
    public WebElement find(By element) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(element)
        );
    }

    /*
     * Realiza un clic normal sobre un elemento visible.
     */
    public void click(By element) {

        WebElement webElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(element)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                webElement
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(webElement)
        ).click();
    }


    /*
     * Espera hasta que el elemento pueda recibir un clic.
     */
    /*
     * Desplaza la pantalla hasta el elemento,
     * espera que pueda recibir el clic y lo selecciona.
     */
    /*
     * Realiza un clic mediante JavaScript.
     *
     * Se utiliza únicamente cuando el elemento existe en el DOM,
     * pero Selenium no puede interactuar con él de manera convencional.
     */
    public void clickCreateAccount(By element) {

        WebElement webElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(element)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                webElement
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                webElement
        );
    }

    /*
     * Envía un formulario mediante el elemento indicado.
     */
    public void submit(By element) {
        find(element).submit();
    }

    /*
     * Limpia el campo y escribe el texto recibido.
     */
    public void type(By element, String text) {
        WebElement field = find(element);
        field.clear();
        field.sendKeys(text);
    }

    /*
     * Obtiene el texto visible de un elemento.
     */
    public String text(By element) {
        return find(element).getText();
    }

    /*
     * Comprueba si un elemento aparece dentro del tiempo establecido.
     */
    public boolean isDisplayedElement(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}