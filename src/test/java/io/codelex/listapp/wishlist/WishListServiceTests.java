package io.codelex.listapp.wishlist;

import io.codelex.listapp.wishlist.domain.Wish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class WishListServiceTests {

    @Mock
    WishListRepository wishListRepository;

    @InjectMocks
    WishListService wishListService;

    private final String newWishDescription = "My first wish";
    int wishId = 1;
    int wrongWishId = 2;
    private final Wish newWish = new Wish(wishId, newWishDescription);
    String wishUpdateDescription = "My first wish update";


    @Test
    public void wishShouldBeAdded() {
        Mockito.doAnswer(invocation -> newWish)
                .when(wishListRepository)
                .save(Mockito.any(Wish.class));
        Wish result = wishListService.addWish(newWishDescription);
        Assertions.assertEquals(newWish, result);
        Assertions.assertEquals(wishId, result.getId());
        Assertions.assertEquals(newWishDescription, result.getDescription());
    }

    @Test
    public void WishWithSameDescriptionShouldNotBeAdded() {
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
        Wish updatedWish = new Wish(wishId, wishUpdateDescription);
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsById(wishId);
        Mockito.doAnswer(invocation -> updatedWish)
                .when(wishListRepository)
                .save(updatedWish);
        Wish result = wishListService.updateWish(wishId, wishUpdateDescription);
        Assertions.assertEquals(updatedWish, result);
        Assertions.assertEquals(wishId, result.getId());
        Assertions.assertEquals(wishUpdateDescription, result.getDescription());
    }

    @Test
    public void wishWithWrongIdShouldNotBeUpdated() {
        Mockito.doAnswer(invocation -> false)
                .when(wishListRepository)
                .existsById(wrongWishId);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.updateWish(wrongWishId, wishUpdateDescription));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void wishWithIdenticalDescriptionShouldNotBeUpdated() {
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsById(wishId);
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
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .deleteById(wishId);
        wishListService.deleteWishById(wishId);
    }

    @Test
    public void wishWithWrongIdShouldNotBeDeleted() {
        Mockito.doAnswer(invocation -> false)
                .when(wishListRepository)
                .existsById(wrongWishId);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.deleteWishById(wrongWishId));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void wishShouldBeFetched() {
        Mockito.doAnswer(invocation -> true)
                .when(wishListRepository)
                .existsById(wishId);
        Mockito.doAnswer(invocation -> Optional.of(newWish))
                .when(wishListRepository)
                .findById(wishId);
        Wish result = wishListService.getWish(wishId);
        Assertions.assertEquals(newWish, result);
        Assertions.assertEquals(wishId, result.getId());
        Assertions.assertEquals(newWishDescription, result.getDescription());

    }

    @Test
    public void wishWithWrongIdShouldNotBeFetched() {
        Mockito.doAnswer(invocation -> false)
                .when(wishListRepository)
                .existsById(wrongWishId);
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> wishListService.getWish(wrongWishId));
        Assertions.assertEquals(404, exception.getRawStatusCode());
        Assertions.assertEquals("There is no such wish in the wishlist, check id!!!", exception.getReason());
    }

    @Test
    public void allWishesShouldBeFetched() {
        List<Wish> wishList = new ArrayList<>();
        wishList.add(newWish);
        Wish secondWish = new Wish(2, wishUpdateDescription);
        wishList.add(secondWish);
        Mockito.doAnswer(invocation -> wishList)
                .when(wishListRepository)
                .findAll();
        List<Wish> result = wishListService.getAllWishes();
        Assertions.assertEquals(wishList, result);
        Assertions.assertEquals(newWish, result.get(0));
        Assertions.assertEquals(secondWish, result.get(1));

    }
}
