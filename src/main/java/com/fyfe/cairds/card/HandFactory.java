package com.fyfe.cairds.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HandFactory {

    public static final int HAND_SIZE = 5;

    public Optional<Hand> takeHandFromDeck(Deck deck) {
        if(deck.cards().size() < HAND_SIZE){
            return Optional.empty();
        }
        else {
            List<Card> cards = deck.cards().stream()
                    .limit(HAND_SIZE)
                    .collect(Collectors.toList());
            deck.cards().removeAll(cards);
            return Optional.of(new Hand(cards));
        }
    }

    public Hand createHand(Card... cards) {
        return new Hand(Arrays.asList(cards));
    }

    @SafeVarargs
    public final Hand createHand(List<Card>... cards) {
        List<Card> allCards = new ArrayList<>();
        for(List<Card> cs: cards) {
            allCards.addAll(cs);
        }
        return new Hand(allCards);
    }
}
