/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.service;

import zls.mastermind.controller.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author zshug
 */
public class NoRoundsFoundException extends Exception{
    public NoRoundsFoundException(String message){
       Error err = new Error();
       err.setMessage(message);
       new ResponseEntity(err, HttpStatus.NOT_FOUND);
    }
}
