package com.theoffice.exceptions.accounts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized Request")
public class AccountNotAuthorizedException extends RuntimeException {
}
