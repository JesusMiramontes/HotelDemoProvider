package com.miramontes.hoteldemoprovider.util;

import static org.junit.jupiter.api.Assertions.*;

import com.miramontes.xsdclasses.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ResponseUtilTest {

    private static final String FOUND = "FOUND";
    private static final String DELETED = "DELETED";
    private static final String UPDATED = "UPDATED";

    @Test
    void ok() {
        ResponseStatus generated = ResponseUtil.ok();

        assertNotNull(generated);
        assertEquals(HttpStatus.OK.value(), generated.getCode());
        assertEquals(HttpStatus.OK.name(), generated.getMsg());
    }

    @Test
    void notFound() {
        ResponseStatus generated = ResponseUtil.notFound();

        assertNotNull(generated);
        assertEquals(HttpStatus.NOT_FOUND.value(), generated.getCode());
        assertEquals(HttpStatus.NOT_FOUND.name(), generated.getMsg());
    }

    @Test
    void found() {
        ResponseStatus generated = ResponseUtil.found();

        assertNotNull(generated);
        assertEquals(HttpStatus.OK.value(), generated.getCode());
        assertEquals(FOUND, generated.getMsg());
    }

    @Test
    void deleted() {
        ResponseStatus generated = ResponseUtil.deleted();

        assertNotNull(generated);
        assertEquals(HttpStatus.OK.value(), generated.getCode());
        assertEquals(DELETED, generated.getMsg());
    }

    @Test
    void updated() {
        ResponseStatus generated = ResponseUtil.updated();

        assertNotNull(generated);
        assertEquals(HttpStatus.OK.value(), generated.getCode());
        assertEquals(UPDATED, generated.getMsg());
    }

    @Test
    void created() {
        ResponseStatus generated = ResponseUtil.created();

        assertNotNull(generated);
        assertEquals(HttpStatus.CREATED.value(), generated.getCode());
        assertEquals(HttpStatus.CREATED.name(), generated.getMsg());
    }

    @Test
    void set() {
        // Set up
        final int code = 418;
        final String msg = "I'm a teapot.";

        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(code);
        responseStatus.setMsg(msg);

        ResponseStatus generated = ResponseUtil.set(code, msg);

        // Assertions
        assertNotNull(generated);
        assertEquals(responseStatus.getCode(), generated.getCode());
        assertEquals(responseStatus.getMsg(), generated.getMsg());
    }
}
