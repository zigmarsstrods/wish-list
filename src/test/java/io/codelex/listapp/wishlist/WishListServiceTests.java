package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.domain.Wish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WishListServiceTests {

    @Mock
    WishListRepository wishListRepository;

    @InjectMocks
    WishListService wishListService;

    private final int wishId = 1;
    private final String newWishDescription = "My first wish";
    private final Wish newWish = createNewWish(wishId, newWishDescription);
    private final int secondWishId = 2;
    private final String wishUpdateDescription = "My first wish update";

    private Wish createNewWish(int id, String description) {
        Wish createdWish = new Wish(description);
        createdWish.setId(id);
        return createdWish;
    }

    @Test
    public void wishShouldBeAdded() {
        Mockito.doAnswer(invocation -> newWish)
                .when(wishListRepository)
                .save(Mockito.any(Wish.class));
        Wish result = wishListService.addWish(newWishDescription);
        Assertions.assertEquals(newWish, result);
    }

    @Test
    public void wishWithSameDescriptionShouldNotBeAdded() {
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsByDescription(Mockito.any(String.class));
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.addWish(newWishDescription));
        Assertions.assertEquals(409, exception.getRawStatusCode());
        Assertions.assertEquals("Can not add 2 identical wishes!", exception.getReason());
    }

    @Test
    public void wishShouldBeUpdated() {
        Wish updatedWish = createNewWish(wishId, wishUpdateDescription);
        Mockito.doAnswer(invocation -> Optional.of(updatedWish))
                .when(wishListRepository)
                .findById(wishId);
        Mockito.doAnswer(invocation -> updatedWish)
                .when(wishListRepository)
                .save(updatedWish);
        Wish result = wishListService.updateWish(wishId, wishUpdateDescription);
        Assertions.assertEquals(updatedWish, result);
    }

    @Test
    public void wishWithWrongIdShouldNotBeUpdated() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.updateWish(secondWishId, wishUpdateDescription));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void wishWithIdenticalDescriptionShouldNotBeUpdated() {
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsByDescription(wishUpdateDescription);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.updateWish(wishId, wishUpdateDescription));
        Assertions.assertEquals(409, exception.getRawStatusCode());
        Assertions.assertEquals("Can not add 2 identical wishes!", exception.getReason());
    }

    @Test
    public void wishShouldBeDeleted() {
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsById(wishId);
        wishListService.deleteWishById(wishId);
        Mockito.verify(wishListRepository).deleteById(wishId);
    }

    @Test
    public void wishWithWrongIdShouldNotBeDeleted() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.deleteWishById(secondWishId));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void wishShouldBeFetched() {
        Mockito.doAnswer(invocation -> Optional.of(newWish))
                .when(wishListRepository)
                .findById(wishId);
        Wish result = wishListService.getWish(wishId);
        Assertions.assertEquals(newWish, result);
    }

    @Test
    public void wishWithWrongIdShouldNotBeFetched() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.getWish(secondWishId));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void allWishesShouldBeFetched() {
        List<Wish> wishList = new ArrayList<>();
        wishList.add(newWish);
        Wish secondWish = createNewWish(secondWishId, wishUpdateDescription);
        wishList.add(secondWish);
        Mockito.doAnswer(invocation -> wishList)
                .when(wishListRepository)
                .findAll();
        List<Wish> result = wishListService.getAllWishes();
        Assertions.assertEquals(wishList, result);
    }
}