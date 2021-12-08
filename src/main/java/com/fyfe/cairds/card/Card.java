package com.fyfe.cairds.card;

public record Card(int rank, int suit) implements Comparable<Card> {

    @Override
    public String toString() {
        return Rank.values()[rank].toString() + " of " + Suit.values()[suit].toString();
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(card.trueRank(), trueRank());
    }

    public int straightCompareTo(Card card) {
        return Integer.compare(card.rank, rank);
    }

    public int trueRank() {
        return rank == Rank.ACE.ordinal() ? 13 : rank;
    }
}
