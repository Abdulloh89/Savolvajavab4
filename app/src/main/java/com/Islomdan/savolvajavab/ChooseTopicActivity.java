package com.Islomdan.savolvajavab;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Islomdan.savolvajavab.fontpackage.MyButtonFont;
import com.Islomdan.savolvajavab.fontpackage.MyTextViewFont;


public class ChooseTopicActivity extends Activity
{

	private static String[] topics = {"Kiril", "Lotin"};
	private static int currentTopicIndex = 0;
	private static final int numberOfTopics = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_topic);
		
		final MyTextViewFont chooseTopicTextView = (MyTextViewFont) findViewById(R.id.topicTextView);
		chooseTopicTextView.setText(topics[currentTopicIndex]);		
		
		final MyButtonFont playButton = (MyButtonFont) findViewById(R.id.playThematicGameButton);
		playButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(ChooseTopicActivity.this,GameActivity.class);
				String topic = (String) chooseTopicTextView.getText();
				intent.putExtra("topic", topic);
				startActivity(intent);
			}
		});
		
		final Button rightButton = (Button) findViewById(R.id.rightArrow);
		rightButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTopicIndex = (currentTopicIndex + 1) % numberOfTopics;
				chooseTopicTextView.setText(topics[currentTopicIndex]);
			}
		});
		
		final Button leftButton = (Button) findViewById(R.id.leftArrow);
		leftButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTopicIndex = (currentTopicIndex - 1 + numberOfTopics) % numberOfTopics;
				chooseTopicTextView.setText(topics[currentTopicIndex]);
			}
		});
		

	}
	
}
