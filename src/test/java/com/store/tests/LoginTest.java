package com.store.tests;

import com.store.utils.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Ecommerce App")
@Feature("Login de usuarios")
public class LoginTest extends BaseTest {

    protected String FIRST_NAME = "Rocke";
    protected String LAST_NAME = "Hernandez";
    protected String VALID_PASSWORD = "Test@123456";

    protected String generateUniqueEmail() {
        return "rocke.login."
                + System.currentTimeMillis()
                + "@test.com";
    }

    /*
     * Crea un cliente para preparar los casos que requieren
     * una cuenta registrada.
     */
    protected String createRegisteredCustomer() {

        String email = generateUniqueEmail();

        driver.get(URL + "/customer/account/create/");

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                email,
                VALID_PASSWORD,
                VALID_PASSWORD
        );

        registerPage.submitRegistrationForm();

        String message = registerPage.getSuccessMessage();

        Assert.assertTrue(
                message.contains("Thank you for registering"),
                "No fue posible preparar el cliente de prueba."
        );

        driver.manage().deleteAllCookies();

        return email;
    }

    public void openLoginPage() {
        driver.get(URL + "/customer/account/login/");
    }

    @Test(
            groups = {"regression", "integration"},
            description = "TC-Login-01 - Inicio de sesión exitoso"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verificar el acceso con credenciales válidas.")
    @Story("TC-Login-01 - Inicio de sesión exitoso")
    public void tcLogin01SuccessfulLogin() {

        String registeredEmail =
                createRegisteredCustomer();

        openLoginPage();

        loginPage.fillLoginForm(
                registeredEmail,
                VALID_PASSWORD
        );

        loginPage.submitLogin();

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "El usuario no ingresó a My Account."
        );
    }

    @Test(
            groups = {"functional"},
            description = "TC-Login-02 - Credenciales incorrectas"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar un correo registrado con contraseña incorrecta.")
    @Story("TC-Login-02 - Credenciales incorrectas")
    public void tcLogin02IncorrectCredentials() {

        String registeredEmail =
                createRegisteredCustomer();

        openLoginPage();

        loginPage.fillLoginForm(
                registeredEmail,
                "WrongPassword@123"
        );

        loginPage.submitLogin();

        String errorMessage =
                loginPage.getLoginErrorMessage();

        Assert.assertFalse(
                errorMessage.isEmpty(),
                "No se mostró el error de credenciales."
        );
    }

    @Test(
            groups = {"functional"},
            description = "TC-Login-03 - Credenciales en blanco"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar los campos obligatorios del formulario.")
    @Story("TC-Login-03 - Credenciales en blanco")
    public void tcLogin03BlankCredentials() {

        openLoginPage();

        loginPage.submitLogin();

        Assert.assertEquals(
                loginPage.getEmailError(),
                "This is a required field."
        );

        Assert.assertEquals(
                loginPage.getPasswordError(),
                "This is a required field."
        );
    }

    @Test(
            groups = {"regression"},
            description = "TC-Login-04 - Correo no registrado"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar el acceso utilizando un correo inexistente.")
    @Story("TC-Login-04 - Correo no registrado")
    public void tcLogin04UnregisteredEmail() {

        openLoginPage();

        loginPage.fillLoginForm(
                generateUniqueEmail(),
                VALID_PASSWORD
        );

        loginPage.submitLogin();

        String errorMessage =
                loginPage.getLoginErrorMessage();

        Assert.assertFalse(
                errorMessage.isEmpty(),
                "Magento no rechazó el correo no registrado."
        );
    }


    @Test(
            groups = {"functional", "integration"},
            description = "TC-Login-05 - Contraseña olvidada"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar el envío de la solicitud de recuperación.")
    @Epic("Ecommerce App")
    @Feature("Login")
    @Story("TC-Login-05 - Contraseña olvidada")
    public void tcLogin05ForgotPassword() {

        String registeredEmail = createRegisteredCustomer();

        openLoginPage();

        loginPage.clickForgotPassword();
        forgotPasswordPage.enterEmail(registeredEmail);
        forgotPasswordPage.submitPasswordReset();

        String message = forgotPasswordPage.getSuccessMessage();

        Assert.assertTrue(
                message.toLowerCase().contains("you will receive an email"),
                "No se mostró la confirmación de recuperación. Mensaje: " + message
        );
    }
}