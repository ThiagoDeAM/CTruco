package com.maciel.impl.autobot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;

public class ThirdRoundStrategy implements AutoBotStrategy{
    @Override
    public int getRaiseResponse(GameIntel intel) {
        if (intel.getHandPoints() == 12 || AutoBotUtil.shouldPlayAggressively(intel)) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return intel.getScore() >= 10 && AutoBotUtil.hasZap(intel.getCards(), intel.getVira());
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        return intel.getScore() <= intel.getOpponentScore() && AutoBotUtil.hasStrongPair(intel);
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        if (intel.getCards().isEmpty()) {
            throw new IllegalStateException("Não há cartas disponíveis para seleção");
        }
        TrucoCard card = AutoBotUtil.getHighestCard(intel.getCards(), intel.getVira());
        if (card == null) {
            card = intel.getCards().get(0);
        }
        return CardToPlay.of(card);
    }
}
