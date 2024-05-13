package com.maciel.impl.autobot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;

public class SecondRoundStrategy implements AutoBotStrategy{
    @Override
    public int getRaiseResponse(GameIntel intel) {
        if (AutoBotUtil.getLastRoundResult().equals(GameIntel.RoundResult.LOST) && AutoBotUtil.calculateAverageCardValue(intel.getCards(), intel.getVira()) > 7) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return intel.getScore() >= 10 && (AutoBotUtil.shouldPlayDefensively(intel) || AutoBotUtil.hasZap(intel.getCards(), intel.getVira()));
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        return AutoBotUtil.getLastRoundResult().equals(GameIntel.RoundResult.LOST) && AutoBotUtil.hasStrongPair(intel);
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        if (intel.getCards().isEmpty()) {
            throw new IllegalStateException("Não há cartas disponíveis para seleção");
        }
        TrucoCard card;
        if (AutoBotUtil.getLastRoundResult().equals(GameIntel.RoundResult.WON)) {
            card = AutoBotUtil.getLowestCard(intel.getCards(), intel.getVira());
        } else {
            card = AutoBotUtil.selectHighestNonManilhaCard(intel.getCards(), intel.getVira());
        }
        if (card == null) {
            card = intel.getCards().get(0);
        }
        return CardToPlay.of(card);
    }
}
