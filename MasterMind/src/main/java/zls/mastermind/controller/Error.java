/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zls.mastermind.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author zshug
 */
public class Error {

    private LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
