package ru.sos.button_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Button is not prepared")
public class ButtonNotPreparedException extends RuntimeException {
  public ButtonNotPreparedException(String message) {
    super(message);
  }
}
