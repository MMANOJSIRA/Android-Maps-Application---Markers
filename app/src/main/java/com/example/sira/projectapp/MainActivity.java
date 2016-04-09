package com.example.sira.projectapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends ActionBarActivity {

    String place2 = null;

    String[] hostelName = new String[4];
    String[] deptName = new String[5];
    String[] cantName = new String[7];
    String[] messName = new String[4];

    String[] hostelLat =new String[4];
    String[] deptLat = new String[5];
    String[] cantLat = new String[7];
    String[] messLat = new String[4];

    String[] hostelLong = new String[4];
    String[] deptLong = new String[5];
    String[] cantLong = new String[7];
    String[] messLong = new String[4];
    int check = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnhit = (Button) findViewById(R.id.sirabutton);

        btnhit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check =1;
                new JSONtask().execute("https://spider.nitt.edu/lateral/appdev");

            } //end of onClick
        });

    }




    public class JSONtask extends AsyncTask<String, String, String[]> {


        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect(); // after this you will connect directly to server

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                StringBuffer buffer = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                } // end of while loop

                // return buffer.toString(); // passes string of buffer to onPostExecute method
                String finalJSON = buffer.toString();
                // StringBuffer finalbufferedData = new StringBuffer();

                try {
                   if(check==1) {
                       JSONObject parentObject = new JSONObject(finalJSON);
                       JSONArray parentarray = parentObject.getJSONArray("categories");
                       String[] place = new String[4];
                       for (int i = 0; i < parentarray.length(); i++) {
                           place[i] = parentarray.getString(i);
                       }
                       return place; // passes string to onPost Execute
                   }

                    if(check==2)
                    {
                        JSONArray hostelarray = new JSONArray(finalJSON);

                        for(int i=0; i<hostelarray.length(); i++)
                        {
                            JSONObject hostelobj =  hostelarray.getJSONObject(i);
                            hostelName[i] = hostelobj.getString("name");
                            hostelLat[i] = hostelobj.getString("latitude");
                            hostelLong[i] = hostelobj.getString("longitude");

                            Log.d(" name is",hostelName[i]);
                            Log.d("lat is ", hostelLat[i]);
                        }
                        return hostelName;

                    }

                    if(check==3)
                    {
                        JSONArray hostelarray = new JSONArray(finalJSON);

                        for(int i=0; i<hostelarray.length(); i++)
                        {
                            JSONObject hostelobj =  hostelarray.getJSONObject(i);
                            deptName[i] = hostelobj.getString("name");
                            deptLat[i] = hostelobj.getString("latitude");
                            deptLong[i] = hostelobj.getString("longitude");

                            Log.d(" name is",deptName[i]);
                        }
                        return deptName;

                    }

                    if(check==4)
                    {
                        JSONArray hostelarray = new JSONArray(finalJSON);

                        for(int i=0; i<hostelarray.length(); i++)
                        {
                            JSONObject hostelobj =  hostelarray.getJSONObject(i);
                            cantName[i] = hostelobj.getString("name");
                            cantLat[i] = hostelobj.getString("latitude");
                            cantLong[i] = hostelobj.getString("longitude");

                            Log.d(" name is",cantName[i]);
                        }
                        return cantName;

                    }

                    if(check==5)
                    {
                            JSONArray hostelarray = new JSONArray(finalJSON);

                            for(int i=0; i<hostelarray.length(); i++)
                            {
                                JSONObject hostelobj =  hostelarray.getJSONObject(i);
                                messName[i] = hostelobj.getString("name");
                                messLat[i] = hostelobj.getString("latitude");
                                messLong[i] = hostelobj.getString("longitude");

                                Log.d(" name is",messName[i]);
                                Log.d("mess lat is",messLat[i]);
                            }
                            return messName;

                    }









                } //end of previous try
                catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null) reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;


        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
                Log.d("result value ", result[0]);
            Log.d("result[1]", result[1]);
            ListView placeList = (ListView)findViewById(R.id.siralistView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (getBaseContext(),android.R.layout.simple_list_item_1,result );
            placeList.setAdapter(adapter);



            placeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String value = (String) adapterView.getItemAtPosition(position);
                    Log.d("value of string is" , value); // values are perfect

                    if(value.equals("hostels"))

                    { //populate list view with new json
                        check =2;
                        new JSONtask().execute("https://spider.nitt.edu/lateral/appdev/coordinates?category=hostels");


                    }

                    else
                    if(value.equals("departments") )
                    { //populate list with department json
                        check=3;
                        new JSONtask().execute("https://spider.nitt.edu/lateral/appdev/coordinates?category=departments");

                        //departments
                    }

                   else if(value.equals("canteens"))
                    {    check=4;
                        new JSONtask().execute("https://spider.nitt.edu/lateral/appdev/coordinates?category=canteens");

                        // /canteens

                    }

                    else if(value.equals("messes"))
                    {
                        check=5;
                        new JSONtask().execute("https://spider.nitt.edu/lateral/appdev/coordinates?category=messes");

                        // messes

                    }

                   else {
                            int check2 =0;
                        for(int i=0; i<hostelName.length; i++)
                        if (value.equals(hostelName[i])) check2 = 1;
                        for(int i=0; i<messName.length; i++)
                        if(value.equals(messName[i])) check2 = 2;
                        for (int i=0; i<cantName.length ; i++) if(value.equals(cantName[i])) check2 =3;
                        for (int i=0; i<deptName.length ; i++) if(value.equals(deptName[i])) check2 =4;


if(check2 == 1)
{

    Log.d("value in intent", value);

    Intent intentH = new Intent(MainActivity.this, SiraMapsActivity.class);
    Log.d("IntentH created ", "");
    intentH.putExtra("finalname", hostelName);
    intentH.putExtra("finalLat", hostelLat);
    intentH.putExtra("finallong", hostelLong);
    intentH.putExtra("value", value);
    intentH.putExtra("check2",check2);
    Log.d("Maphostel gonna start", value);
    startActivity(intentH);
}
if(check2 == 2)
{
     Log.d("value in intent", value);
    Intent intentH = new Intent(MainActivity.this, SiraMapsActivity.class);
    Log.d("IntentH created ", "");
    intentH.putExtra("finalname", messName);
    intentH.putExtra("finalLat",messLat);
    intentH.putExtra("finallong", messLong);
    intentH.putExtra("value", value);
    Log.d("Map gonna start", value);
    startActivity(intentH);
}
if(check2 == 3)
{
    Log.d("value in intent", value);
    Intent intentH = new Intent(MainActivity.this, SiraMapsActivity.class);
    Log.d("IntentH created ", "");
    intentH.putExtra("finalname", cantName);
    intentH.putExtra("finalLat",cantLat);
    intentH.putExtra("finallong", cantLong);
    intentH.putExtra("value", value);
    Log.d("Map gonna start", value);
    startActivity(intentH);
}

                        if(check2 == 4)
                        {       Log.d("value in intent", value);
                            Intent intentH = new Intent(MainActivity.this, SiraMapsActivity.class);
                            Log.d("IntentH created ", "");
                            intentH.putExtra("finalname", deptName);
                            intentH.putExtra("finalLat",deptLat);
                            intentH.putExtra("finallong", deptLong);
                            intentH.putExtra("value", value);
                            Log.d("Map gonna start", value);
                            startActivity(intentH);

                           }



                    }// end of else
                        }
                     });


        }
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

} // end of Main Activity
