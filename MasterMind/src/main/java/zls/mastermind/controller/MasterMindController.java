/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import zls.mastermind.dto.Game;
import zls.mastermind.dto.Round;
import zls.mastermind.service.GameFinishedException;
import zls.mastermind.service.InvalidGameIDException;
import zls.mastermind.service.MasterMindService;
import zls.mastermind.service.NoGameFoundException;
import zls.mastermind.service.NoRoundsFoundException;

/**
 *
 * @author zshug
 */

@RestController
@RequestMapping("/api")
public class MasterMindController {
    
    private final MasterMindService service;
    
    public MasterMindController(MasterMindService service) {
        this.service = service; 
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game startGame() {
       Game game = service.beginGame();
       int gameID = game.getGameId();
       return game;
    }
    
    @PostMapping("/guess/{id}/{playerGuess}")
    @ResponseStatus(HttpStatus.CREATED)
    public Round guess(@PathVariable int id, @PathVariable String playerGuess) throws InvalidGameIDException, GameFinishedException{
        Round round = new Round();
        round.setGameId(id);
        round.setGuess(playerGuess);
        Round guessRound =  service.createGuessRound(round);
        return guessRound;
    }
    
    @GetMapping("game")
    public List<Game> listGames() throws NoGameFoundException{
        return service.getAllGames();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable int gameId) throws InvalidGameIDException{
        Game game = service.getGameById(gameId);
        if(game == null){
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
    @GetMapping("/round/{gameId}")
    public List<Round> getRounds(@PathVariable int gameId) throws InvalidGameIDException, NoRoundsFoundException{
        List<Round> rounds = service.getRoundsByGameId(gameId);
        return rounds;
    }
    
    
}


