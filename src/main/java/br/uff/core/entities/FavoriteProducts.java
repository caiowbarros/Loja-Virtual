package br.uff.core.entities;

public class FavoriteProducts {
    private Integer id;
    private Integer userId;
    private Integer productId;

    public FavoriteProducts(){
    }

    public FavoriteProducts(
        Integer id,
        Integer userId,
        Integer productId
    ) {
        this.setId(id);
        this.setUserId(userId);
        this.setProductId(productId);
    }

    public FavoriteProducts(
        Integer userId,
        Integer productId
    ) {
        this.setUserId(userId);
        this.setProductId(productId);
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
