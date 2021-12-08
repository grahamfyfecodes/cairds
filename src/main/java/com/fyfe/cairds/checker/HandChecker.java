package com.fyfe.cairds.checker;

import com.fyfe.cairds.card.Card;
import com.fyfe.cairds.card.Hand;
import com.fyfe.cairds.card.HandFactory;
import com.fyfe.cairds.card.Rank;

import java.util.*;
import java.util.stream.Collectors;

import static com.fyfe.cairds.card.HandFactory.HAND_SIZE;

public class HandChecker {

    private final HandFactory handFactory;

    public HandChecker(HandFactory handFactory) {
        this.handFactory = handFactory;
    }

    public MadeHand checkHand(Hand hand) {
        boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(hand);
        Map<Integer, Long> freqMap = createRankFreqMap(hand);
        return
                buildStraightFlush(hand, isFlush, isStraight).orElse(
                buildQuads(hand, freqMap).orElse(
                buildFullHouse(hand, freqMap).orElse(
                buildFlush(hand, isFlush).orElse(
                buildStraight(hand, isStraight).orElse(
                buildTrips(hand, freqMap).orElse(
                buildTwoPairOrPair(hand, freqMap).orElse(
                buildHighCard(hand))))))));
    }

    private Optional<MadeHand> buildStraightFlush(Hand hand, boolean isFlush, boolean isStraight) {
        if(isFlush && isStraight) {
            List<Card> cards = new ArrayList<>(hand.cards());
            cards.sort(getCardComparator(cards));
            return cards.get(0).rank() == Rank.ACE.ordinal() ?
                    Optional.of(new MadeHand(HandType.ROYAL_FLUSH, new Hand(cards))) :
                    Optional.of(new MadeHand(HandType.STRAIGHT_FLUSH, new Hand(cards)));
        }
        else {
            return Optional.empty();
        }
    }

    private Optional<MadeHand> buildFlush(Hand hand, boolean isFlush) {
        if(isFlush) {
            List<Card> cards = new ArrayList<>(hand.cards());
            cards.sort(getCardComparator(cards));
            return Optional.of(new MadeHand(HandType.FLUSH, new Hand(cards)));
        }
        else {
            return Optional.empty();
        }
    }

    private Optional<MadeHand> buildStraight(Hand hand, boolean isStraight) {
        if(isStraight) {
            List<Card> cards = new ArrayList<>(hand.cards());
            cards.sort(getCardComparator(cards));
            return Optional.of(new MadeHand(HandType.STRAIGHT, new Hand(cards)));
        }
        else {
            return Optional.empty();
        }
    }

    private boolean isFlush(Hand hand) {
        return hand.cards().stream()
                .allMatch(card -> card.suit() == hand.cards().get(0).suit());
    }

    private boolean isStraight(Hand hand) {
        List<Card> cards = new ArrayList<>(hand.cards());
        cards.sort(getCardComparator(cards));
        int rank = cards.get(0).trueRank();
        for(int i = 1; i < HAND_SIZE; i ++) {
            if(cards.get(i).rank() != rank - 1) {
                return false;
            }
            else {
                rank--;
            }
        }
        return true;
    }

    private Comparator<? super Card> getCardComparator(List<Card> cards) {
        return cards.stream().anyMatch(card -> card.rank() == Rank.TWO.ordinal()) ?
                Card::straightCompareTo :
                Card::compareTo;
    }

    private Optional<MadeHand> buildQuads(Hand hand, Map<Integer, Long> freqMap) {
        for(Integer rank : freqMap.keySet()) {
            if(freqMap.get(rank) >= 4){
                List<Card> cards = new ArrayList<>(hand.cards());
                List<Card> quads = takeAllOfRank(hand.cards(), rank);
                cards.removeAll(quads);
                return Optional.of(new MadeHand(
                        HandType.QUADS, handFactory.createHand(quads, cards)
                ));
            }
        }
        return Optional.empty();
    }

    private Optional<MadeHand> buildFullHouse(Hand hand, Map<Integer, Long> freqMap) {
        for(Integer rank : freqMap.keySet()) {
            if(freqMap.get(rank) == 3){
                List<Card> cards = new ArrayList<>(hand.cards());
                List<Card> trips = takeAllOfRank(hand.cards(), rank);
                cards.removeAll(trips);
                return freqMap.get(cards.get(0).rank()) == 2 ?
                        Optional.of(new MadeHand(HandType.FULL_HOUSE, handFactory.createHand(trips, cards))) :
                        Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<MadeHand> buildTrips(Hand hand, Map<Integer, Long> freqMap) {
        for(Integer rank : freqMap.keySet()) {
            if(freqMap.get(rank) == 3){
                List<Card> cards = new ArrayList<>(hand.cards());
                List<Card> trips = takeAllOfRank(hand.cards(), rank);
                cards.removeAll(trips);
                cards.sort(Card::compareTo);
                return freqMap.get(cards.get(0).rank()) != 2 ?
                        Optional.of(new MadeHand(HandType.TRIPS, handFactory.createHand(trips, cards))) :
                        Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<MadeHand> buildTwoPairOrPair(Hand hand, Map<Integer, Long> freqMap) {
        for(Integer rank : freqMap.keySet()) {
            if(freqMap.get(rank) == 2){
                List<Card> cards = new ArrayList<>(hand.cards());
                List<Card> pair = takeAllOfRank(hand.cards(), rank);
                cards.removeAll(pair);
                cards.sort(Card::compareTo);

                Optional<List<Card>> sndPairOpt = getSecondPair(freqMap, cards);
                if(sndPairOpt.isPresent()) {
                    List<Card> sndPair = sndPairOpt.get();
                    cards.removeAll(sndPair);
                    return rank > sndPair.get(0).rank() ?
                            Optional.of(new MadeHand(HandType.TWO_PAIR, handFactory.createHand(pair, sndPair, cards))):
                            Optional.of(new MadeHand(HandType.TWO_PAIR, handFactory.createHand(sndPair, pair, cards)));
                }
                else {
                    return Optional.of(new MadeHand(HandType.PAIR, handFactory.createHand(pair, cards)));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<List<Card>> getSecondPair(Map<Integer, Long> freqMap, List<Card> cards) {
        for(int i = 0; i < 2; i++) {
            if(freqMap.get(cards.get(i).rank()) == 2) {
                return Optional.of(takeAllOfRank(cards, cards.get(i).rank()));
            }
        }
        return Optional.empty();
    }

    private MadeHand buildHighCard(Hand hand) {
        List<Card> sortedCards = new ArrayList<>(hand.cards());
        sortedCards.sort(Card::compareTo);
        return new MadeHand(HandType.HIGH_CARD, new Hand(sortedCards));
    }

    private List<Card> takeAllOfRank(List<Card> cards, int rank) {
        return cards.stream()
                .filter(card -> card.rank() == rank)
                .collect(Collectors.toList());
    }

    private Map<Integer, Long> createRankFreqMap(Hand hand) {
        return hand.cards().stream()
                .collect(Collectors.groupingBy(
                        Card::rank,
                        Collectors.counting()
                ));
    }

}
