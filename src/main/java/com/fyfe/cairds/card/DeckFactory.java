package com.fyfe.cairds.card;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckFactory {

    public Deck createDeck() {
        List<Card> cards = new ArrayList<>();
        for(int r = 0; r < Rank.values().length; r++) {
            for(int s = 0; s < Suit.values().length; s++) {
                cards.add(new Card(r, s));
            }
        }
        return new Deck(cards);
    }

    public Deck createShuffledDeck() {
        Deck deck = createDeck();
        Collections.shuffle(deck.cards());
        return deck;
    }
}
