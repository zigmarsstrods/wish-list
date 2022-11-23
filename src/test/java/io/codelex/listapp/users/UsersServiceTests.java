package io.codelex.listapp.users;

import io.codelex.listapp.users.dto.UserList;
import io.codelex.listapp.users.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UsersServiceTests {

    private final UsersService usersService = new UsersService();

    @Test
    public void allUserNamesShouldBeFetched() {
        String name1 = "zigmarsstrods";
        String name2 = "janisberzins";
        String type = "user";
        User testUser1 = new User(type, 1, name1, "zigmars.strods@gmail.com");
        User testUser2 = new User(type, 2, name2, "janis.berzins@inbox.lv");
        List<User> rawUserList = new ArrayList<>();
        rawUserList.add(testUser1);
        rawUserList.add(testUser2);
        UserList testUserList = new UserList();
        testUserList.setUsers(rawUserList);
        String result = usersService.getAllNames(testUserList);
        String expectedString = String.join(", ", name1, name2);
        Assertions.assertEquals(expectedString, result);
    }
}
