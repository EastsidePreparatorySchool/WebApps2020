/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import org.eastsideprep.hanabiserver.interfaces.HintInterface;

/**
 *
 * @author eoreizy
 */
public class Hint implements HintInterface {

    Boolean isColor;

    String playerFromId;
    String playerToId;

    String hintContent;

    public Hint(String _hintContent, String _playerFromId, String _playerToId) {
        hintContent = _hintContent;
        playerFromId = _playerFromId;
        playerToId = _playerToId;
        isColor = findIsColor(_hintContent);

    }

    @Override
    public String toString() {
        return hintContent;
    }

    @Override
    public Boolean getIsColor() {
        return isColor;
    }

    @Override
    public Boolean isNumber() {
        return !isColor;
    }

    @Override
    public String getPlayerFrom() {
        return playerFromId;
    }

    @Override
    public String getPlayerTo() {
        return playerToId;
    }

    private Boolean findIsColor(String _hintContent) {
        try {
            Integer.parseInt(_hintContent);
        } catch (Exception ex) {
            return true;
        }
        return false;
    }
}
