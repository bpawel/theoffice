package com.theoffice.exceptions.accounts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccountConflictException extends RuntimeException {
}
