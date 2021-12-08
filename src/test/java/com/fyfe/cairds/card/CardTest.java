package com.fyfe.cairds.card;

import org.junit.Test;

public class CardTest {

    private final CardFactory cardFactory = new CardFactory();

    @Test
    public void compareTo() {
        Card threeOfHearts = cardFactory.createCard(Rank.THREE, Suit.HEARTS);
        Card twoOfSpades = cardFactory.createCard(Rank.TWO, Suit.HEARTS);
        Card fourOfSpades = cardFactory.createCard(Rank.FOUR, Suit.SPADES);
        Card kingOfHearts = cardFactory.createCard(Rank.KING, Suit.HEARTS);
        Card aceOfHearts = cardFactory.createCard(Rank.ACE, Suit.HEARTS);


        System.out.println(threeOfHearts.compareTo(fourOfSpades));
//        System.out.println(fourOfSpades.compareTo(twoOfHearts));
//        System.out.println(twoOfHearts.compareTo(twoOfSpades));
        System.out.println(kingOfHearts.compareTo(aceOfHearts));
    }
}