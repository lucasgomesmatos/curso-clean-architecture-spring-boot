package com.catalogo.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void testMain(){
        Assertions.assertNotNull(new Application());
        Application.main(new String[]{});
    }
}
