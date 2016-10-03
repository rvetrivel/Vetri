package com.qib.qibhr1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Quizinstructions extends Activity {
    private ProgressDialog pDialog;
    private Button nextButton;
    ListView list;
    private CheckBox read;
    private String lang, en;
    private int value = 0;
    // URL to get contacts JSON
    //private static String url = "http://10.20.30.137:1030//gaapi/ApiAssementInstruction?Type=AS&lang="+lang;
    // JSON Node names
    private static final String TAG_CONTACTS = "QuizInstructions";
    private static final String TAG_KEY1 = "key1";
    private static final String TAG_KEY2 = "key2";
    private static final String TAG_KEY3 = "key3";
    private static final String TAG_KEY4 = "key4";
    private static final String TAG_KEY5 = "key5";
    private static final String TAG_KEY6 = "key6";
    ArrayList<String> key =new ArrayList<>();
    private static final String tag = "SignUp Instruction";

    private TextView readandunderstood;

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_instructions);
        list = (ListView) findViewById(R.id.listcat);
        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        Log.d(tag, "Lang " + lang);
        if (lang == null) {
            lang = "en";
        }
        contactList = new ArrayList<HashMap<String, String>>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        /*ActionBar bar = getActionBar();
        // for color
        bar.setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.setting));
        getActionBar().setTitle("   Instructions");
        getActionBar().setIcon(R.drawable.transparent);
        getActionBar().setDisplayShowTitleEnabled(true);*/


        if (isNetworkAvailable()) {

            //handler.post(timedTask);
            new GetContacts().execute();
            new LogoutTask(Quizinstructions.this).execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Quizinstructions.this);
            // set dialog message
            alertDialogBuilder
                    .setMessage(Quizinstructions.this.getString(R.string.network))
                    .setCancelable(false)
                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            nextButton.setVisibility(View.GONE);
                            readandunderstood.setVisibility(View.GONE);
                            read.setVisibility(View.GONE);
                            dialog.cancel();
                            Intent i = new Intent(Quizinstructions.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");
        nextButton = (Button) findViewById(R.id.start);
        nextButton.setTypeface(myTypeface);
        read = (CheckBox) findViewById(R.id.read);
        readandunderstood = (TextView) findViewById(R.id.readandunderstood);
        nextButton.setVisibility(View.VISIBLE);
        read.setVisibility(View.VISIBLE);
        readandunderstood.setVisibility(View.VISIBLE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read.isChecked()) {
                    value = 1;
                }
                Log.d("Onclick start button ", "> " + value);
                if (value == 1) {
                    Intent i = new Intent(Quizinstructions.this, QuizActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Quizinstructions.this,
                            R.string.notchecked,
                            Toast.LENGTH_SHORT).show();
                }
                /*Intent i = new Intent(Quizinstructions.this, QuizActivity.class);
                startActivity(i);*/
            }

        });

        /*Button back=(Button)findViewById(R.id.back) ;
        back.setTypeface(myTypeface);

        back.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Quizinstructions.this, MainActivity.class);
                startActivity(i);
            }

        });*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) Quizinstructions.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Quizinstructions.this);
            pDialog.setMessage(Quizinstructions.this.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN+"gaapi/ApiAssementInstruction?Type=AS&lang=" + lang;
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int count = 0;
                        int keylenth = contacts.length();
                        for (int l = 1; l <= keylenth++; l++) {
                            String value = c.getString("key" + l);
                            if(value.equals("")||value.equals("null"))
                            {
                                System.out.println("value "+value);
                            }
                            else {
                                count++;
                                System.out.println("value " + value);
                                key.add(count + "." + c.getString("key" + l));
                                System.out.println("key array" + key);
                                ArrayAdapter<String> arrayAdapter =
                                        new ArrayAdapter<String>(Quizinstructions.this,R.layout.instruction_listsdy, key);
                                // Set The Adapter
                                list.setAdapter(arrayAdapter);
                            }
                        }

                        /*String key1 = c.getString(TAG_KEY1);
                        String key2 = c.getString(TAG_KEY2);
                        String key3 = c.getString(TAG_KEY3);
                        String key4 = c.getString(TAG_KEY4);
                        String key5 = c.getString(TAG_KEY5);
                        String key6 = c.getString(TAG_KEY6);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        contact.put(TAG_KEY1, key1);
                        contact.put(TAG_KEY2, key2);
                        contact.put(TAG_KEY3, key3);
                        contact.put(TAG_KEY4, key4);
                        contact.put(TAG_KEY5, key5);
                        contact.put(TAG_KEY6, key6);

                        // adding contact to contact list
                        contactList.add(contact);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* ListAdapter adapter = new SimpleAdapter(
                        Quizinstructions.this, contactList,
                        R.layout.quiz_instructions_list, new String[]{
                        TAG_KEY1, TAG_KEY2, TAG_KEY3, TAG_KEY4, TAG_KEY5, TAG_KEY6
                },
                        new int[]{R.id.key1, R.id.key2, R.id.key3, R.id.key4, R.id.key5, R.id.key6
                        });

                setListAdapter(adapter);*/
            } else {
                nextButton.setVisibility(View.GONE);
                read.setVisibility(View.GONE);
                readandunderstood.setVisibility(View.GONE);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Quizinstructions.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(Quizinstructions.this.getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                nextButton.setVisibility(View.GONE);
                                readandunderstood.setVisibility(View.GONE);
                                read.setVisibility(View.GONE);
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                System.exit(0);
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }

        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}