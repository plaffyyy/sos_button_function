package ru.sos.button_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Button was activated less than 24 hours ago")
public class CannotActivateButtonException extends RuntimeException {
    public CannotActivateButtonException(String message) {
        super(message);
    }
}
