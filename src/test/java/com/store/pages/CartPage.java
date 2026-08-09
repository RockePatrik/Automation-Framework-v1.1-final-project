package com.store.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

     By emptyCartMessage = By.cssSelector(".cart-empty");
    By buttonUpdateCart = By.cssSelector("button.action.update");
    public CartPage(WebDriver driver) {
        super(driver);
    }

    /*
     * Localiza el campo de cantidad mediante el SKU guardado
     * por Magento en el carrito.
     */
    private By productQuantityBySku(String sku) {
        return By.cssSelector(
                "input.qty[data-cart-item-id='" + sku + "']"
        );
    }

    /*
     * Localiza toda la fila del producto utilizando su SKU.
     */
    private By productRowBySku(String sku) {
        return By.xpath(
                "//input[@data-cart-item-id='" + sku + "']"
                        + "/ancestor::tbody[contains(@class,'cart')]"
        );
    }

    @Step("Verificar producto con SKU {sku} en el carrito")
    public boolean isProductDisplayed(String sku) {
        return isDisplayedElement(
                productQuantityBySku(sku)
        );
    }

    @Step("Eliminar producto con SKU {sku} del carrito")
    public void removeProduct(String sku) {

        WebElement productRow = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        productRowBySku(sku)
                )
        );

        WebElement deleteButton = productRow.findElement(
                By.cssSelector("a.action-delete")
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButton
                )
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        productQuantityBySku(sku)
                )
        );
    }

    @Step("Obtener mensaje del carrito vacío")
    public String getEmptyCartMessage() {
        return text(emptyCartMessage);
    }
    @Step("Cambiar cantidad del producto {sku} a {quantity}")
    public void updateProductQuantity(
            String sku,
            int quantity
    ) {
        By quantityInput =
                productQuantityBySku(sku);

        type(
                quantityInput,
                String.valueOf(quantity)
        );

        click(buttonUpdateCart);

        wait.until(
                ExpectedConditions.attributeToBe(
                        quantityInput,
                        "value",
                        String.valueOf(quantity)
                )
        );
    }

    @Step("Obtener cantidad del producto {sku}")
    public String getProductQuantity(String sku) {
        return find(
                productQuantityBySku(sku)
        ).getAttribute("value");
    }
}