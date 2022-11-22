package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.exceptions.NotFoundException;
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
        Wish newWish = new Wish(wishRequest);
        return wishListRepository.save(newWish);
    }

    public Wish updateWish(int id, String wishUpdate) {
        validateWishUnique(wishUpdate);
        Wish wishToUpdate = wishListRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        wishToUpdate.setDescription(wishUpdate);
        return wishListRepository.save(wishToUpdate);
    }

    public void deleteWishById(int id) {
        if (!wishListRepository.existsById(id)) {
            throw new NotFoundException();
        }
        wishListRepository.deleteById(id);
    }

    public Wish getWish(int id) {
        return wishListRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Wish> getAllWishes() {
        return wishListRepository.findAll();
    }

    private void validateWishUnique(String wishDescription) {
        if (wishListRepository.existsByDescription(wishDescription)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not add 2 identical wishes!");
        }
    }
}
