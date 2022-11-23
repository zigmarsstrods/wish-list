package io.codelex.listapp.users;

import io.codelex.listapp.users.dto.UserList;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiOperation(value = "To get names for all users provided in the input JSON array")
    @PutMapping("/get-all-names")
    public String getAllNames(@RequestBody UserList users) {
        return usersService.getAllNames(users);
    }
}
