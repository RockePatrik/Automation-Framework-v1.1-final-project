package com.store.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    /*
     * Dirección de envío.
     *
     * Usamos el atributo name porque los identificadores
     * del checkout de Magento pueden generarse dinámicamente.
     */
    By inputFirstName =
            By.cssSelector("input[name='firstname']");

    By inputLastName =
            By.cssSelector("input[name='lastname']");

    By inputStreet =
            By.cssSelector("input[name='street[0]']");
    By selectCountry =
            By.cssSelector("select[name='country_id']");

    By selectRegion =
            By.cssSelector("select[name='region_id']");
    By inputCity =
            By.cssSelector("input[name='city']");

    By inputPostcode =
            By.cssSelector("input[name='postcode']");
    By inputTelephone =
            By.cssSelector("input[name='telephone']");

    /*
     * Método de envío y navegación.
     */
    By flatRateShipping =
            By.cssSelector(
                    "input[type='radio'][value='flatrate_flatrate']"
            );

    By buttonNext =
            By.cssSelector("button[data-role='opc-continue']");

    // Pago y confirmación.

    By buttonPlaceOrder =
            By.cssSelector("button.action.primary.checkout");

    By successTitle =
            By.cssSelector("span.base");
    By orderNumber =
            By.cssSelector(".checkout-success a");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Completar dirección de envío")
    public void fillShippingAddress(
            String firstName,
            String lastName,
            String street,
            String countryCode,
            String region,
            String city,
            String postcode,
            String telephone
    ) {
        Allure.step("Ingresar datos de dirección", () -> {

            type(inputFirstName, firstName);
            type(inputLastName, lastName);
            type(inputStreet, street);

            Select country = new Select(
                    find(selectCountry)
            );

            country.selectByValue(countryCode);

            /*
             * Magento actualiza dinámicamente los estados
             * después de seleccionar el país.
             */
            wait.until(webDriver -> {
                Select regions = new Select(
                        webDriver.findElement(selectRegion)
                );

                return regions.getOptions().size() > 1;
            });

            Select state = new Select(
                    find(selectRegion)
            );

            state.selectByVisibleText(region);

            type(inputCity, city);
            type(inputPostcode, postcode);
            type(inputTelephone, telephone);
        });
    }

    @Step("Seleccionar método de envío Flat Rate")
    public void selectFlatRateShipping() {

        WebElement shippingMethod = wait.until(
                ExpectedConditions.elementToBeClickable(
                        flatRateShipping
                )
        );

        if (!shippingMethod.isSelected()) {
            shippingMethod.click();
        }
    }

    @Step("Continuar a Review and Payments")
    public void continueToPayment() {

        click(buttonNext);

        wait.until(
                ExpectedConditions.urlContains("#payment")
        );
    }

    @Step("Confirmar y crear la orden")
    public void placeOrder() {

        click(buttonPlaceOrder);

        wait.until(
                ExpectedConditions.urlContains(
                        "/checkout/onepage/success/"
                )
        );
    }

    @Step("Obtener mensaje de orden exitosa")
    public String getSuccessTitle() {
        return text(successTitle);
    }

    @Step("Obtener número de orden")
    public String getOrderNumber() {
        return text(orderNumber);
    }
}