package com.Islomdan.savolvajavab;


import java.util.Arrays;
import java.util.Collections;

public class TestQuestion 
{
	private Question question;
	private Answer[] answer_options;
	private String correct_answer;
	
	public TestQuestion(Question q, Answer[] answers)
	{
		setQuestion(q);
		setAnswerOptions(answers);
		shuffle();
		for (int i = 0; i < 4; ++i)
			if (answers[i].getCorrect() == 1)
			{
				correct_answer = answers[i].getAnswer_text();
				break;
			}

	}
	
	public String getCorrectAnswer()
	{
		return correct_answer;
	}

	public String getQuestion()
	{
		return question.getText();
		//return question;
	}
	public void setQuestion(Question q)
	{
		question = q;
	}

	public String getAnswerOption(int index)
	{
		return answer_options[index].getAnswer_text();
	}
	
	public void setAnswerOptions(Answer[] options)
	{
		answer_options = options;
	}
	
	private void shuffle()
	{
		Collections.shuffle(Arrays.asList(answer_options));
	}
	
	public long getQuestionId()
	{
		return question.getId_qstn();
	}
}
