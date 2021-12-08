package com.fyfe.cairds.dealer;

import com.fyfe.cairds.card.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Dealer {

    private final DeckFactory deckFactory;
    private final HandFactory handFactory;
    private Deck deck;

    public Dealer(DeckFactory deckFactory, HandFactory handFactory) {
        this.deckFactory = deckFactory;
        this.handFactory = handFactory;
        this.deck = newShuffle();
    }

    public Optional<Hand> takeHandFromDeck() {
        return handFactory.takeHandFromDeck(deck);
    }

    public Deck newShuffle() {
        return deckFactory.createShuffledDeck();
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }
}
