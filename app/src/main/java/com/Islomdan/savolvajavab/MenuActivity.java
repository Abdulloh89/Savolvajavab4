package com.Islomdan.savolvajavab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.Islomdan.savolvajavab.fontpackage.MyButtonFont;
import com.Islomdan.savolvajavab.fontpackage.MyTextViewFont;


@SuppressLint("HandlerLeak")
public class MenuActivity extends Activity implements OnClickListener {
    SharedPreferences sharedpreferences;
    public boolean ENABLE_MUSIC = true;
    public boolean ENABLE_EFFECT = true;

    private SQLiteDatabase mDB = null;
    private DatabaseHelper mDbHelper;

    private static String[] topics = {"Lotin", "Kiril"};
    private static int currentTopicIndex = 0;
    private static final int numberOfTopics = 2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_topic);


        final MyTextViewFont chooseTopicTextView = (MyTextViewFont) findViewById(R.id.topicTextView);
        chooseTopicTextView.setText(topics[currentTopicIndex]);

        final MyButtonFont playButton = (MyButtonFont) findViewById(R.id.playThematicGameButton);
        playButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(MenuActivity.this,GameActivity.class);
                String topic = (String) chooseTopicTextView.getText();
                intent.putExtra("topic", topic);
                startActivity(intent);
            }
        });

        final Button rightButton = (Button) findViewById(R.id.rightArrow);
        rightButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentTopicIndex = (currentTopicIndex + 1) % numberOfTopics;
                chooseTopicTextView.setText(topics[currentTopicIndex]);
            }
        });

        final Button leftButton = (Button) findViewById(R.id.leftArrow);
        leftButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentTopicIndex = (currentTopicIndex - 1 + numberOfTopics) % numberOfTopics;
                chooseTopicTextView.setText(topics[currentTopicIndex]);
            }
        });


        //settings
        MyButtonFont settingsButton = (MyButtonFont) findViewById(R.id.menuSettingsBtn);
        settingsButton.setOnClickListener(this);

        MyButtonFont exitButton = (MyButtonFont) findViewById(R.id.menuExitBtn);
        exitButton.setOnClickListener(this);


        setPreferences();
        mDbHelper = DatabaseHelper.getInstance(this);
        mDbHelper.setDataBase(mDB = mDbHelper.getWritableDatabase());
    }


    public void clearAll(){
    	mDB.delete(DatabaseHelper.TABLE_ANSWER, null, null);
    	Log.d("TABLE", "ANSWER DELETED");
    	mDB.delete(DatabaseHelper.TABLE_QUESTION, null, null);
    	Log.d("TABLE", "QUESTION DELETED");
    }

    /** Button handling */
    public void onClick(View v) {
                switch (v.getId()) {


                        //Settings
                        case R.id.menuSettingsBtn: {
                            Intent set = new Intent();
                            set.setClass(MenuActivity.this, SettingsActivity.class);
                        	//Intent set = new Intent(MenuActivity.this, SettingsActivity.class);
                            startActivity(set);
                        } break;

                         //exit
                        case R.id.menuExitBtn: {
                             finish();
                        }break;

                        default:
                            break;
                }
        }

    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 5000; //time of view splash picture 5 sec
    private ImageView splash;

    private Handler splashHandler = new Handler() {
             public void handleMessage(Message msg) {
                 switch (msg.what) {
                 case STOPSPLASH:
                     //get out Splash picture
                     splash.setVisibility(View.GONE);
                     break;
                 }
                 super.handleMessage(msg);
             }
          };

    private void setPreferences(){
    	// sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    	this.ENABLE_EFFECT = sharedPrefs.getBoolean("prefEnableEffect", true);
    	this.ENABLE_MUSIC = sharedPrefs.getBoolean("prefEnableMusic", true);
    }
}
