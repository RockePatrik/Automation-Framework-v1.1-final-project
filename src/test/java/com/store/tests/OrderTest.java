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
@Feature("Creación de órdenes")
public class OrderTest extends BaseTest {

    protected String FIRST_NAME = "Rocke";
    protected String LAST_NAME = "Hernandez";
    protected String VALID_PASSWORD = "Test@123456";

    protected String ELECTRONICS_URL = "/electronica.html";
    protected String AIRPODS_SKU = "airpods-pro-2";
    protected String GALAXY_SKU = "galaxy-s25-ultra";
    protected String SONY_SKU = "wh-1000xm6";


    private String generateUniqueEmail() {
        return "rocke.order."
                + System.currentTimeMillis()
                + "@test.com";
    }

    /*
     * Registra un cliente y conserva su sesión activa
     * para ejecutar los escenarios de órdenes.
     */
    private void createLoggedCustomer() {

        driver.get(URL + "/customer/account/create/");

        registerPage.fillRegistrationForm(
                FIRST_NAME,
                LAST_NAME,
                generateUniqueEmail(),
                VALID_PASSWORD,
                VALID_PASSWORD
        );

        registerPage.submitRegistrationForm();

        String message = registerPage.getSuccessMessage();

        Assert.assertTrue(
                message.contains("Thank you for registering"),
                "No fue posible preparar el cliente de la prueba."
        );
    }

    @Test(
            groups = {"regression"},
            description = "TC-Order-01 - Añadir producto al carrito"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description(
            "Verificar que un usuario registrado pueda añadir "
                    + "un producto al carrito."
    )
    @Story("TC-Order-01 - Añadir productos al carrito")
    public void tcOrder01AddProductToCart() {

        createLoggedCustomer();

        driver.get(URL + ELECTRONICS_URL);

        productPage.addProductToCart(AIRPODS_SKU);

        String message = productPage.getSuccessMessage();

        Assert.assertTrue(
                message.contains("Apple AirPods Pro 2"),
                "Magento no confirmó que el producto fue agregado. "
                        + "Mensaje: " + message
        );
    }

    @Test(
            groups = {"regression"},
            description = "TC-Order-02 - Eliminar producto del carrito"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description(
            "Verificar que un usuario registrado pueda eliminar "
                    + "un producto previamente agregado."
    )
    @Story("TC-Order-02 - Eliminar productos del carrito")
    public void tcOrder02RemoveProductFromCart() {

        createLoggedCustomer();

        // Agregamos el producto
        driver.get(URL + ELECTRONICS_URL);
        productPage.addProductToCart(AIRPODS_SKU);

        Assert.assertTrue(
                productPage.getSuccessMessage()
                        .contains("Apple AirPods Pro 2"),
                "El producto no fue agregado correctamente."
        );

        // Abrimos el carrito
        driver.get(URL + "/checkout/cart/");

        Assert.assertTrue(
                cartPage.isProductDisplayed(AIRPODS_SKU),
                "El producto no aparece en el carrito."
        );

        // Eliminamos el producto
        cartPage.removeProduct(AIRPODS_SKU);

        String emptyMessage =
                cartPage.getEmptyCartMessage();

        Assert.assertTrue(
                emptyMessage.toLowerCase()
                        .contains("no items"),
                "El carrito no quedó vacío. Mensaje: "
                        + emptyMessage
        );
    }
    @Test(
            groups = {"functional", "integration"},
            description = "TC-Order-03 - Modificar cantidad"
    )
    @Severity(SeverityLevel.NORMAL)
    @Description(
            "Verificar que el usuario pueda modificar la cantidad "
                    + "de un producto agregado al carrito."
    )
    @Story("TC-Order-03 - Modificar cantidad de productos")
    public void tcOrder03UpdateProductQuantity() {

        createLoggedCustomer();
        pause(2);
        // Agregamos el producto
        driver.get(URL + ELECTRONICS_URL);
        productPage.addProductToCart(AIRPODS_SKU);

        Assert.assertTrue(
                productPage.getSuccessMessage()
                        .contains("Apple AirPods Pro 2"),
                "El producto no fue agregado correctamente."
        );

        // Abrimos el carrito y cambiamos la cantidad
        driver.get(URL + "/checkout/cart/");
        pause(2);

        Assert.assertTrue(
                cartPage.isProductDisplayed(AIRPODS_SKU),
                "El producto no aparece en el carrito."
        );

        cartPage.updateProductQuantity(
                AIRPODS_SKU,
                2
        );

        /*
         * Actualizamos la página para confirmar que Magento
         * guardó la cantidad y no fue solamente un cambio visual.
         */
        driver.navigate().refresh();

        Assert.assertEquals(
                cartPage.getProductQuantity(AIRPODS_SKU),
                "2",
                "No conservó la cantidad actualizada."
        );
    }


    @Test(
            groups = {"regression"},
            description = "TC-Order-04 - Creación de orden exitosa"
    )
    @Severity(SeverityLevel.BLOCKER)
    @Description(
            "Verificar que un usuario registrado pueda completar "
                    + "una orden con tres productos diferentes."
    )
    @Story("TC-Order-04 - Creación de orden exitosa")
    public void tcOrder04SuccessfulOrder() {

        createLoggedCustomer();

        // Agregamos Apple AirPods
        driver.get(URL + ELECTRONICS_URL);
        productPage.addProductToCart(AIRPODS_SKU);

        Assert.assertTrue(
                productPage.getSuccessMessage()
                        .contains("Apple AirPods Pro 2")
        );

        // Agregamos Samsung Galaxy
        driver.get(URL + ELECTRONICS_URL);
        productPage.addProductToCart(GALAXY_SKU);

        Assert.assertTrue(
                productPage.getSuccessMessage()
                        .contains("Samsung Galaxy S25 Ultra")
        );

        // Agregamos audífonos Sony
        driver.get(URL + ELECTRONICS_URL);
        productPage.addProductToCart(SONY_SKU);

        Assert.assertTrue(
                productPage.getSuccessMessage()
                        .contains("Sony WH-1000XM6 Headphones")
        );

        // Verificamos los tres productos
        driver.get(URL + "/checkout/cart/");

        Assert.assertTrue(
                cartPage.isProductDisplayed(AIRPODS_SKU)
        );

        Assert.assertTrue(
                cartPage.isProductDisplayed(GALAXY_SKU)
        );

        Assert.assertTrue(
                cartPage.isProductDisplayed(SONY_SKU)
        );

        // Abrimos Checkout
        driver.get(URL + "/checkout/");

        checkoutPage.fillShippingAddress(
                FIRST_NAME,
                LAST_NAME,
                "Avenida Principal 123",
                "MX",
                "Estado de México",
                "Ecatepec",
                "55000",
                "5512345678"
        );

        checkoutPage.selectFlatRateShipping();
        checkoutPage.continueToPayment();

        // Magento utiliza Check / Money Order por defecto
        checkoutPage.placeOrder();

        String successMessage =
                checkoutPage.getSuccessTitle();

        Assert.assertTrue(
                successMessage.contains(
                        "Thank you for your purchase"
                ),
                "Magento no confirmó la orden. Mensaje: "
                        + successMessage
        );

        String generatedOrderNumber =
                checkoutPage.getOrderNumber();

        Assert.assertFalse(
                generatedOrderNumber.trim().isEmpty(),
                "Magento no generó un número de orden."
        );

        System.out.println(
                "Orden creada correctamente: "
                        + generatedOrderNumber
        );
    }
}