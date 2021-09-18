package com.iuh.IUHStudent.response;

import java.util.List;

public interface ResponseInterface {
    ResponseStatus status = null;
    String message = null;
    List<String> errors = null;
}
