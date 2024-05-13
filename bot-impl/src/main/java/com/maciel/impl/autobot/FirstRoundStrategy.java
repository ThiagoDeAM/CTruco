package com.maciel.impl.autobot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;

public class FirstRoundStrategy implements AutoBotStrategy{

    @Override
    public int getRaiseResponse(GameIntel intel) {
        return AutoBotUtil.hasZap(intel.getCards(), intel.getVira()) ? 1 : -1;
    }

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return AutoBotUtil.hasZap(intel.getCards(), intel.getVira()) && intel.getScore() >= 10;
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        return AutoBotUtil.hasManilha(intel.getCards(), intel.getVira());
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        if (intel.getCards().isEmpty()) {
            throw new IllegalStateException("Não há cartas disponíveis para seleção.");
        }
        TrucoCard card = AutoBotUtil.getLowestCard(intel.getCards(), intel.getVira());
        if (card == null) {
            card = intel.getCards().get(0);
        }
        return CardToPlay.of(card);
    }
}
