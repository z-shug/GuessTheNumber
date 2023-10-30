/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.service;

import java.util.List;
import zls.mastermind.dto.Game;
import zls.mastermind.dto.Round;

/**
 *
 * @author zshug
 */
public interface MasterMindService {
    
    Game beginGame();
    
    Game getGameById(int gameId)throws InvalidGameIDException;
    
    Round createGuessRound(Round round)
            throws InvalidGameIDException, GameFinishedException;
    
    List<Game> getAllGames()throws NoGameFoundException;
    
    List<Round> getRoundsByGameId(int gameId)throws InvalidGameIDException, NoRoundsFoundException;
    
    boolean deleteGameById(int gameId)throws InvalidGameIDException;
}
