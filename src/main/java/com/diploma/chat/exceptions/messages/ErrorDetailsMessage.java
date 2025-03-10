package com.diploma.chat.exceptions.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorDetailsMessage {
    private final String message;
}
