
package com.example.sasha.tlumach_dictionary;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, TextToSpeech.OnInitListener {
    private EditText voiceInput;
    private ImageButton btn_voice, btn_speak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;
    private ImageButton btn_seach;
    private TextView textView3;
    private EditText edit_word;
    private ImageButton btn_clear;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this);

        btn_seach = (ImageButton) findViewById(R.id.btn_seach);
        btn_seach.setOnClickListener(this);
        btn_clear = (ImageButton) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

        edit_word = (EditText) findViewById(R.id.edit_word);
        textView3 = (TextView) findViewById(R.id.textView3);

        voiceInput = (EditText) findViewById(R.id.edit_word);
        //speakButton = (ImageButton) findViewById(R.id.btn_voice);

        btn_voice = (ImageButton) findViewById(R.id.btn_voice);
        btn_voice.setOnClickListener(this);
        btn_speak = (ImageButton) findViewById(R.id.btn_speak);
        btn_speak.setOnClickListener(this);

        dbHelper = new DBHelper(this);

       /* btn_delete_ = (Button) findViewById(R.id.btn_delete);
        btn_delete_.setOnClickListener(this);

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);*/

       /* speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    public void onClick(View v) {
      String word = edit_word.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

     ContentValues contentValues = new ContentValues();

        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = null;
        switch (v.getId()) {

            case R.id.btn_voice:
                askSpeechInput();
                break;
            case R.id.btn_speak:
                speakOut();
                break;
            case R.id.btn_clear:
                textView3.setText(" ");
                edit_word.setText(" ");
                break;
            case R.id.btn_seach:
                Log.d("mLog", "--- Население больше --");
               /* selection = "word LIKE ?";
                selectionArgs = new String[] { word + '%' };*/
                selection = "word = ?";
                selectionArgs = new String[] { word };
                cursor = database.query(DBHelper.TABLE_TLUMACH, null, selection, selectionArgs, null, null,
                        null);

                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameColIndex = cursor.getColumnIndex(DBHelper.KEY_WORD);
                    int emailColIndex = cursor.getColumnIndex(DBHelper.KEY_ZNACHENNA);

                    textView3.setText(cursor.getString(emailColIndex));

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d("mLog",
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", word = " + cursor.getString(nameColIndex) +
                                        ", znachenna = " + cursor.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                cursor.close();
                break;
      /*      case R.id.btn_update:
                if (word.equalsIgnoreCase("")) {
                    break;
                }
                Log.d("mLog", "--- Update mytable: ---");
                // подготовим значения для обновления
                contentValues.put(DBHelper.KEY_WORD, word);
                contentValues.put(DBHelper.KEY_ZNACHENNA, znach);
                // обновляем по id
                int updCount = database.update(DBHelper.TABLE_TLUMACH, contentValues, DBHelper.KEY_WORD + "= ?",
                        new String[] { word });
                Log.d("mLog", "updated rows count = " + updCount);
                break;
            case R.id.btn_delete:
                if (word.equalsIgnoreCase("")) {
                    break;
                }
                Log.d("mLog", "--- Delete from mytable: ---");
                // удаляем по id
                int delCount = database.delete(DBHelper.TABLE_TLUMACH, DBHelper.KEY_WORD + "= ?",
                        new String[] { word });
                Log.d("mLog", "deleted rows count = " + delCount);
                break;
            case R.id.btn_clear:
                Log.d("mLog", "--- Clear from mytable: ---");
                database.delete(DBHelper.TABLE_TLUMACH, null, null);
                break;
            case R.id.btn_read:
                Log.d("mLog", "--- Read from mytable: ---");
                cursor = database.query(DBHelper.TABLE_TLUMACH, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameColIndex = cursor.getColumnIndex(DBHelper.KEY_WORD);
                    int emailColIndex = cursor.getColumnIndex(DBHelper.KEY_ZNACHENNA);

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d("mLog",
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", word = " + cursor.getString(nameColIndex) +
                                        ", znachenna = " + cursor.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                cursor.close();
                break;
            case R.id.btn_seach:
                Log.d("mLog", "--- Население больше --");
               *//* selection = "word LIKE ?";
                selectionArgs = new String[] { word + '%' };*//*
                selection = "word = ?";
                selectionArgs = new String[] { word };
                cursor = database.query(DBHelper.TABLE_TLUMACH, null, selection, selectionArgs, null, null,
                        null);

                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameColIndex = cursor.getColumnIndex(DBHelper.KEY_WORD);
                    int emailColIndex = cursor.getColumnIndex(DBHelper.KEY_ZNACHENNA);

                    text_znach.setText(cursor.getString(emailColIndex));

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d("mLog",
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", word = " + cursor.getString(nameColIndex) +
                                        ", znachenna = " + cursor.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                cursor.close();
                break;*/

        }
        dbHelper.close();
    }


    // Showing google speech input dialog
    //Start Введення голосом
    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceInput.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
// End


    //Start Озвучення голосом
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        Locale myLocale = new Locale("us");
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btn_speak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        CharSequence text = voiceInput.getText();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"id1");
    }

    //End

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.m_seach) {
            // Handle the camera action
          Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivities(new Intent[]{intent});
        } else if (id == R.id.m_add) {
            Intent intent = new Intent(MainActivity.this, Add.class);
            startActivities(new Intent[]{intent});

       } else if (id == R.id.m_help) {
            Intent intent = new Intent(MainActivity.this, Help.class);
            startActivities(new Intent[]{intent});
        } else if (id == R.id.m_about_aplication) {
            Intent intent = new Intent(MainActivity.this, About_aplication.class);
            startActivities(new Intent[]{intent});
        }
        else if (id == R.id.m_enter) {
            Intent intent = new Intent(MainActivity.this, Enter.class);
            startActivities(new Intent[]{intent});
        }
       /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
