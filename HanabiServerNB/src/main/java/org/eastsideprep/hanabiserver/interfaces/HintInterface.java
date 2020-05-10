/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

/**
 *
 * @author kyang
 */
public interface HintInterface {
    
    String toString();
    
    String toInt();
    
    Boolean isColor();
    
    Boolean isNumber();
    
    int getPlayerFrom();
    
    int getPlayerTo();
    
}
