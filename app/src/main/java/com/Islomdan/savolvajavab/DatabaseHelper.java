package com.Islomdan.savolvajavab;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 3;
    private static String DB_NAME = "hu";


    public static final String TABLE_QUESTION = "Question";
    public static final String TABLE_ANSWER = "Answer";
    private final String fileName = "hu";
    private Context context;
    private static DatabaseHelper instance = null;
    private SQLiteDatabase myDataBase;


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, (DB_VERSION));
        this.context = context;
    }


    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    public void setDataBase(SQLiteDatabase dataBase) {
        this.myDataBase = dataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open(fileName);
            if (inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                sqLiteDatabase.beginTransaction();
                while ((line = br.readLine()) != null) {
                    Log.d("ASSETS ", line);
                    sqLiteDatabase.execSQL(line);
                }
                sqLiteDatabase.setTransactionSuccessful();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        onCreate(sqLiteDatabase);
    }


    public TestQuestion getQuestionWithAnswers(String topic, long quest_number, long curQuestId) {

        Cursor cursor = null;
        if (curQuestId == -1) {
            if (topic != null)
                cursor = myDataBase.rawQuery(
                        "SELECT * FROM Question WHERE difficulty=? AND topic=? ORDER BY RANDOM() LIMIT 1",
                        new String[]{String.valueOf(quest_number), topic});
            else
                cursor = myDataBase.rawQuery(
                        "SELECT * FROM Question WHERE difficulty=? ORDER BY RANDOM() LIMIT 1",
                        new String[]{String.valueOf(quest_number)});
            cursor.moveToFirst();
        } else {
            if (topic != null)
                cursor = myDataBase.rawQuery(
                        "SELECT * FROM Question WHERE difficulty=? AND topic=? ORDER BY RANDOM()",
                        new String[]{String.valueOf(quest_number), topic});
            else
                cursor = myDataBase.rawQuery(
                        "SELECT * FROM Question WHERE difficulty=? ORDER BY RANDOM()",
                        new String[]{String.valueOf(quest_number)});
            cursor.moveToFirst();
        }

        Question question = new Question(
                cursor.getLong(cursor.getColumnIndex("id_question")),
                cursor.getString(cursor.getColumnIndex("text")),
                quest_number,
                cursor.getString(cursor.getColumnIndex("topic"))
        );


        System.out.println(question.getText() + " ; " + question.get_difficulty());

        //this is the id of the question
        long id = cursor.getLong(cursor.getColumnIndex("id_question"));
        cursor.close();

        //let's select the answers for this question
        Cursor curs = myDataBase.rawQuery(
                "SELECT * FROM Answer WHERE id_question = ?;",
                new String[]{String.valueOf(id)}
        );

        // ЗДЕСЬ МЫ ВОЗВРАЩАЕМ НУЛЛ, ЕСЛИ КУРСОР НУЛЛ ИЛИ ПУСТОЙ
        if (curs == null || !curs.moveToFirst()) return null;

        Answer[] answers = new Answer[4];
        int i = 0;
        do {
            if (curs.getCount() != 4) {
                // ЗДЕСЬ МЫ ВОЗВРАЩАЕМ НУЛЛ, ЕСЛИ КОЛИЧЕСТВО ОТВЕТОВ НЕ РАВНО 4
                return null;
            }
            answers[i++] = new Answer(
                    curs.getLong(curs.getColumnIndex("id_answer")),
                    curs.getString(curs.getColumnIndex("answer_text")),
                    id,
                    curs.getLong(curs.getColumnIndex("correct"))
            );
        } while (curs.moveToNext());


        TestQuestion tQuestion = new TestQuestion(question, answers);
        return tQuestion;
    }


}
