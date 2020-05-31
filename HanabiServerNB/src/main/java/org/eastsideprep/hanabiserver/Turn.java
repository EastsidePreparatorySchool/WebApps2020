/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

/**
 *
 * @author mgoetzmann
 */
public class Turn {
    int gameId;
    
    boolean isDiscard;  
    boolean isPlay;
    
    boolean isHint;
    String playerTo;
    String hintType;
    String hint;
}
