package com.example.michael.agris_semestralka_clanky;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements HeadlinesFragment.OnFragmentInteractionListener{
    private static final String DEBUG_TAG = "DEBUG_TAG";

    private ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_CLANKY = "clanky";
    private static final String TAG_ID = "id_text";
    private static final String TAG_HEADLINE = "nazev";
    private static final String TAG_PEREX = "perex";

    // articles JSONArray
    JSONArray articles = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> headlineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        HeadlinesFragment headlinesFragment = HeadlinesFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,headlinesFragment,"TAG_HEADFRAG").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    @Override
    public void onFragmentInteraction(String id) {

    }
    public void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}

