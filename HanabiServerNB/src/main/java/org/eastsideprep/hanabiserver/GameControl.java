/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.Collections;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.CardSpotInterface;
import org.eastsideprep.hanabiserver.interfaces.GameControlInterface;

/**
 *
 * @author eoreizy
 */
public class GameControl implements GameControlInterface {

    private GameData gameData;
    
    public GameData getGameData(){
        return gameData;
    }
    
    public GameControl(GameData gameData){
        this.gameData = gameData;
    }
    
}
