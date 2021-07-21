package br.uff.core.entities;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer roleId;

    public User() {
    }

    public User(
        String name,
        String email,
        String password,
        Integer roleId
    ) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setRoleId(roleId);
    }

    public User(
        Integer id,
        String name,
        String email,
        String password,
        Integer roleId
    ) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setRoleId(roleId);
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
