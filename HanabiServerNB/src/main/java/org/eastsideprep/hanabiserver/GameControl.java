/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

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
    
    @Override
    public void shuffle(CardSpotInterface spot) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveCard(CardInterface card, CardSpotInterface spotFrom, CardSpotInterface spotTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public GameControl(GameData gameData){
        this.gameData = gameData;
    }
    
}
