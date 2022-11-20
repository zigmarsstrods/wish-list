package io.codelex.listapp.users.domain;

import java.util.List;

public class UserList {

    private List<Usr> users;

    public UserList() {
    }

    public UserList(List<Usr> users) {
        this.users = users;
    }

    public List<Usr> getUsers() {
        return users;
    }

    public void setUsers(List<Usr> users) {
        this.users = users;
    }
}
