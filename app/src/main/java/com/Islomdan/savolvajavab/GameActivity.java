package com.Islomdan.savolvajavab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;

import com.Islomdan.savolvajavab.fontpackage.MyButtonFont;
import com.Islomdan.savolvajavab.fontpackage.MyTextViewFont;


public class GameActivity extends Activity 
{
	private static GameManager mGameManager;
	private static AudioPlayer mAudioPlayer;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initAudioPlayer();

        String topic = null;
        boolean blitz = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
        	topic = bundle.getString("topic");
        	blitz = bundle.getBoolean("Blitz");
        }
        
        initGameManager(blitz);
        initButtons();
        
        mGameManager.startGame(topic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
    
    private void initAudioPlayer()
    {
        mAudioPlayer = AudioPlayer.getInstance();
        mAudioPlayer.setContext(getApplicationContext());    
        mAudioPlayer.setMusicEnabled(getMusicPref());
        mAudioPlayer.setEffectsEnabled(getEffectPref());
        mAudioPlayer.setTheme(AudioPlayer.Theme.Classic);
    }
    
    private boolean getMusicPref(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        boolean prefMusic = sharedPrefs.getBoolean("prefEnableMusic", true);
        return prefMusic;
    }
    
    private boolean getEffectPref(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        boolean prefEffect = sharedPrefs.getBoolean("prefEnableEffect", true);
        return prefEffect;
    }

    private void initGameManager(boolean blitz)
    {
        mGameManager = GameManager.getInstance(blitz);
    	mGameManager.setActivity(this);
    	mGameManager.initViewReferences();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
	protected void onPause() {
		super.onPause();
	}


	@Override
    protected void onStop() {
		mAudioPlayer.stopPlaying();
		mGameManager.stop();
        super.onStop();
    }

    @Override
	protected void onDestroy() {
    	mAudioPlayer.stopPlaying();
		super.onDestroy();
	}




	private void initButtons()
	{
        final MyButtonFont fiftyFiftyButton = (MyButtonFont) findViewById(R.id.fiftyFiftyButton);
        fiftyFiftyButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                if (mGameManager.useFiftyFifty())
                {
                	fiftyFiftyButton.setEnabled(false);//setClickable(false);
                	fiftyFiftyButton.setBackgroundResource(R.drawable.button_hint_used);
            	}
            }
        });
        
        final MyButtonFont changeQuestionButton = (MyButtonFont) findViewById(R.id.changeQuestionButton);
        changeQuestionButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useChangeQuestion())
				{
					changeQuestionButton.setEnabled(false);//setClickable(false);
					changeQuestionButton.setBackgroundResource(R.drawable.button_hint_used);
				}
			}
		});
        
        final MyButtonFont askForAudienceButton = (MyButtonFont) findViewById(R.id.askForAudienceButton);
        askForAudienceButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (mGameManager.useAskForAudience())
				{
					askForAudienceButton.setEnabled(false);
					askForAudienceButton.setBackgroundResource(R.drawable.button_hint_used);
				}
			}
		});
        

        int[] ids = {R.id.variant_a, R.id.variant_b, R.id.variant_c, R.id.variant_d};


        for (int i = 0; i < 4; i++)

        {
        	MyTextViewFont answerView = (MyTextViewFont) findViewById(ids[i]);
        	final int id = i;

        	answerView.setOnClickListener(new View.OnClickListener() 
        	{		
				@Override
				public void onClick(View v) {
					mGameManager.chooseAnswer(id);					
				}
			});
        }
        
        int[] percIds = {R.id.percentageTextViewA, R.id.percentageTextViewB, 
        		R.id.percentageTextViewC,R.id.percentageTextViewD};
        for (int i = 0; i < 4; ++i)
        	findViewById(percIds[i]).setVisibility(View.INVISIBLE);   
    }
	
	protected Dialog onCreateDialog(String message) 
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle(R.string.end);
        adb.setMessage(message);
        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
				Intent menu = new Intent();
                menu.setClass(GameActivity.this, MenuActivity.class);
                menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(menu);
            }
        });
        return adb.create();
    }
	
    public void showWinDialog() {
		// create alert dialog
        String message = getResources().getString(R.string.epicwin);
		Dialog dialog = onCreateDialog(message);
		// show it
		dialog.show();
      }
    
    public void showCondDialog(int sum, int questionNumber){
    	// create alert dialog
    	String message = null;
    	if (questionNumber == 1) {
    		message = getResources().getString(R.string.epicfail);
        } else if (isBetween(questionNumber, 1, 1)) {
            message = getResources().getString(R.string.lost1to1);
        } else if (isBetween(questionNumber, 2, 2)) {
    		message = getResources().getString(R.string.lost2to2);
        } else if (isBetween(questionNumber, 3, 3)) {
            message = getResources().getString(R.string.lost3to3);
        } else if (isBetween(questionNumber, 4, 4)) {
            message = getResources().getString(R.string.lost4to4);
        } else if (isBetween(questionNumber, 5, 5)) {
            message = getResources().getString(R.string.lost5to5);
        } else if (isBetween(questionNumber, 6, 6)) {
            message = getResources().getString(R.string.lost6to6);
        } else if (isBetween(questionNumber, 7, 7)) {
            message = getResources().getString(R.string.lost7to7);
        } else if (isBetween(questionNumber, 8, 8)) {
            message = getResources().getString(R.string.lost8to8);
        } else if (isBetween(questionNumber, 9, 9)) {
            message = getResources().getString(R.string.lost9to9);
        } else if (isBetween(questionNumber, 10, 10)) {
            message = getResources().getString(R.string.lost10to10);
        } else if (isBetween(questionNumber, 11, 11)) {
            message = getResources().getString(R.string.lost11to11);
        } else if (isBetween(questionNumber, 12, 12)) {
            message = getResources().getString(R.string.lost12to12);
        } else if (isBetween(questionNumber, 13, 13)) {
    		message = getResources().getString(R.string.lost13to13);
        } else if (isBetween(questionNumber, 14, 15)) {
            message = getResources().getString(R.string.lost14to14);
        } else if (isBetween(questionNumber, 15, 15)) {
    		message = getResources().getString(R.string.lost15to15);
    	}
		//String message = String.format(getResources().getString(R.string.condolence), sum);
    	
		Dialog dialog = onCreateDialog(message);
		// show it
		dialog.show();
    }
    
    private static boolean isBetween(int x, int lower, int upper) {
    	return lower <= x && x <= upper;
    }
    
//    public void showTakeMoneyDialog(){
//    	Dialog Dialog = onCreateDialog(3);
//    	Dialog.show();
//    }
      

    public void onBackPressed()
    {
    	super.onBackPressed();
        mGameManager.onStopGame();
    }
    
}
