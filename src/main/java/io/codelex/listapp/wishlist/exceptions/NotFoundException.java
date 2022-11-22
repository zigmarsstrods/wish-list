package io.codelex.listapp.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "There is no such wish in the wishlist, check id!!!");
    }
}
