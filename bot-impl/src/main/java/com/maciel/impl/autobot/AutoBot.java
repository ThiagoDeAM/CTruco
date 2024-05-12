package com.maciel.impl.autobot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.service.BotServiceProvider;
import java.util.Optional;
public class AutoBot implements BotServiceProvider {

    private AutoBotStrategy strategy;
    private GameIntel gameIntel;

    public AutoBot(){
        this.strategy = new FirstRoundStrategy();
    }
    public void updateGameIntelAndStrategy(GameIntel intel) {
        this.gameIntel = intel;
        updateStrategyBasedOnRound();
    }
    private void updateStrategyBasedOnRound() {
        int roundNumber = gameIntel.getRoundResults().size() + 1;
        this.strategy = chooseStrategy(roundNumber);
    }
    private AutoBotStrategy chooseStrategy(int roundNumber) {
        return switch (roundNumber) {
            case 1 -> new FirstRoundStrategy();
            case 2 -> new SecondRoundStrategy();
            case 3 -> new ThirdRoundStrategy();
            default -> throw new IllegalStateException("Número de rodada inválido: " + roundNumber);
        };
    }
    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        updateGameIntelAndStrategy(intel);
        return strategy.getMaoDeOnzeResponse(intel);
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        updateGameIntelAndStrategy(intel);
        return strategy.decideIfRaises(intel);
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        updateGameIntelAndStrategy(intel);
        return strategy.chooseCard(intel);
    }

    @Override
    public int getRaiseResponse(GameIntel intel) {
        updateGameIntelAndStrategy(intel);
        return strategy.getRaiseResponse(intel);
    }

    @Override
    public String getName() {
        return BotServiceProvider.super.getName();
    }
}
