package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<Wish, Integer> {
    boolean existsByDescription(String description);
}
