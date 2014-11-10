package com.reubenk.gmessengertest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void readMessage(View v)
    {
        DownloadTask dt = new DownloadTask();

        dt.execute();

    }

    public class DownloadTask extends AsyncTask<Void, Void, Void> {

        String text;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getApplicationContext(), "Updating...", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                ServiceHandler sh = new ServiceHandler();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall("http://gconnect.azurewebsites.net/api/test1/GetMessage/1,1", ServiceHandler.GET);

                String jsonStr2 = "{\"message\": " + jsonStr + "} ";

                JSONObject jsonObj = new JSONObject(jsonStr2);


                text = jsonObj.getString("message");

                // looping through All Contacts


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), "message " + text, Toast.LENGTH_SHORT).show();

            TextView tv = (TextView)findViewById(R.id.textViewMessage);
            tv.setText(text);

        }
    }
}
