package braintrain.model.card;

import static braintrain.testutil.TypicalCards.CARD_BELGIUM;
import static braintrain.testutil.TypicalCards.CARD_JAPAN;
import static braintrain.testutil.TypicalCards.CARD_JAPAN_CORE1;
import static braintrain.testutil.TypicalCards.CARD_JAPAN_CORE2;
import static braintrain.testutil.TypicalCards.CARD_JAPAN_OPT1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import braintrain.model.card.exceptions.MissingCoreException;
import braintrain.model.card.exceptions.MissingOptionalException;
import braintrain.testutil.Assert;
import braintrain.testutil.CardBuilder;

/**
 * Tests the {@code Card} object (100% coverage).
 */
public class CardTest {
    @Test
    public void equals() {
        // Different type of object -> return false
        assertFalse(CARD_BELGIUM.equals(new Object()));

        // Same object -> returns true
        assertTrue(CARD_BELGIUM.equals(CARD_BELGIUM));

        // Different object -> returns false
        assertFalse(CARD_BELGIUM.equals(CARD_JAPAN));

        // Same cores and optionals -> returns true
        Card belgiumCopy = new CardBuilder(CARD_BELGIUM).build();
        assertTrue(CARD_BELGIUM.equals(belgiumCopy));

        // Same cores with modified optionals -> returns false
        Card modifiedCopy = new CardBuilder(CARD_JAPAN).withOptionals("Same characters as Kyoto").build();
        assertFalse(CARD_JAPAN.equals(modifiedCopy));

        // Modify existing card to have same cores and optionals as another card -> returns true
        modifiedCopy = new CardBuilder(CARD_BELGIUM)
                .withCores(CARD_JAPAN_CORE1, CARD_JAPAN_CORE2)
                .withOptionals(CARD_JAPAN_OPT1).build();
        assertTrue(CARD_JAPAN.equals(modifiedCopy));

        // Same cores and optionals but different order for cores -> returns false
        modifiedCopy = new CardBuilder(CARD_JAPAN).withCores(CARD_JAPAN_CORE2, CARD_JAPAN_CORE1).build();
        assertFalse(CARD_JAPAN.equals(modifiedCopy));
    }

    @Test
    public void setAndGetCoresAndOptionals() {
        Card belgiumCopy = new CardBuilder(CARD_BELGIUM).build();
        Card japanCopy = new CardBuilder(CARD_JAPAN).build();
        // These two cards have different cores and optionals and should not be equal.
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setCores(japanCopy.getCores());
        // Despite having the same cores, the two cards still have different optionals.
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setOptionals(japanCopy.getOptionals());
        // Both cards have the same cores and optionals, and should be treated as equivalents.
        assertEquals(belgiumCopy, japanCopy);
    }

    @Test
    public void setAndGetCoreAndOptional() {
        Card belgiumCopy = new CardBuilder(CARD_BELGIUM).build();
        Card japanCopy = new CardBuilder(CARD_JAPAN).build();
        assertNotEquals(belgiumCopy, japanCopy);

        belgiumCopy.setCore(0, japanCopy.getCore(0));
        assertEquals(belgiumCopy.getCore(0), japanCopy.getCore(0));

        belgiumCopy.setOptional(0, japanCopy.getOptional(0));
        assertEquals(belgiumCopy.getOptional(0), japanCopy.getOptional(0));
    }

    @Test
    public void cardToString() {
        Card belgiumCopy = new CardBuilder(CARD_BELGIUM).build();
        Card newCard = new Card(belgiumCopy.getCores(), belgiumCopy.getOptionals());
        // newCard should be a copy of belgiumCopy
        assertEquals(belgiumCopy, newCard);
        // since both cards are identical, their string representation should be the same
        assertEquals(belgiumCopy.toString(), newCard.toString());
    }

    @Test
    public void getCore_invalidIndex_throwsMissingCoreException() {
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(0), () -> {
            Card testCard = new CardBuilder(CARD_JAPAN).build();
            testCard.setCore(0, "");
            testCard.getCore(0);
        });
    }

    @Test
    public void getOptional_invalidIndex_throwsMissingOptionalException() {
        Assert.assertThrows(MissingOptionalException.class, MissingOptionalException.generateMessage(0), () -> {
            Card testCard = new CardBuilder(CARD_JAPAN).build();
            testCard.setOptional(0, "");
            testCard.getOptional(0);
        });
    }
}
