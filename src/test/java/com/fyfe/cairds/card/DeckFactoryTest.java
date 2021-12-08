package com.fyfe.cairds.card;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DeckFactoryTest {

    private final DeckFactory testObject = new DeckFactory();

    @Test
    public void createDeck() {
        testObject.createDeck().cards().forEach(System.out::println);
    }

    @Test
    public void createShuffledDeck() {
        testObject.createShuffledDeck().cards().forEach(System.out::println);
    }

    @Test
    public void isShuffledDeckUnique() {
        Deck deck = testObject.createShuffledDeck();
        Set<Card> cards = new HashSet<>(deck.cards());
        assertEquals(deck.cards().size(), cards.size());
    }
}