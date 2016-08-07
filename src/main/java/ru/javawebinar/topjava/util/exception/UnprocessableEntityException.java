package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jager on 07.08.2016.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "no data found") //422
public class UnProcessableEntityException extends RuntimeException {
    public UnProcessableEntityException(String message)
    {
        super(message);
    }
}
