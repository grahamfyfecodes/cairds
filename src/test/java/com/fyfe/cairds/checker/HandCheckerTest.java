package com.fyfe.cairds.checker;

import com.fyfe.cairds.card.*;
import com.fyfe.cairds.dealer.Dealer;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HandCheckerTest {

    private final Dealer dealer;
    private final HandChecker testObject;
    private final HandFactory handFactory;

    public HandCheckerTest() {
        this.dealer = new Dealer(new DeckFactory(), new HandFactory());
        handFactory = new HandFactory();
        this.testObject = new HandChecker(handFactory);
    }

    @Test
    public void checkHandQuadAces() {
        Hand hand = handFactory.createHand(
                new Card(Rank.ACE.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.DIAMONDS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand quads = testObject.checkHand(hand);
        System.out.println(quads);
        assertEquals(HandType.QUADS, quads.type());
    }

    @Test
    public void checkHandQuadKings() {
        Hand hand = handFactory.createHand(
                new Card(Rank.KING.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.DIAMONDS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand quads = testObject.checkHand(hand);
        System.out.println(quads);
        assertEquals(HandType.QUADS, quads.type());
    }

    @Test
    public void checkHandFullHouse() {
        Hand hand = handFactory.createHand(
                new Card(Rank.KING.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.DIAMONDS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand fullHouse = testObject.checkHand(hand);
        System.out.println(fullHouse);
        assertEquals(HandType.FULL_HOUSE, fullHouse.type());
    }

    @Test
    public void checkHandTrips() {
        Hand hand = handFactory.createHand(
                new Card(Rank.KING.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.DIAMONDS.ordinal())
        );
        MadeHand trips = testObject.checkHand(hand);
        System.out.println(trips);
        assertEquals(HandType.TRIPS, trips.type());
    }

    @Test
    public void checkHandTwoPair() {
        Hand hand = handFactory.createHand(
                new Card(Rank.KING.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.DIAMONDS.ordinal())
        );
        MadeHand twoPair = testObject.checkHand(hand);
        System.out.println(twoPair);
        assertEquals(HandType.TWO_PAIR, twoPair.type());
    }

    @Test
    public void checkHandTwoPairHigher() {
        Hand hand = handFactory.createHand(
                new Card(Rank.KING.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FIVE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.DIAMONDS.ordinal())
        );
        MadeHand twoPair = testObject.checkHand(hand);
        System.out.println(twoPair);
        assertEquals(HandType.TWO_PAIR, twoPair.type());
    }

    @Test
    public void checkHandPairHigher() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.CLUBS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.DIAMONDS.ordinal())
        );
        MadeHand pair = testObject.checkHand(hand);
        System.out.println(pair);
        assertEquals(HandType.PAIR, pair.type());
    }

    @Test
    public void checkHandFlush() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand flush = testObject.checkHand(hand);
        System.out.println(flush);
        assertEquals(HandType.FLUSH, flush.type());
    }

    @Test
    public void checkHandFlushNot() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand notFlush = testObject.checkHand(hand);
        System.out.println(notFlush);
        assertEquals(HandType.HIGH_CARD, notFlush.type());
    }

    @Test
    public void checkHandStraight() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.THREE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.SIX.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand straight = testObject.checkHand(hand);
        System.out.println(straight);
        assertEquals(HandType.STRAIGHT, straight.type());
    }

    @Test
    public void checkHandStraightWheel() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.THREE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand straight = testObject.checkHand(hand);
        System.out.println(straight);
        assertEquals(HandType.STRAIGHT, straight.type());
    }

    @Test
    public void checkHandStraightBroadway() {
        Hand hand = handFactory.createHand(
                new Card(Rank.QUEEN.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.JACK.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TEN.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal())
        );

        MadeHand straight = testObject.checkHand(hand);
        System.out.println(straight);
        assertEquals(HandType.STRAIGHT, straight.type());
    }

    @Test
    public void checkHandStraightNot() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand notStraight = testObject.checkHand(hand);
        System.out.println(notStraight);
        assertEquals(HandType.HIGH_CARD, notStraight.type());
    }

    @Test
    public void checkHandStraightFiveBetween() {
        Hand hand = handFactory.createHand(
                new Card(Rank.EIGHT.ordinal(), Suit.SPADES.ordinal()),
                new Card(Rank.EIGHT.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal())
        );
        MadeHand notStraight = testObject.checkHand(hand);
        System.out.println(notStraight);
        assertEquals(HandType.TWO_PAIR, notStraight.type());
    }

    @Test
    public void checkHandRoyalFlush() {
        Hand hand = handFactory.createHand(
                new Card(Rank.QUEEN.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.JACK.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TEN.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal())
        );

        MadeHand straightFlush = testObject.checkHand(hand);
        System.out.println(straightFlush);
        assertEquals(HandType.ROYAL_FLUSH, straightFlush.type());
    }

    @Test
    public void checkHandStraightFlushWheel() {
        Hand hand = handFactory.createHand(
                new Card(Rank.FIVE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.FOUR.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.THREE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.ACE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TWO.ordinal(), Suit.HEARTS.ordinal())
        );

        MadeHand straightFlush = testObject.checkHand(hand);
        System.out.println(straightFlush);
        assertEquals(HandType.STRAIGHT_FLUSH, straightFlush.type());
    }

    @Test
    public void checkHandStraightFlush() {
        Hand hand = handFactory.createHand(
                new Card(Rank.QUEEN.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.JACK.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.TEN.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.NINE.ordinal(), Suit.HEARTS.ordinal()),
                new Card(Rank.KING.ordinal(), Suit.HEARTS.ordinal())
        );

        MadeHand straightFlush = testObject.checkHand(hand);
        System.out.println(straightFlush);
        assertEquals(HandType.STRAIGHT_FLUSH, straightFlush.type());
    }
}