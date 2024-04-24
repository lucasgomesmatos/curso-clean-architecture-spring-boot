package com.catalogo.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.AbstractEnvironment;

class ApplicationTest {

    @Test
    void testMain(){
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        Assertions.assertNotNull(new Application());
        Application.main(new String[]{});
    }
}
