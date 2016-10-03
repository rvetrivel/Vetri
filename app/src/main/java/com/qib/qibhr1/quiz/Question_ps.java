package com.qib.qibhr1.quiz;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question_ps extends Application {
	
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;

	private int rating;
	private static ArrayList<Integer> result;
	private static Question_ps instance;
	/**
	 * @return the question
	 */

	public static ArrayList<Integer> getArrayList() {
		return result;
	}
	public void setImagevalue(ArrayList<Integer> result) {
		Question_ps.result = result;
	}
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer1() {
		return answer1;
	}
	/**
	 * @param answer1 the answer to set
	 */
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	/**
	 * @return the option1
	 */
	public String getAnswer2() {
		return answer2;
	}
	/**
	 * @param answer2 the option1 to set
	 */
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	/**
	 * @return the option2
	 */
	public String getAnswer3() {
		return answer3;
	}
	/**
	 * @param answer3 the option2 to set
	 */
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	/**
	 * @return the option3
	 */
	public String getAnswer4() {
		return answer4;
	}
	/**
	 * @param answer4 the option3 to set
	 */
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	public String getAnswer5() {
		return answer5;
	}
	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}
	public List<String> getQuestionOptions(){
		List<String> shuffle = new ArrayList<String>();
		shuffle.add(answer1);
		shuffle.add(answer2);
		shuffle.add(answer3);
		shuffle.add(answer4);
		shuffle.add(answer5);
		Collections.shuffle(shuffle);
		return shuffle;
	}

}
