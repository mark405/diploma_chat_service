package com.diploma.chat.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private static final String NOT_FOUND_MESSAGE = "NOT_FOUND";

    public NotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }
}
