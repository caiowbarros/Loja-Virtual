package br.uff.core.entities;

public class CartProduct {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;

    public CartProduct(){
    }

    public CartProduct(
        Integer id,
        Integer cartId,
        Integer productId,
        Integer quantity
    ) {
        this.setId(id);
        this.setCartId(cartId);
        this.setProductId(productId);
        this.setQuantity(quantity);
    }

    public CartProduct(
        Integer cartId,
        Integer productId,
        Integer quantity
    ) {
        this.setCartId(cartId);
        this.setProductId(productId);
        this.setQuantity(quantity);
    }

    private void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
