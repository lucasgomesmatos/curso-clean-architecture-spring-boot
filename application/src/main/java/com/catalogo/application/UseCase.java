package com.catalogo.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);

    public UseCase() {
        // Default constructor
    }
}