package com.fyfe.cairds.card;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class CardFactory {

    private static final Random RAND = new Random();

    public Card createCard(int rank, int suit) {
        if(rank < 0 || rank >= Rank.values().length || suit < 0 || suit >= Rank.values().length) {
            System.out.printf("Trying to create nonsense card: Rank %d Suit %d", rank, suit);
            return new Card(Rank.ACE.ordinal(), Suit.SPADES.ordinal());
        }
        else {
            return new Card(rank, suit);
        }
    }

    public Card createCard(Rank rank, Suit suit) {
        return new Card(rank.ordinal(), suit.ordinal());
    }

    public Card createRandomCard() {
        return new Card(RAND.nextInt(13), RAND.nextInt(4));
    }
}
