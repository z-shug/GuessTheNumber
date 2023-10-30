/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.dto;

/**
 *
 * @author zshug
 */
public class Game {
    
    private int gameId;
    private String answer;
    private String status;
    
    
    public void setGameId ( int id){
        this.gameId = id;
    }
    
    public int getGameId(){
        return this.gameId;
    }
    
    public void setAnswer(String answer){
        this.answer = answer;
    }
    
    public String getAnswer(){
        return this.answer;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }
}
