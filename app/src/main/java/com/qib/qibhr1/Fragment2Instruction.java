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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;


public class Fragment2Instruction extends Fragment {
    private ProgressDialog pDialog;
    private String lang;
    TextView Noactivity;
    private String[] lv_arr = {};
    JSONArray contacts = null;

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
    public Fragment2Instruction() {
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
            pDialog.setMessage(Fragment2Instruction.this.getString(R.string.plz));
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
                int l;
                contacts = jsonObj.getJSONArray(TAG_CONTACTS1);
                Log.d("TAG_CONTACTS: ", "> " + TAG_CONTACTS1);

                if (contacts.length() == 0) {
                    Noactivity.setVisibility(Noactivity.VISIBLE);
                } else {
                    Noactivity.setVisibility(Noactivity.GONE);
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String key1 = c.getString(TAG_KEY1);
                        String key2 = c.getString(TAG_KEY2);
                        String key3 = c.getString(TAG_KEY3);
                        String key4 = c.getString(TAG_KEY4);
                        String key5 = c.getString(TAG_KEY5);
                        String key6 = c.getString(TAG_KEY6);
                        String key7 = c.getString(TAG_KEY7);
                        String key8 = c.getString(TAG_KEY8);
                        String key9 = c.getString(TAG_KEY9);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        contact.put(TAG_KEY1, key1);
                        contact.put(TAG_KEY2, key2);
                        contact.put(TAG_KEY3, key3);
                        contact.put(TAG_KEY4, key4);
                        contact.put(TAG_KEY5, key5);
                        contact.put(TAG_KEY6, key6);
                        contact.put(TAG_KEY7, key7);
                        contact.put(TAG_KEY8, key8);
                        contact.put(TAG_KEY9, key9);
                        // adding contact to instruction list
                        contactList.add(contact);
                    }
                }
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), contactList,
                        R.layout.instructions_list, new String[]{
                        TAG_KEY1, TAG_KEY2, TAG_KEY3, TAG_KEY4, TAG_KEY5, TAG_KEY6, TAG_KEY7, TAG_KEY8, TAG_KEY9},
                        new int[]{R.id.key1, R.id.key2, R.id.key3, R.id.key4, R.id.key5, R.id.key6, R.id.key7, R.id.key8, R.id.key9
                        });

                list.setAdapter(adapter);
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
