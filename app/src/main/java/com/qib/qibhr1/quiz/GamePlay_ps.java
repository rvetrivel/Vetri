/**
 * 
 */
package com.qib.qibhr1.quiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robert.hinds
 * 
 * This class represents the current game being played
 * tracks the score and player details
 *
 */
public class GamePlay_ps {

	private static String questionsize;
	private static final String TAG = questionsize ;
	private int numRounds;
	private int difficulty;
	private String playerName;
	private int right;
	private int wrong;
	private int round;
	
	private List<Question_ps> questions = new ArrayList<Question_ps>();

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @return the wrong
	 */
	public int getWrong() {
		return wrong;
	}
	/**
	 * @param wrong the wrong to set
	 */
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}
	/**
	 * @param round the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}
	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question_ps> questions) {
		this.questions = questions;
		Log.d(TAG, "setQuestions " + questions);
	}
	
	/**
	 * @param q the question to add
	 */
	public void addQuestions(Question_ps q) {
		this.questions.add(q);
	}
	
	/**
	 * @return the questions
	 */
	public List<Question_ps> getQuestions() {

		return questions;
	}
	
	
	public Question_ps getNextQuestion(){
		
		//get the question
		Question_ps next = questions.get(this.getRound());
		//update the round number to the next round
		this.setRound(this.getRound()+1);
		return next;
	}
	
	/**
	 * method to increment the number of correct answers this game
	 */
	public void incrementRightAnswers(){
		right ++;
	}
	
	/**
	 * method to increment the number of incorrect answers this game
	 */
	public void incrementWrongAnswers(){
		wrong ++;
	}
	/**
	 * @param numRounds the numRounds to set
	 */
	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}
	/**
	 * @return the numRounds
	 */
	public int getNumRounds() {
		return numRounds;
	}
	
	/**
	 * method that checks if the game is over
	 * @return boolean
	 */
	public boolean isGameOver(){
		return (getRound() >= getNumRounds());
	}


}
