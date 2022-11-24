package io.codelex.listapp.users.dto;

import io.codelex.listapp.users.domain.User;

import java.util.List;

public class UserList {

    private List<User> users;

    public UserList() {
    }

    public UserList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
