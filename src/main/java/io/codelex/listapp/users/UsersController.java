package io.codelex.listapp.users;

import io.codelex.listapp.users.domain.UserList;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PutMapping("/get-all-names")
    public String getAllNames(@RequestBody UserList users) {
        return usersService.getAllNames(users);
    }
}
