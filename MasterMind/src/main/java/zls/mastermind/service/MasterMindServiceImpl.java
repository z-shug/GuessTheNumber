/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zls.mastermind.dao.MasterMindDBDao;
import zls.mastermind.dao.MasterMindDao;
import zls.mastermind.dto.Game;
import zls.mastermind.dto.Round;

/**
 *
 * @author zshug
 */
@Service
public class MasterMindServiceImpl implements MasterMindService{
    
    @Autowired
    MasterMindDao dao;
    
    public MasterMindServiceImpl(MasterMindDBDao dao){
        this.dao = dao;
    }

    @Override
    public Game beginGame() {
        Game game = new Game();
        game.setAnswer(genAnswer());
        game.setStatus("In Progress");
        game = dao.addGame(game);
        return game;
        
    }
    
    private String genAnswer(){
        List<Integer> intList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
        Random rand = new Random();
        String answer = "";
        for (int i = 0; i<4; i++){
            answer += intList.remove(rand.nextInt(intList.size()));
        }
        return answer;
    }
    
    private Game hideAnswer(Game game){
        if(game.getStatus().equals("In Progress")){
            game.setAnswer("XXXX");
        }
        return game;
    }

    @Override
    public Game getGameById(int gameId)throws InvalidGameIDException{
        Game game = dao.getGame(gameId);
        if(game == null){
            throw new InvalidGameIDException("Game with ID" + gameId + "not found");
        }
        if(game.getStatus().contains("In Progress")){
            return hideAnswer(game);
        }else{
            return game;
        }
        
    }

    @Override
    public Round createGuessRound(Round round)throws InvalidGameIDException, GameFinishedException {
        int e= 0;
        int p = 0;
        
        int gameId = round.getGameId();
        String guess = round.getGuess();
        Game game = dao.getGame(round.getGameId());
        if (game == null){
            throw new InvalidGameIDException("Game with ID " + gameId + " not found");
        }else if(game.getStatus().contains("Finished")){
            throw new GameFinishedException("Game with ID " + gameId + " is already finished");
        }
        String answer = game.getAnswer();
        for ( int i =0; i<guess.length();i++){
               if(answer.contains(guess.substring(i,i+1))){
                   if (answer.substring(i,i+1).equals(guess.substring(i,i+1))){
                       e++;
                   } else {
                        p++;
                    }
               }
            }
        if(e == 4){
            game.setStatus("Finished");
            dao.updateStatus(game);
        }
        String guessFormat = String.format("E:%sP:%s",e,p);
        round.setResult(guessFormat); 
        LocalDateTime ldt = LocalDateTime.now();
        round.setTime(ldt.withNano(0));
        dao.addRound(round);
        return round;
    }

    @Override
    public List<Game> getAllGames() throws NoGameFoundException{
        List<Game> games = dao.getAllGames();
        if(games.isEmpty()){
            throw new NoGameFoundException("There are no games being played");
        }
        for(Game game:games){
           if(game.getStatus().contains("In Progress")){
            hideAnswer(game);
            }
           
        }
        return games;  
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId)
        throws InvalidGameIDException, NoRoundsFoundException{
        
        Game game = dao.getGame(gameId);
        if(game ==  null){
            throw new InvalidGameIDException("Game with ID " + gameId +" not found!");
        }
        
        List<Round> rounds = dao.getRounds(gameId);
        if(rounds.isEmpty()){
            throw new NoRoundsFoundException("There are no rounds played for Game ID " + gameId);
        }
        return rounds;
    }

    @Override
    public boolean deleteGameById(int gameId)throws InvalidGameIDException{
        boolean gameDeleted = dao.deleteGame(gameId);
        if(gameDeleted){
            return gameDeleted;
        }else{
            throw new InvalidGameIDException("Game with ID " +gameId+" not found");
        }
    }  
}
