package com.qib.qibhr1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;


public class Fragment2Instructiondy extends Fragment {
    private ProgressDialog pDialog;
    private String lang;
    int j;
    TextView Noactivity;

    JSONArray contacts = null;
    String[] array;
    private static final String TAG_CONTACTS1 = "QuizInstructions";
    private static final String tag = "Instruction Response";
    private static final String TAG_KEY = "key";
    private static final String TAG_KEY1 = "key1";
    private static final String TAG_KEY2 = "key2";
    private static final String TAG_KEY3 = "key3";
    private static final String TAG_KEY4 = "key4";
    private static final String TAG_KEY5 = "key5";
    private static final String TAG_KEY6 = "key6";
    private static final String TAG_KEY7 = "key7";
    private static final String TAG_KEY8 = "key8";
    private static final String TAG_KEY9 = "key9";
    ListView list;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<String> key =new ArrayList<>();

    public Fragment2Instructiondy() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.instructions, container,
                false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        Noactivity=(TextView)rootView.findViewById(R.id.noactivity);
        list = (ListView) rootView.findViewById(R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = gmtDateFormat.format(new Date());
        Log.d("date: ", "> " + date);
        contactList = new ArrayList<HashMap<String, String>>();
        SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        Log.d(tag, "Lang " + lang);
        if (lang.equals("null")) {
            lang = "en";
        }
        if (isNetworkAvailable()) {
            new GetContacts().execute();
            new LogoutTask(getActivity()).execute();
        }
        // Calling async task to get json

        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            // set dialog message
            alertDialogBuilder
                    .setMessage(getActivity().getString(R.string.network))
                    .setCancelable(false)
                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    })
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();
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

    private class GetContacts extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Fragment2Instructiondy.this.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            String jsonStr;

            String url = AppUrl.URL_COMMAN+"gaapi/ApiAssementInstruction?Type=GN&lang=" + lang;
            Log.d("instruc url Response: ", "> " + url);
            jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            // Dismiss the progress dialog
            /*ListAdapter adapter = new SimpleAdapter(
                    getActivity(), contactList,
                    R.layout.instructions_list, new String[]{
                    TAG_KEY1, TAG_KEY2, TAG_KEY3, TAG_KEY4, TAG_KEY5, TAG_KEY6, TAG_KEY7, TAG_KEY8, TAG_KEY9},
                    new int[]{R.id.key1, R.id.key2, R.id.key3, R.id.key4, R.id.key5, R.id.key6, R.id.key7, R.id.key8, R.id.key9
                    });

            list.setAdapter(adapter);*/
            if (result != null) try {
                JSONObject jsonObj = new JSONObject(result);

                // Getting JSON Array node
                contacts = jsonObj.getJSONArray(TAG_CONTACTS1);
                Log.d("TAG_CONTACTS: ", "> " + TAG_CONTACTS1);

                if (contacts.length() == 0) {
                    Noactivity.setVisibility(Noactivity.VISIBLE);
                } else {
                    Noactivity.setVisibility(Noactivity.GONE);

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
                                        new ArrayAdapter<String>(getActivity(),R.layout.instruction_listsdy, key);
                                // Set The Adapter
                                list.setAdapter(arrayAdapter);
                            }
                        }

                    }

                }
                // Create The Adapter with passing ArrayList as 3rd parameter

                /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        array );
                list.setAdapter(arrayAdapter);*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                // set dialog message
                alertDialogBuilder
                        .setMessage(getActivity().getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                getActivity().finish();
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

    public boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public void onBackPressed() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }
}
