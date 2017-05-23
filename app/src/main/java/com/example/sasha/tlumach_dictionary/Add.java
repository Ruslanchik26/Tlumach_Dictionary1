package com.example.sasha.tlumach_dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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
import android.widget.TextView;


public class Add extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    Button btn_add, btn_delete_, btn_clear, btn_update, btn_read, btn_seach;
    EditText edit_word, edit_znach, text_znach;
    TextView textV_znach;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

       btn_add = (Button) findViewById(R.id.btn_add);
       btn_add.setOnClickListener(this);

        btn_delete_ = (Button) findViewById(R.id.btn_delete);
        btn_delete_.setOnClickListener(this);



        edit_word = (EditText) findViewById(R.id.edit_word);
        edit_znach = (EditText) findViewById(R.id.edit_znach);
        //text_znach = (EditText) findViewById(R.id.text_znach);

        dbHelper = new DBHelper(this);


       /* View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){

                    case R.id.btn_add:
                        edit_word.setText("Натиснутий кнопка 1");
                       *//* intent = new Intent(MainActivity.this, Menu.class);
                        startActivities(new Intent[]{intent});*//*
                        break;
                    case R.id.btn_update:
                        edit_word.setText("Натиснутий кнопка 2");
                        *//*intent = new Intent(MainActivity.this, Speak.class);
                        startActivities(new Intent[]{intent});*//*
                        *//*intent = new Intent(MainActivity.this, Help.class);
                        startActivities(new Intent[]{intent});*//*
                        break;
                    case R.id.btn_delete:
                        edit_word.setText("Натиснутий кнопка 3");
                       *//* intent = new Intent(MainActivity.this, Result.class);
                        startActivities(new Intent[]{intent});*//*
                       *//* intent = new Intent(MainActivity.this, Help.class);
                        startActivities(new Intent[]{intent});*//*
                        break;
                    case R.id.btn_clear:
                        edit_word.setText("Натиснутий кнопка 4");
                        *//*intent = new Intent(MainActivity.this, Reading.class);
                        startActivities(new Intent[]{intent});*//*
                        break;

                }
            }
        };


        btn_add.setOnClickListener(onClickListener);
        btn_update.setOnClickListener(onClickListener);
        btn_delete_.setOnClickListener(onClickListener);
        btn_clear.setOnClickListener(onClickListener);
*/

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
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
            Intent intent = new Intent(Add.this, MainActivity.class);
            startActivities(new Intent[]{intent});
        } else if (id == R.id.m_add) {
            Intent intent = new Intent(Add.this, Add.class);
            startActivities(new Intent[]{intent});

        } else if (id == R.id.m_help) {
            Intent intent = new Intent(Add.this, Help.class);
            startActivities(new Intent[]{intent});
        } else if (id == R.id.m_about_aplication) {
            Intent intent = new Intent(Add.this, About_aplication.class);
            startActivities(new Intent[]{intent});
        }
        else if (id == R.id.m_enter) {
            Intent intent = new Intent(Add.this, Enter.class);
            startActivities(new Intent[]{intent});
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        String word = edit_word.getText().toString();
        String znach = edit_znach.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = null;
        switch (v.getId()){

            case R.id.btn_add:
                Log.d("mLog", "--- Insert in mytable: ---");
               // edit_word.setText("Натиснутий кнопка 1");
                contentValues.put(DBHelper.KEY_WORD, word);
                contentValues.put(DBHelper.KEY_ZNACHENNA, znach);

                database.insert(DBHelper.TABLE_TLUMACH, null, contentValues);
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


            case R.id.btn_seach:


        }


// закрываем подключение к БД
        dbHelper.close();

    }
}


class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ukrTlymSlov";
    public static final String TABLE_TLUMACH = "tlumach";

    public static final String KEY_ID = "_id";
    public static final String KEY_WORD = "word";
    public static final String KEY_ZNACHENNA = "znachenna";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// создаем таблицу с полями
        db.execSQL("create table " + TABLE_TLUMACH + "(" + KEY_ID + " integer primary key autoincrement," + KEY_WORD + " text,"
                + KEY_ZNACHENNA + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + TABLE_TLUMACH);

        onCreate(db);
    }


}
