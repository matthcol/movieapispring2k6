package org.example.movieapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Data not found")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String dataType, int dataId) {
        super(MessageFormat.format("Data {0} with id {1} not found", dataId, dataId));
    }
}
