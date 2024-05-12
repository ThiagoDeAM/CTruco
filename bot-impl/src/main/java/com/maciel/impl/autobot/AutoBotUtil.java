package com.maciel.impl.autobot;

import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AutoBotUtil {
    private static List<GameIntel.RoundResult> roundResults = Collections.emptyList();

    public static boolean hasManilha(GameIntel intel){
        return intel.getCards().stream().anyMatch(card -> card.isManilha(intel.getVira()));
    }

    public static boolean hasStrongPair(GameIntel intel){
        return intel.getCards().stream().filter(card -> card.relativeValue(intel.getVira()) > 10).count() >= 2;
    }

    public static double calculateAverageCardValue(List<TrucoCard> cards, TrucoCard vira){
        return cards.stream().mapToInt(card -> card.relativeValue(vira)).average().orElse(0.0);
    }

    public static boolean shouldPlayDefensively(GameIntel intel){
        return intel.getScore() > intel.getOpponentScore() && (intel.getScore() - intel.getOpponentScore() > 2);
    }

    public static boolean shouldPlayAggressively(GameIntel intel){
        return intel.getOpponentScore() > intel.getScore() && (intel.getOpponentScore() - intel.getScore() > 2);
    }

    public static TrucoCard selectHighestNonManilhaCard(List<TrucoCard> cards, TrucoCard vira){
        return cards.stream()
                .filter(card -> !card.isManilha(vira))
                .max(Comparator.comparingInt(card -> card.relativeValue(vira)))
                .orElse(null);
    }

    public static TrucoCard getHighestCard(List<TrucoCard> cards, TrucoCard vira) {
        return cards.stream()
                .max(Comparator.comparingInt(card -> card.relativeValue(vira)))
                .orElse(null);
    }

    public static TrucoCard getLowestCard(List<TrucoCard> cards, TrucoCard vira) {
        return cards.stream().min(Comparator.comparingInt(card -> card.relativeValue(vira)))
                .orElse(null);
    }

    public static void setRoundResults(List<GameIntel.RoundResult> newRoundResults) {
        roundResults = Optional.ofNullable(newRoundResults).orElse(Collections.emptyList());
    }

    public static Optional<GameIntel.RoundResult> getLastRoundResult() {
        if (roundResults.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(roundResults.get(roundResults.size() - 1));
    }
}

