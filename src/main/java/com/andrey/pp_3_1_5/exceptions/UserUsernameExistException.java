package com.andrey.pp_3_1_5.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserUsernameExistException extends DataIntegrityViolationException {
    public UserUsernameExistException(String msg) {


        super(msg);
    }
}
