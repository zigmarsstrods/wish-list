package io.codelex.listapp.users;

import io.codelex.listapp.users.domain.UserList;
import io.codelex.listapp.users.domain.Usr;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public String getAllNames(UserList users) {
        List<Usr> parsedUserList = users.getUsers();
        validateUserAddition(parsedUserList);
        usersRepository.saveAll(parsedUserList);
        return parsedUserList.stream().map(Usr::getName).collect(Collectors.joining(", "));
    }

    private void validateUserAddition(List<Usr> userList) {
        if (userList.stream().anyMatch(user -> usersRepository.existsById(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not add 2 identical users!");
        }
    }
}
