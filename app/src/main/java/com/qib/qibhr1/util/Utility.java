/**
 * 
 */
package com.qib.qibhr1.util;

import com.qib.qibhr1.quiz.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rob
 *
 */
public class Utility {
	
	/**
	 * Method to capitalise the first letter of a given string
	 * 
	 * @param s
	 * @return
	 */
	public static String capitalise(String s){
		if (s==null || s.length()==0) return s;
		
		String s1 = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s1;
	}
	public static ArrayList<Integer> capitalise(ArrayList<Integer> i){
		if (i==null || i.size()==0) return i;


		return null ;
	}
	/**
	 * Method to get set of answers for a list of questions
	 * @param questions
	 * @return
	 */
	public static String getAnswers(List<Question> questions) {
		int question = 1;
		StringBuffer sb = new StringBuffer();
		
		for (Question q : questions){
			sb.append("Q").append(question).append(") ").append(q.getQuestion()).append("? \n");
			sb.append("Answer: ").append(q.getAnswer()).append("\n\n");
			question ++;
		}
		
		return sb.toString();
	}

	/*public static ArrayList<Integer> capitalise(ArrayList<Integer> i) {
		if (i==null || i.size()==0) return i;

		Integer i1 = i.subList(0, 1).get(i) + i.subList(1,1);
		return i1;

	}*/
}
