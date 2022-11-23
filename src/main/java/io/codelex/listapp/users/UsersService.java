package io.codelex.listapp.users;

import io.codelex.listapp.users.dto.UserList;
import io.codelex.listapp.users.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    public String getAllNames(UserList users) {
        List<User> parsedUserList = users.getUsers();
        return parsedUserList.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));
    }
}
