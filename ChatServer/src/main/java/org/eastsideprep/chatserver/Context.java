/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.chatserver;

import java.util.ArrayList;

/**
 *
 * @author mgoetzmann
 */
public class Context {
    public String username;
    public int messagesSent = 0;
    public ArrayList<String> messages = new ArrayList<>();
}
