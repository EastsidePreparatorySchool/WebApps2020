/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.hanabiserver;

import java.util.ArrayList;
import java.util.HashMap;

import org.eastsideprep.hanabiserver.interfaces.GameInterface;
import java.util.concurrent.atomic.AtomicInteger;
import org.eastsideprep.hanabiserver.interfaces.CardInterface;
import org.eastsideprep.hanabiserver.interfaces.GameControlInterface;

/**
 *
 * @author eoreizy
 */
public class GameData implements GameInterface {

    public int debugNum;
    
    private String name;

    private ArrayList<Player> players;

    private int remainingStrikes;
    
    public String[] cardColors = new String[]{"Purple", "Green", "Yellow", "Blue", "Red"};
    public int cardNumbers = 5;
    public int cardDuplicates = 2;

//    private static int GameIdSettingValue=1; 
    private static AtomicInteger GameIdSettingValue2 = new AtomicInteger(Integer.MIN_VALUE);

    private int gameId;

    //   private ArrayList<Card> deck; // Can be an instance of the Deck class
    private Deck deck; // Can be an instance of the Deck class

    public GameData(ArrayList<Player> players, int startingStrikes, int deckVolume, String name, int gameId) {
        this.players = players;
        this.remainingStrikes = startingStrikes;
        this.deck = new Deck(new ArrayList<Card>());
        this.name = name;
        this.gameId = gameId;
        //do the stuff to fill the deck//
        this.deck = this.createDeck(); // Shuffled & everything
    }
    private HashMap<String, PlayedCards> playedCardPiles;

    private Discard discardPile;

    GameData(String nm) { //whenever you call game, synchronize
        name = nm;
        gameId = GameIdSettingValue2.getAndDecrement();

        // gameId=GameIdSettingValue;
        // GameIdSettingValue+=1;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public Deck getDeck() {
        return deck;
    }

//    public ArrayList<Card> getDeck() {
//        return (ArrayList<Card>) deck.getCards();
//    }
    @Override
    public Player getPlayerAtId(int id) {
        return players.get(id);
    }

    @Override
    public Hand getPlayerHandAtId(int id) {
        return players.get(id).GetHand();
    }

    @Override
    public CardInterface getDeckCardAtId(int id) {
        return deck.getCards().get(id);
    }

    @Override
    public int getRemainingStrikes() {
        return remainingStrikes;
    }

    @Override
    public ArrayList<Hint> getAllSentHints() {
        ArrayList<Hint> hints = new ArrayList<Hint>();
        for (Player p : players) {
            hints.addAll(p.GetReceivedHints());
        }
        return hints;
    }

    public int getid() {
        return gameId;
    }

    public String getname() {
        return name;
    }

    @Override
    public PlayedCards getPlayedCardPile(String playedCardPileColor) {
        return playedCardPiles.get(playedCardPileColor);
    }

    @Override
    public Discard getDiscardPile() {
        return this.discardPile;
    }

    @Override
    public int getMaxCardsInHand() {
        if (players.size() <= 3) {
            return 5;
        } else if (players.size() <= 5) {
            return 4;
        } else {
            return 3;
        }
    }
    
    private Deck createDeck() {
        ArrayList<Card> tempDeck = new ArrayList<>();
        for (int cardNumber = 1; cardNumber <= cardNumbers; cardNumber++) {
            for (String cardColor : cardColors) {
                for (int i = 0; i < cardDuplicates; i++) {
                    tempDeck.add(new Card(cardColor, cardNumber));
                }
            }
        }
        Deck completedDeck = new Deck(tempDeck);
        GameControlInterface.shuffle(completedDeck);
        return completedDeck;
    }
    
    public void dealHands() {
        for (int i = 0; i < getMaxHandSize(); i++) {
            players.forEach((player) -> {
            player.AddCardToHand(deck.draw());
        });
        }
    }
    
    private int getMaxHandSize() {
        if (players.size() <= 3) {
            return 5;
        } else if (players.size() <= 5) {
            return 4;
        } else {
            return 3;
        }
    }
            

    GameData(ArrayList<Player> players, Deck deck, HashMap<String, PlayedCards> playedCards, Discard discard) {
        this.players = players;
        this.deck = deck;
        this.discardPile = discard;
        this.playedCardPiles = playedCards;

        this.remainingStrikes = 3;

        gameId = GameIdSettingValue2.getAndDecrement();
    }

    GameData(ArrayList<Player> players, Deck deck, HashMap<String, PlayedCards> playedCards, Discard discard, String name) {
        this.players = players;
        this.deck = deck;
        this.discardPile = discard;
        this.playedCardPiles = playedCards;

        this.remainingStrikes = 3;
        this.name = name;
        gameId = GameIdSettingValue2.getAndDecrement();
    }
}
