package com.Islomdan.savolvajavab;


public class Question {
	
	private long id_qstn;
	private String text;
	private long difficulty; //means the number of the question
	private String topic;
	//private Answer[] answers = new Answer[4];

	public Question(){
	}
	
	public Question(String text, long difficulty, String topic){
		this.text = text;
		this.difficulty = difficulty;
		this.topic = topic;
	}
	
	public Question(long id, String text, long difficulty, String topic){
		this.id_qstn = id;
		this.text = text;
		this.difficulty = difficulty;
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public long getId_qstn() {
		return id_qstn;
	}

	public void setId_qstn(long id_qstn) {
		this.id_qstn = id_qstn;
	}
	
	public long get_difficulty() {
		return difficulty;
	}

	public void set_difficulty(long difficulty) {
		this.difficulty = difficulty;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
//	public Answer[] getAnswers() {
//		return answers;
//	}
//
//	public void setAnswers(Answer[] answers) {
//		this.answers = answers;
//	}
	
}
