package com.mitek.poker.evaluator;

import java.util.ArrayList;

import com.mitek.poker.evaluator.Game2.GameState;

public class GameHistory {

  int[] ranks = new int[10];
  Node temp = new Node();

  public GameHistory(int num) {

  }

  class Node {
    byte round;
    String winner;
    boolean tie;
  }

  /**
   * @param gameState   - round of the game
   * @param players     - array of players
   * @param board       - cards of the board
   * @param round_hands - hands of that round
   */
  public void saveGameState(byte gameState,
      ArrayList<Player> players,
      ArrayList<Card> board,
      ArrayList<Hand> round_hands) {

    collectTotals(round_hands);

    temp.round = gameState;
    temp.winner = "babajan";

    switch (gameState) {
      case GameState.FLOP:
        break;
      case GameState.TURN:
        break;
      case GameState.RIVER:
        FLogger.logln("\nRIVER");
        FLogger.logln("hands:");
        for (Hand h : round_hands)
          FLogger.logln(h.toString());
        FLogger.logln("\n\n");
        processRoundHands(gameState, round_hands);
        declareWinner();
        break;

    }

  }

  /**
   * 
   */
  private void declareWinner() {

  }

  /** total summary of hands for all games */
  public String getHandsSummary() {
    String res = "";

    for (int i = 0; i < ranks.length; ++i) {
      res += PokerEvaluator.hand_names[i] + ":\t" + ranks[i] + "\n";
    }
    return res;
  }

  /**
   * @param gameState
   * @param round_hands
   */
  private void processRoundHands(byte gameState, ArrayList<Hand> round_hands) {

    int min_rank = -1;
    int idx = 0;

    for (Hand h : round_hands) {

      if (min_rank < 0)
        min_rank = h.rank;

      if (gameState == GameState.RIVER) {
        if (idx > 0 && h.rank == min_rank)
          temp.tie = true;
      }

      idx++;
    }
  }

  public void printBoard(ArrayList<Card> board) {
    board.sort(null);
    ;

    FLogger.logln("----Board----------");
    for (Card c : board) {
      FLogger.log(c + "\t");
    }
    FLogger.logln("\n-------------------");
  }

  /** counts hands */
  private void collectTotals(ArrayList<Hand> round_hands) {
    for (Hand h : round_hands) {
      int x = PokerEvaluator.hand_rank(h.rank).ordinal();
      ++ranks[x];
    }
  }

}
