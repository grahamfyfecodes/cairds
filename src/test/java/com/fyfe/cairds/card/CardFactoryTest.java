package com.fyfe.cairds.card;

import org.junit.Test;

import java.util.stream.IntStream;

public class CardFactoryTest {

    private final CardFactory testObject = new CardFactory();;

    @Test
    public void createNewCard() {
        IntStream.range(0, 52).forEach(
                i -> System.out.println(testObject.createRandomCard())
        );
    }
}