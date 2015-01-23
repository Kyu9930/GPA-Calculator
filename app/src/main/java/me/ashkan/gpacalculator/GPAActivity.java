package me.ashkan.gpacalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class GpaActivity extends ActionBarActivity {
    private final String TAG = "GpaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Settings");
        setContentView(R.layout.activity_gpa);

        addData();
    }

    /**
     * Add the standard GPA data to the ListView
     */
    public void addData() {

        String[] data = {
                "90 - 100 |  A+  |  4.0",
                "85 - 89  |  A   |  4.0",
                "80 - 84  |  A-  |  3.7",
                "77 - 79  |  B+  |  3.3",
                "73 - 76  |  B   |  3.0",
                "70 - 72  |  B-  |  2.7",
                "67 - 69  |  C+  |  2.3",
                "63 - 66  |  C   |  2.0",
                "60 - 62  |  C-  |  1.7",
                "57 - 59  |  D+  |  1.3",
                "53 - 56  |  D   |  1.0",
                "50 - 52  |  D-  |  0.7",
                "0 - 49   |  F   |  0.0",
        };
        ListView mainListView = (ListView) findViewById(R.id.listView_gpa);
        ArrayList<String> gpaData = new ArrayList<String>(Arrays.asList(data));
        ArrayAdapter<String> mGPAList = new ArrayAdapter<String>(
                this, // The current context (this activity)
                R.layout.list_item_layout, // The name of the layout ID.
                R.id.list_item_layout_gpa, // The ID of the textview to populate.
                gpaData);
        mainListView.setAdapter(mGPAList);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpa, menu);
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
        if (id == R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}
