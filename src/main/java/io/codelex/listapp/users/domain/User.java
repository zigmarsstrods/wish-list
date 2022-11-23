package io.codelex.listapp.users.domain;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull
    private String type;
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String email;

    public User() {
    }

    public User(String type, Integer id, String name, String email) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
