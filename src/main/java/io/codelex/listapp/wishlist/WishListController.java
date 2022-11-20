package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.domain.Wish;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wish-list")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @PutMapping("/add-wish")
    @ResponseStatus(HttpStatus.CREATED)
    public Wish addWish(@RequestBody String wishRequest) {
        return wishListService.addWish(wishRequest);
    }

    @PutMapping("/update-wish/{id}")
    public Wish updateWish(@PathVariable int id, @RequestBody String wishUpdate) {
        return wishListService.updateWish(id, wishUpdate);
    }

    @DeleteMapping("/delete-wish/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Wish deleted successfully!")
    public void deleteWishById(@PathVariable int id) {
        wishListService.deleteWishById(id);
    }

    @GetMapping("/get-wish/{id}")
    public Wish getWish(@PathVariable int id) {
        return wishListService.getWish(id);
    }

    @GetMapping("/get-all-wishes")
    public List<Wish> getAllWishes() {
        return wishListService.getAllWishes();
    }
}
