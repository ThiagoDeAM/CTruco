package com.maciel.impl.autobot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;

public interface AutoBotStrategy {
    int getRaiseResponse(GameIntel intel);
    boolean getMaoDeOnzeResponse(GameIntel intel);
    boolean decideIfRaises(GameIntel intel);
    CardToPlay chooseCard(GameIntel intel);
}
