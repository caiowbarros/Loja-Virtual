package br.uff.core.entities;

public class Category {
    private Integer id;
    private String name;
    private Integer fatherCategoryId;

    public Category(){
    }

    public Category(
        Integer id,
        String name,
        Integer fatherCategoryId
    ) {
        this.setId(id);
        this.setName(name);
        this.setFatherCategoryId(fatherCategoryId);
    }

    public Category(
        String name,
        Integer fatherCategoryId
    ) {
        this.setName(name);
        this.setFatherCategoryId(fatherCategoryId);
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFatherCategoryId(Integer fatherCategoryId) {
        this.fatherCategoryId = fatherCategoryId;
    }

    public Integer getFatherCategoryId() {
        return fatherCategoryId;
    }
}
