package org.example.ws.web;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by arthur on 20/04/17.
 */
public interface ExceptionAttributes {
    Map<String, Object> getExceptionAttributes(Exception exception,
                                               HttpServletRequest httpRequest, HttpStatus httpStatus);
}
