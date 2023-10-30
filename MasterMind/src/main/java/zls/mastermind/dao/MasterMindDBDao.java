/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zls.mastermind.dto.Game;
import zls.mastermind.dto.Round;

/**
 *
 * @author zshug
 */
@Repository
@Profile("database")
public class MasterMindDBDao implements MasterMindDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired 
    public MasterMindDBDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final String ADD_GAME = 
            "INSERT INTO Game(answer, status) VALUES (?,?)";
    private final String ADD_ROUND = 
            "INSERT INTO Round (guess, guessTime, result, gameID) VALUES (?,?,?,?) ";
    private final String GET_GAMES = 
            "SELECT  gameID, answer, status FROM Game";
    private final String GET_GAME_BY_ID = 
            "SELECT gameID, answer, status FROM Game WHERE gameID=?";
    private final String GET_ROUNDS_BY_GAMEID =
            "SELECT roundID, guess, guessTime, result, gameID FROM Round WHERE gameID=? ORDER BY guessTime";
    private final String UPDATE_GAME_STATUS = 
            "UPDATE Game SET status=? WHERE gameID=?";
    private final String GET_INSERT_ID = 
            "SELECT LAST_INSERT_ID()";
    private final String DELETE_ROUNDS = 
            "DELETE FROM Round WHERE gameID=?";
    private final String DELETE_GAME = 
            "DELETE FROM Game Where gameID=? ";
    
    
    
    @Override
    @Transactional
    public Game addGame(Game game) {
       GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                ADD_GAME, 
                Statement.RETURN_GENERATED_KEYS);

            
            statement.setString(1, game.getAnswer());
            statement.setString(2, game.getStatus());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    } 
    

    @Override
    public Round addRound(Round round) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                ADD_ROUND, 
                Statement.RETURN_GENERATED_KEYS);

            
            statement.setString(1, round.getGuess());
            statement.setTimestamp(2, Timestamp.valueOf(round.getTime()));
            statement.setString(3, round.getResult());
            statement.setInt(4, round.getGameId());
            return statement;

        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Game> getAllGames() {
       List<Game>games = jdbcTemplate.query(GET_GAMES, new GameMapper());
        return games;
    }

    @Override
    public Game getGame(int gameId) {
       Game game = jdbcTemplate.queryForObject(GET_GAME_BY_ID, new GameMapper(), gameId); 
        return game; 
    }

    @Override
    public List<Round> getRounds(int gameId) {
        List<Round> rounds = jdbcTemplate.query(GET_ROUNDS_BY_GAMEID, new RoundMapper(), gameId); 
        return rounds;
    }

    @Override
    public boolean updateStatus(Game game) {
        return jdbcTemplate.update(UPDATE_GAME_STATUS, game.getStatus(),game.getGameId()) > 0;
    }

    @Override
    public boolean deleteGame(int gameId) {
        jdbcTemplate.update(DELETE_ROUNDS,gameId);
        return jdbcTemplate.update(DELETE_GAME, gameId)>0;
    }
    
    private static final class GameMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameID"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getString("status"));
            return game;
        }
    }
    private static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundID"));
            round.setGuess(rs.getString("guess"));
            round.setTime(rs.getTimestamp("guessTime").toLocalDateTime());
            round.setResult(rs.getString("result"));
            round.setGameId(rs.getInt("gameID"));
            return round;
        }
    }
}
