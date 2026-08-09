package com.store.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;

public class RegisterPage extends BasePage {

    //cmapos de formulario
    By inputFirstName = By.id("firstname");
    By inputLastName = By.id("lastname");
    By inputEmail = By.id("email_address");
    By inputPassword = By.id("password");
    By inputConfirmPassword = By.id("password-confirmation");

    By buttonCreateAccount = By.id("send2");

    //mensaje de envio de form
    By successMessage = By.cssSelector(".message-success");
    By generalErrorMessage = By.cssSelector(".message-error");

    //mensajes de validacion
    By firstNameError = By.id("firstname-error");
    By lastNameError = By.id("lastname-error");
    By emailError = By.id("email_address-error");
    By passwordError = By.id("password-error");
    By confirmPasswordError =
            By.id("password-confirmation-error");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Completa el formulario con los datos recibidos.

    public void fillRegistrationForm(
            String firstName,
            String lastName,
            String email,
            String password,
            String confirmPassword
    ) {
        Allure.step("Completar formulario de registro", () -> {
            type(inputFirstName, firstName);
            type(inputLastName, lastName);
            type(inputEmail, email);
            type(inputPassword, password);
            type(inputConfirmPassword, confirmPassword);
        });
    }

    @Step("Enviar formulario de creación de cuenta")
    public void submitRegistrationForm() {
        find(inputConfirmPassword).sendKeys(Keys.ENTER);
    }
    @Step("Obtener mensaje de registro exitoso")
    public String getSuccessMessage() {
        return text(successMessage);
    }

    @Step("Obtener mensaje general de error")
    public String getGeneralErrorMessage() {
        return text(generalErrorMessage);
    }

    public String getFirstNameError() {
        return text(firstNameError);
    }

    public String getLastNameError() {
        return text(lastNameError);
    }

    public String getEmailError() {
        return text(emailError);
    }

    public String getPasswordError() {
        return text(passwordError);
    }

    public String getConfirmPasswordError() {
        return text(confirmPasswordError);
    }
}