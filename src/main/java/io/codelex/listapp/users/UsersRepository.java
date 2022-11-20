package io.codelex.listapp.users;

import io.codelex.listapp.users.domain.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Usr, Integer> {
}
