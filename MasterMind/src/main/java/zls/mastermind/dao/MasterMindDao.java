/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.dao;

import java.util.List;
import zls.mastermind.dto.Game;
import zls.mastermind.dto.Round;

/**
 *
 * @author zshug
 */
public interface MasterMindDao {
    
    Game addGame(Game game);
    
    Round addRound(Round round);
    
    List<Game> getAllGames();
    
    Game getGame(int gameId);
    
    List<Round> getRounds(int gameId);
    
    boolean updateStatus(Game game);
    
    boolean deleteGame(int gameId);
    
    
}
