package com.miramontes.hoteldemoprovider.util;

import com.miramontes.xsdclasses.ResponseStatus;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    private static final String FOUND = "FOUND";
    private static final String DELETED = "DELETED";
    private static final String UPDATED = "UPDATED";

    private ResponseUtil() {}

    public static ResponseStatus ok() {
        return set(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    public static ResponseStatus notFound() {
        return set(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
    }

    public static ResponseStatus found() {
        return set(HttpStatus.OK.value(), FOUND);
    }

    public static ResponseStatus deleted() {
        return set(HttpStatus.OK.value(), DELETED);
    }

    public static ResponseStatus updated() {
        return set(HttpStatus.OK.value(), UPDATED);
    }

    public static ResponseStatus created() {
        return set(HttpStatus.CREATED.value(), HttpStatus.CREATED.name());
    }

    private static ResponseStatus set(int code, String msg) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(code);
        responseStatus.setMsg(msg);
        return responseStatus;
    }
}
