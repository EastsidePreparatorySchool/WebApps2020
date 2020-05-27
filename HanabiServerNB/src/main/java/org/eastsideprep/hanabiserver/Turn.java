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

    int debug;

    // HINT & PLAY & DISCARD
    String turnType;

    // HINT & PLAY
    String color;

    // HINT
    String playerTo;
    String hintType;
    Object hint; // either int or String

}
