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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Epic("Ecommerce App")
@Feature("Registro de clientes")
public class RegisterTest extends BaseTest {

    protected String FIRST_NAME = "Rocke";
    protected String LAST_NAME = "Hernandez";
    protected String VALID_PASSWORD = "Test@123456";

    /*
     * Genera un correo diferente en cada ejecución para evitar duplicados.
     */
    protected String generateUniqueEmail() {
        return "rocke.qa." + System.currentTimeMillis() + "@test.com";
    }

    @Test(groups = {"functional"}, description = "TC-Register-01 - Registro exitoso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verificar el registro de un cliente con datos válidos.")
    @Story("TC-Register-01 - Registro exitoso")
    public void tcRegister01SuccessfulRegistration() {

        String email = generateUniqueEmail();

        homePage.clickRegister();

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
                "No se mostró el mensaje de registro exitoso. Mensaje: "
                        + message
        );
    }

    @Test(
            groups = {"functional", "integration"},
            description = "TC-Register-02 - Campos obligatorios faltantes"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verificar las validaciones al enviar el formulario vacío.")
    @Story("TC-Register-02 - Campos obligatorios faltantes")
    public void tcRegister02RequiredFields() {

        homePage.clickRegister();
        registerPage.submitRegistrationForm();

        Assert.assertEquals(
                registerPage.getFirstNameError(),
                "This is a required field."
        );

        Assert.assertEquals(
                registerPage.getLastNameError(),
                "This is a required field."
        );

        Assert.assertEquals(
                registerPage.getEmailError(),
                "This is a required field."
        );

        Assert.assertEquals(
                registerPage.getPasswordError(),
                "This is a required field."
        );

        Assert.assertEquals(
                registerPage.getConfirmPasswordError(),
                "This is a required field."
        );
    }

    @Test(
            groups = {"functional", "regression"},
            description = "TC-Register-03 - Formato de correo inválido"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verificar que Magento rechace un correo incorrecto.")
    @Story("TC-Register-03 - Formato de correo electrónico")
    public void tcRegister03InvalidEmail() {

        homePage.clickRegister();

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                "correo-invalido",
                VALID_PASSWORD,
                VALID_PASSWORD
        );

        registerPage.submitRegistrationForm();

        String errorMessage = registerPage.getEmailError();

        Assert.assertTrue(
                errorMessage.toLowerCase().contains("valid email"),
                "No se mostró la validación esperada. Mensaje: "
                        + errorMessage
        );
    }

    @Test(
            groups = {"regression"},
            description = "TC-Register-04 - Contraseña débil"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verificar que Magento rechace una contraseña débil.")
    @Story("TC-Register-04 - Contraseña segura")
    public void tcRegister04WeakPassword() {

        homePage.clickRegister();

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                generateUniqueEmail(),
                "12345678",
                "12345678"
        );

        registerPage.submitRegistrationForm();

        String errorMessage = registerPage.getPasswordError();

        Assert.assertFalse(
                errorMessage.isEmpty(),
                "Magento no mostró la validación de contraseña débil."
        );
    }

    @Test(
            groups = {"functional", "integration"},
            description = "TC-Register-05 - Confirmación diferente"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description("Verificar que la contraseña y su confirmación coincidan.")
    @Story("TC-Register-05 - Confirmación de contraseña")
    public void tcRegister05PasswordMismatch() {

        homePage.clickRegister();

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                generateUniqueEmail(),
                VALID_PASSWORD,
                "Password@999"
        );

        registerPage.submitRegistrationForm();

        String errorMessage =
                registerPage.getConfirmPasswordError();

        Assert.assertTrue(
                errorMessage.toLowerCase().contains("same value"),
                "No se mostró la validación de contraseñas diferentes. "
                        + "Mensaje: " + errorMessage
        );
    }

    @Test(groups = {"integration"},
            description = "TC-Register-06 - Correo duplicado"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verificar que Magento no permita registrar el mismo correo.")
    @Story("TC-Register-06 - Correo electrónico único")
    public void tcRegister06DuplicateEmail() {

        String duplicatedEmail = generateUniqueEmail();
        String registerUrl =
                URL + "/customer/account/create/";

        /*
         * Primer registro con un correo nuevo.
         */
        driver.get(registerUrl);

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                duplicatedEmail,
                VALID_PASSWORD,
                VALID_PASSWORD
        );

        registerPage.submitRegistrationForm();

        String successMessage =
                registerPage.getSuccessMessage();

        Assert.assertTrue(
                successMessage.contains("Thank you for registering"),
                "No se pudo realizar el primer registro. Mensaje: "
                        + successMessage
        );

        /*
         * Eliminamos la sesión y abrimos un nuevo formulario.
         */
        driver.manage().deleteAllCookies();
        driver.get(registerUrl);

        /*
         * Segundo registro utilizando exactamente el mismo correo.
         */
        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                duplicatedEmail,
                VALID_PASSWORD,
                VALID_PASSWORD
        );

        registerPage.submitRegistrationForm();

        String errorMessage =
                registerPage.getGeneralErrorMessage();

        Assert.assertTrue(
                errorMessage.toLowerCase()
                        .contains("already an account"),
                "Magento no informó que el correo ya está registrado. "
                        + "Mensaje: " + errorMessage
        );
    }
}