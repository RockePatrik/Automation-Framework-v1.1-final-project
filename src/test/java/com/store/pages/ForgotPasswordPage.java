package com.store.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;

public class ForgotPasswordPage extends BasePage {

    // Elementos confirmados en la página Forgot Your Password
    By inputEmail = By.id("email_address");

    // Mensajes de respuesta
    By emailError = By.id("email_address-error");
    By successMessage = By.cssSelector(".message-success");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ingresar correo para recuperar contraseña")
    public void enterEmail(String email) {
        type(inputEmail, email);
    }

    @Step("Enviar solicitud de recuperación")
    public void submitPasswordReset() {
        find(inputEmail).sendKeys(Keys.ENTER);
    }

    public String getEmailError() {
        return text(emailError);
    }

    @Step("Obtener confirmación de recuperación")
    public String getSuccessMessage() {
        return text(successMessage);
    }
}