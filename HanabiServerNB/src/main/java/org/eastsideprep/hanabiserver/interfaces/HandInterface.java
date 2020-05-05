/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver.interfaces;

/**
 *
 * @author etardif
 */
public interface HandInterface {
    Arraylist<CardInterface> cards= new ArrayList<CardInterface> ();
    
        
    HandInterface(){
         
    }
    
    clue(){
    
    }
    
    CardInterface discard(int position){ //from 0 to 1
        
    }   
    
    
    draw(CardInterFace Card){
        
    }
    
    
    
    
    }
}
