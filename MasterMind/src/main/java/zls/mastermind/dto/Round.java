/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.dto;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author zshug
 */
public class Round {
   
    private int roundId;
    private String guess;
    private LocalDateTime time;
    private String result;
    private int gameId;
    
    public void setRoundId ( int id){
        this.roundId = id;
    }
    
    public int getRoundId(){
        return this.roundId;
    }
    
    public void setGuess(String answer){
        this.guess = answer;
    }
    
    public String getGuess(){
        return this.guess;
    }
    
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    
    public LocalDateTime getTime(){
        return this.time;
    }
    
    public void setGameId ( int id){
        this.gameId = id;
    }
    
    public int getGameId(){
        return this.gameId;
    }
    
    public void setResult (String result){
        this.result = result;
    }
    
    public String getResult () {
        return this.result;
    }
    
}
