package com.catalogo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {


    @Test
    void testNewCategory() {
        Assertions.assertNotNull(new Category());
    }
}