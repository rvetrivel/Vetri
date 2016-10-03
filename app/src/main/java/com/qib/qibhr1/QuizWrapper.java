package com.qib.qibhr1;

import org.json.JSONArray;

public class QuizWrapper {

    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String answer;
    private String option1id;
    private String option2id;
    private String option3id;
    private String option4id;
    private String option5id;
    private int questiontypeid;
    private int optioncount;
    private int timeline;
    private String questionurl;
    private String questioncontent;
    private JSONArray matchingans;
    private JSONArray optionsformatching;

    public QuizWrapper(int id, String question, String questionurl, int questiontypeid, String questioncontent, int optioncount, JSONArray optionsformatching, String option1, String option1id, String option2, String option2id, String option3, String option3id, String option4, String option4id, String option5, String option5id, String answer, JSONArray matchingans, int timeline) {
        this.id = id;
        this.question = question;
        this.questioncontent = questioncontent;
        this.questionurl = questionurl;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.option1id = option1id;
        this.option2id = option2id;
        this.option3id = option3id;
        this.option4id = option4id;
        this.option5id = option5id;
        this.answer = answer;
        this.matchingans = matchingans;
        this.questiontypeid = questiontypeid;
        this.optioncount = optioncount;
        this.optionsformatching = optionsformatching;
        this.timeline = timeline;
    }

    public int gettimeline() {
        return timeline;
    }

    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestioncontent() {
        return questioncontent;
    }

    public void setQuestioncontent(String questioncontent) {
        this.questioncontent = questioncontent;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionurl() {
        return questionurl;
    }

    public void setQuestionurl(String questionurl) {
        this.questionurl = questionurl;
    }

    public int getQuestiontypeid() {
        return questiontypeid;
    }

    public void setQuestion(int questiontypeid) {
        this.questiontypeid = questiontypeid;
    }

    public int getOptioncount() {
        return optioncount;
    }

    public void setOptioncount(int optioncount) {
        this.optioncount = optioncount;
    }

    public String getAnswers1() {
        return option1;
    }

    public void setAnswers1(String option1) {
        this.option1 = option1;
    }

    public String getAnswers2() {
        return option2;
    }

    public void setAnswers2(String option2) {
        this.option2 = option2;
    }

    public String getAnswers3() {
        return option3;
    }

    public void setAnswers3(String option3) {
        this.option3 = option3;
    }

    public String getAnswers4() {
        return option4;
    }

    public void setAnswers4(String option4) {
        this.option4 = option4;
    }

    public String getAnswers5() {
        return option5;
    }

    public void setAnswers5(String option5) {
        this.option5 = option5;
    }

    public String getOption1id() {
        return option1id;
    }

    public void setOption1id(String option1id) {
        this.option1id = option1id;
    }

    public String getOption2id() {
        return option2id;
    }

    public void setOption2id(String option2id) {
        this.option2id = option2id;
    }

    public String getOption3id() {
        return option3id;
    }

    public void setOption3id(String option3id) {
        this.option3id = option3id;
    }

    public String getOption4id() {
        return option4id;
    }

    public void setOption4id(String option5id) {
        this.option4id = option4id;
    }

    public String getOption5id() {
        return option5id;
    }

    public void setOption5id(String option5id) {
        this.option5id = option5id;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public JSONArray getMatchingans() {
        return matchingans;
    }

    public void setMatchingans(JSONArray optionsformatching) {
        this.matchingans = matchingans;
    }

    public JSONArray getOptionsformatching() {
        return optionsformatching;
    }

    public void setOptionsformatching(JSONArray optionsformatching) {
        this.optionsformatching = optionsformatching;
    }


}
