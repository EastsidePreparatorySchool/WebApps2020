/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

/**
 *
 * @author mgoetzmann
 */
public class Message {
    String msg;
    String username;
    
    Message (String s1, String s2) {
        msg = s1;
        username = s2;
    }
}
