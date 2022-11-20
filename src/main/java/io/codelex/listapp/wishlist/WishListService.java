package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.domain.Wish;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public Wish addWish(String wishRequest) {
        validateWishUnique(wishRequest);
        int wishCount = (int) wishListRepository.count();
        Wish newWish = new Wish(wishCount + 1, wishRequest);
        return wishListRepository.save(newWish);
    }

    public Wish updateWish(int id, String wishUpdate) {
        validateWishIdExistence(id);
        validateWishUnique(wishUpdate);
        Wish updatedWish = new Wish(id, wishUpdate);
        return wishListRepository.save(updatedWish);
    }

    public void deleteWishById(int id) {
        validateWishIdExistence(id);
        wishListRepository.deleteById(id);
    }

    public Wish getWish(int id) {
        validateWishIdExistence(id);
        return wishListRepository.findById(id)
                .orElse(new Wish(-1, "No such wish!"));
    }

    public List<Wish> getAllWishes() {
        return wishListRepository.findAll();
    }

    private void validateWishUnique(String wishDescription) {
        if (wishListRepository.existsByDescription(wishDescription)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not add 2 identical wishes!");
        }
    }

    private void validateWishIdExistence(int id) {
        if (!wishListRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such wish in the wishlist, check id!!!");
        }
    }
}
