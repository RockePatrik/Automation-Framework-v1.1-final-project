package com.store.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;

public class LoginPage extends BasePage {

    // Elementos confirmados directamente en Magento
    By inputEmail = By.id("email");
    By inputPassword = By.id("password");
     By linkForgotPassword =
            By.cssSelector("a.action.remind");

    // Mensajes de validación
    By generalErrorMessage =
            By.cssSelector(".message-error");
    By emailError = By.id("email-error");
    By passwordError = By.id("password-error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /*
     * Completa las credenciales sin mostrar la contraseña en Allure.
     */
    public void fillLoginForm(String email, String password) {
        Allure.step("Completar formulario de inicio de sesión", () -> {
            type(inputEmail, email);
            type(inputPassword, password);
        });
    }

    @Step("Enviar formulario de inicio de sesión")
    public void submitLogin() {
        find(inputPassword).sendKeys(Keys.ENTER);
    }

    @Step("Ingresar a recuperación de contraseña")
    public void clickForgotPassword() {
        click(linkForgotPassword);
    }

    @Step("Obtener mensaje general de login")
    public String getLoginErrorMessage() {
        return text(generalErrorMessage);
    }

    public String getEmailError() {
        return text(emailError);
    }

    public String getPasswordError() {
        return text(passwordError);
    }

    @Step("Verificar que el usuario ingresó a My Account")
    public boolean isLoginSuccessful() {
        return wait.until(
                ExpectedConditions.titleIs("My Account")
        );
    }

}