package io.codelex.listapp.users;

import io.codelex.listapp.users.domain.UserList;
import io.codelex.listapp.users.domain.Usr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UsersServiceTests {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UsersService usersService;

    String name = "zigmarsstrods";
    int id = 1;
    UserList testUserList = new UserList();
    Usr testUser = new Usr("user", id, name, "zigmars.strods@gmail");
    List<Usr> rawUserList = new ArrayList<>();

    @BeforeEach
    public void setupUserList() {
        rawUserList.add(testUser);
        testUserList.setUsers(rawUserList);
    }

    @Test
    public void allUserNamesShouldBeFetched() {
        String result = usersService.getAllNames(testUserList);
        Assertions.assertEquals(name, result);

    }

    @Test
    public void theSameUserNamesShouldBeFetched() {
        Mockito.doAnswer(invocation -> true)
                .when(usersRepository)
                .existsById(id);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> usersService.getAllNames(testUserList));
        Assertions.assertEquals(409, exception.getRawStatusCode());
        Assertions.assertEquals("Can not add 2 identical users!", exception.getReason());
    }
}
