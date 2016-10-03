
package com.qib.qibhr1;

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
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Fragment1Category extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    ListView list;
    LinearLayout EN001, EN002, EN003, EN004, EN005, EN006;
    private JSONObject json;

    //JSON Node Names
    private static final String TAG_LIST = "schedulelst";
    private static final String TAG_NAME = "TopicName";
    private static final String TAG_CODE = "TopicCode";
    private static final String TAG_STATUS = "Status";
    private static final String TAG_START = "startDate";
    private static final String TAG_STIME = "startTime";
    private static final String TAG_ETIME = "endTime";
    private static final String TAG_DURATION = "Duration";
    private static final String TAG_ASSID = "AssementId";
    private static final String tag = "Instruction Response";
    JSONArray android = null;
    private String lang, CurentTime, date, userid, status, start, stime, etime;

    private ArrayList<String> assid = new ArrayList<>();
    private ArrayList<String> code = new ArrayList<>();
    private ArrayList<String> duration = new ArrayList<>();
    private TextView EN001_Tittle, EN001_date, EN001_duration, EN001_stime, EN001_etime, EN001_Start, EN001_End;
    private TextView EN002_Tittle, EN002_date, EN002_duration, EN002_stime, EN002_etime, EN002_Start, EN002_End;
    private TextView EN003_Tittle, EN003_date, EN003_duration, EN003_stime, EN003_etime, EN003_Start, EN003_End;
    private TextView EN004_Tittle, EN004_date, EN004_duration, EN004_stime, EN004_etime, EN004_Start, EN004_End;
    private TextView EN005_Tittle, EN005_date, EN005_duration, EN005_stime, EN005_etime, EN005_Start, EN005_End;
    private TextView EN006_Tittle, EN006_date, EN006_duration, EN006_stime, EN006_etime, EN006_Start, EN006_End;
    private ArrayList<String> name = new ArrayList<>();

    public Fragment1Category() {
    }

    private Typeface myTypeface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nasdaq_fragment, container,
                false);

        SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        userid = settings.getString("userid", null);
        Log.d(tag, "userid " + userid);
        if (lang == null) {
            lang = "en";
        }
        DateFormat utcFormat = new SimpleDateFormat("dd/MM/yyyy");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        date = utcFormat.format(new Date());

        Log.d("date: ", "> " + date);

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        CurentTime = df.format(now);
        System.out.println("Current Time" + CurentTime);
        Log.d("gmtTime: ", "> " + CurentTime);
        if (isNetworkAvailable()) {
            new JSONParse().execute();
            new LogoutTask(getActivity()).execute();
        } else {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Fragment1Category firstFragment = new Fragment1Category();
                getFragmentManager().beginTransaction()
                        .add(R.id.content_frame, firstFragment).commit();

                swipeContainer.setRefreshing(false);

            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorScheme(R.color.list_background,
                R.color.white,
                R.color.list_background,
                R.color.white);
        myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "dax_regular.ttf");
        list = (ListView) rootView.findViewById(R.id.list);
        EN001 = (LinearLayout) rootView.findViewById(R.id.EN001);
        EN002 = (LinearLayout) rootView.findViewById(R.id.EN002);
        EN003 = (LinearLayout) rootView.findViewById(R.id.EN003);
        EN004 = (LinearLayout) rootView.findViewById(R.id.EN004);
        EN005 = (LinearLayout) rootView.findViewById(R.id.EN005);
        EN006 = (LinearLayout) rootView.findViewById(R.id.EN006);
        EN001_Start = (TextView) rootView.findViewById(R.id.EN001_start);
        EN001_Start.setTypeface(myTypeface);
        EN001_End = (TextView) rootView.findViewById(R.id.EN001_end);
        EN001_End.setTypeface(myTypeface);
        EN001_Tittle = (TextView) rootView.findViewById(R.id.EN001_Tittle);
        EN001_Tittle.setTypeface(myTypeface);
        EN001_date = (TextView) rootView.findViewById(R.id.EN001_date);
        EN001_date.setTypeface(myTypeface);
        EN001_duration = (TextView) rootView.findViewById(R.id.EN001_duration);
        EN001_duration.setTypeface(myTypeface);
        EN001_stime = (TextView) rootView.findViewById(R.id.EN001_stime);
        EN001_stime.setTypeface(myTypeface);
        EN001_etime = (TextView) rootView.findViewById(R.id.EN001_etime);
        EN001_etime.setTypeface(myTypeface);

        EN002_Tittle = (TextView) rootView.findViewById(R.id.EN002_Tittle);
        EN002_Tittle.setTypeface(myTypeface);
        EN002_date = (TextView) rootView.findViewById(R.id.EN002_date);
        EN002_date.setTypeface(myTypeface);
        EN002_duration = (TextView) rootView.findViewById(R.id.EN002_duration);
        EN002_duration.setTypeface(myTypeface);
        EN002_Start = (TextView) rootView.findViewById(R.id.EN002_start);
        EN002_Start.setTypeface(myTypeface);
        EN002_End = (TextView) rootView.findViewById(R.id.EN002_end);
        EN002_End.setTypeface(myTypeface);
        EN002_stime = (TextView) rootView.findViewById(R.id.EN002_stime);
        EN002_stime.setTypeface(myTypeface);
        EN002_etime = (TextView) rootView.findViewById(R.id.EN002_etime);
        EN002_etime.setTypeface(myTypeface);

        EN003_Tittle = (TextView) rootView.findViewById(R.id.EN003_Tittle);
        EN003_Tittle.setTypeface(myTypeface);
        EN003_date = (TextView) rootView.findViewById(R.id.EN003_date);
        EN003_date.setTypeface(myTypeface);
        EN003_duration = (TextView) rootView.findViewById(R.id.EN003_duration);
        EN003_duration.setTypeface(myTypeface);
        EN003_stime = (TextView) rootView.findViewById(R.id.EN003_stime);
        EN003_stime.setTypeface(myTypeface);
        EN003_Start = (TextView) rootView.findViewById(R.id.EN003_start);
        EN003_Start.setTypeface(myTypeface);
        EN003_End = (TextView) rootView.findViewById(R.id.EN003_end);
        EN003_End.setTypeface(myTypeface);

        EN003_etime = (TextView) rootView.findViewById(R.id.EN003_etime);
        EN003_etime.setTypeface(myTypeface);

        EN004_Tittle = (TextView) rootView.findViewById(R.id.EN004_Tittle);
        EN004_Tittle.setTypeface(myTypeface);
        EN004_date = (TextView) rootView.findViewById(R.id.EN004_date);
        EN004_date.setTypeface(myTypeface);
        EN004_duration = (TextView) rootView.findViewById(R.id.EN004_duration);
        EN004_duration.setTypeface(myTypeface);
        EN004_Start = (TextView) rootView.findViewById(R.id.EN004_start);
        EN004_Start.setTypeface(myTypeface);
        EN004_End = (TextView) rootView.findViewById(R.id.EN004_end);
        EN004_End.setTypeface(myTypeface);

        EN004_stime = (TextView) rootView.findViewById(R.id.EN004_stime);
        EN004_stime.setTypeface(myTypeface);
        EN004_etime = (TextView) rootView.findViewById(R.id.EN004_etime);
        EN004_etime.setTypeface(myTypeface);

        EN005_Tittle = (TextView) rootView.findViewById(R.id.EN005_Tittle);
        EN005_Tittle.setTypeface(myTypeface);
        EN005_date = (TextView) rootView.findViewById(R.id.EN005_date);
        EN005_date.setTypeface(myTypeface);
        EN005_duration = (TextView) rootView.findViewById(R.id.EN005_duration);
        EN005_duration.setTypeface(myTypeface);
        EN005_Start = (TextView) rootView.findViewById(R.id.EN005_start);
        EN005_Start.setTypeface(myTypeface);
        EN005_End = (TextView) rootView.findViewById(R.id.EN005_end);
        EN005_End.setTypeface(myTypeface);
        EN005_stime = (TextView) rootView.findViewById(R.id.EN005_stime);
        EN005_stime.setTypeface(myTypeface);
        EN005_etime = (TextView) rootView.findViewById(R.id.EN005_etime);
        EN005_etime.setTypeface(myTypeface);

        EN006_Tittle = (TextView) rootView.findViewById(R.id.EN006_Tittle);
        EN006_Tittle.setTypeface(myTypeface);
        EN006_date = (TextView) rootView.findViewById(R.id.EN006_date);
        EN006_date.setTypeface(myTypeface);
        EN006_duration = (TextView) rootView.findViewById(R.id.EN006_duration);
        EN006_duration.setTypeface(myTypeface);
        EN006_Start = (TextView) rootView.findViewById(R.id.EN006_start);
        EN006_Start.setTypeface(myTypeface);
        EN006_End = (TextView) rootView.findViewById(R.id.EN006_end);
        EN006_End.setTypeface(myTypeface);

        EN006_stime = (TextView) rootView.findViewById(R.id.EN006_stime);
        EN006_stime.setTypeface(myTypeface);
        EN006_etime = (TextView) rootView.findViewById(R.id.EN006_etime);
        EN006_etime.setTypeface(myTypeface);

        return rootView;
    }

    private class JSONParse extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setIndeterminate(false);
            pDialog.setMessage(Fragment1Category.this.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN+"gaapi/ApiExamInfo?lang=" + lang + "&userid=" + userid;
            Log.d("Home page url: ", "> " + url);
            // Getting JSON from URL
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            pDialog.dismiss();
            super.onPostExecute(jsonStr);
            if (jsonStr != null) {
                JSONObject jsonob = null;
                try {
                    jsonob = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    // Getting JSON Array from URL
                    android = jsonob.getJSONArray(TAG_LIST);

                    for (int i = 0; i < android.length(); i++) {
                        JSONObject c = android.getJSONObject(i);
                        Log.d("response: ", "> " + c);
                        // Storing  JSON item in a Variable
                        name.add(c.getString(TAG_NAME));
                        assid.add(c.getString(TAG_ASSID));
                        code.add(c.getString(TAG_CODE));
                        duration.add(c.getString(TAG_DURATION));
                        status = c.getString(TAG_STATUS);

                        start = c.getString(TAG_START);

                        stime = c.getString(TAG_STIME);

                        etime = c.getString(TAG_ETIME);
                        Log.d("code response: ", "> " + code);
                        Log.d("code response: ", "> " + code);
                        if (code.get(i).equals("EN-001") || code.get(i).equals("AR-001")) {

                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN001_Start.setText(R.string.starttime);
                                    EN001_End.setText(R.string.endtime);
                                    EN001_date.setText(start);
                                    EN001_duration.setText(duration.get(i));
                                    EN001_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN001_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {
                                        EN001.setBackgroundResource(R.drawable.gk);

                                        EN001_Start.setText(R.string.starttime);
                                        EN001_End.setText(R.string.endtime);
                                        EN001_Tittle.setText(name.get(i));
                                        EN001_Tittle.setGravity(Gravity.CENTER);
                                        EN001_date.setText(start);
                                        EN001_duration.setText(duration.get(i));
                                        EN001_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN001_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI = i;
                                        final int finalI6 = i;
                                        EN001.setOnClickListener(new View.OnClickListener() {
                                            // Start new list activity
                                            public void onClick(View v) {


                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI6));
                                                editor.putString("topicname", name.get(finalI6));
                                                editor.commit();
                                                System.out.println("start " + start);
                                                System.out.println("Is assid " + assid);

                                                Intent intent = new Intent(getActivity(), Quizinstructions.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {

                                }

                            } else {

                            }

                        }
                        if (code.get(i).equals("EN-002") || code.get(i).equals("AR-002")) {

                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN002_Start.setText(R.string.starttime);
                                    EN002_End.setText(R.string.endtime);
                                    EN002_date.setText(start);
                                    EN002_duration.setText(duration.get(i));
                                    EN002_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN002_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {
                                        EN002.setBackgroundResource(R.drawable.numeriacal_ebility);
                                        EN002_Start.setText(R.string.starttime);
                                        EN002_End.setText(R.string.endtime);
                                        EN002_Tittle.setText(name.get(i));
                                        EN002_Tittle.setGravity(Gravity.CENTER);

                                        EN002_date.setText(start);
                                        EN002_duration.setText(duration.get(i));
                                        EN002_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN002_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI1 = i;
                                        EN002.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {


                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI1));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI1));
                                                editor.putString("topicname", name.get(finalI1));
                                                editor.commit();
                                                Intent intent = new Intent(getActivity(), Quizinstructions.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {

                                }

                            } else {

                            }
                        }
                        if (code.get(i).equals("EN-003") || code.get(i).equals("AR-003")) {
                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN003_Start.setText(R.string.starttime);
                                    EN003_End.setText(R.string.endtime);


                                    EN003_date.setText(start);
                                    EN003_duration.setText(duration.get(i));
                                    EN003_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN003_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {

                                        EN003.setBackgroundResource(R.drawable.logical_reasoning);
                                        EN003_Start.setText(R.string.starttime);
                                        EN003_End.setText(R.string.endtime);

                                        EN003_Tittle.setText(name.get(i));
                                        EN003_Tittle.setGravity(Gravity.CENTER);

                                        EN003_date.setText(start);
                                        EN003_duration.setText(duration.get(i));
                                        EN003_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN003_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI2 = i;
                                        final int finalI7 = i;
                                        EN003.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI2));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI7));
                                                editor.putString("topicname", name.get(finalI7));
                                                editor.commit();
                                                Intent intent = new Intent(getActivity(), Quizinstructions.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {


                                }

                            } else {


                            }
                        }
                        if (code.get(i).equals("EN-004") || code.get(i).equals("AR-004")) {
                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN004_Start.setText(R.string.starttime);
                                    EN004_End.setText(R.string.endtime);

                                    EN004_Tittle.setText(name.get(i));
                                    EN004_Tittle.setGravity(Gravity.CENTER);

                                    EN004_date.setText(start);
                                    EN004_duration.setText(duration.get(i));
                                    EN004_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN004_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {

                                        EN004.setBackgroundResource(R.drawable.english_communictaion);
                                        EN004_Start.setText(R.string.starttime);
                                        EN004_End.setText(R.string.endtime);

                                        EN004_Tittle.setText(name.get(i));
                                        EN004_Tittle.setGravity(Gravity.CENTER);

                                        EN004_date.setText(start);
                                        EN004_duration.setText(duration.get(i));
                                        EN004_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN004_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI3 = i;
                                        final int finalI8 = i;
                                        EN004.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {

                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI3));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI8));
                                                editor.putString("topicname", name.get(finalI8));
                                                editor.commit();
                                                Intent intent = new Intent(getActivity(), Quizinstructions.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {

                                }

                            } else {


                            }
                        }
                        if (code.get(i).equals("EN-005") || code.get(i).equals("AR-005")) {
                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN005_Start.setText(R.string.starttime);
                                    EN005_End.setText(R.string.endtime);

                                    EN005_date.setText(start);
                                    EN005_duration.setText(duration.get(i));
                                    EN005_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN005_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {

                                        EN005.setBackgroundResource(R.drawable.basic_computer_skills);
                                        EN005_Start.setText(R.string.starttime);
                                        EN005_End.setText(R.string.endtime);
                                        EN005_Tittle.setText(name.get(i));
                                        EN005_Tittle.setGravity(Gravity.CENTER);

                                        EN005_date.setText(start);
                                        EN005_duration.setText(duration.get(i));
                                        EN005_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN005_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI4 = i;
                                        EN005.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {


                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI4));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI4));
                                                editor.putString("topicname", name.get(finalI4));
                                                editor.commit();
                                                Intent intent = new Intent(getActivity(), Quizinstructions.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {

                                }

                            } else {


                            }
                        }
                        if (code.get(i).equals("EN-006") || code.get(i).equals("AR-006")) {

                            if (start.equals(date)) {

                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

                                boolean isSplit = false, isWithin = false;
                                Date st = null, et = null, ct = null;
                                st = sdf1.parse(stime);
                                et = sdf1.parse(etime);
                                ct = sdf1.parse(CurentTime);

                                isSplit = (et.compareTo(st) < 0);
                                if (isSplit) {
                                    isWithin = (ct.after(st) || ct.before(et));
                                } else {
                                    isWithin = (ct.after(st) && ct.before(et));
                                }
                                System.out.println("Is time within interval? " + isWithin);
                                if (isWithin == true) {
                                    EN006_Start.setText(R.string.starttime);
                                    EN006_End.setText(R.string.endtime);

                                    EN006_date.setText(start);
                                    EN006_duration.setText(duration.get(i));
                                    EN006_stime.setText(stime);
                                    Log.d("uid1: ", "> " + start);
                                    EN006_etime.setText(etime);
                                    Log.d("dae: ", "> " + etime);
                                    if (status.equals("0")) {

                                        EN006.setBackgroundResource(R.drawable.psychometric);

                                        EN006_Start.setText(R.string.starttime);
                                        EN006_End.setText(R.string.endtime);
                                        EN006_Tittle.setText(name.get(i));
                                        EN006_Tittle.setGravity(Gravity.CENTER);

                                        EN006_date.setText(start);
                                        EN006_duration.setText(duration.get(i));
                                        EN006_stime.setText(stime);
                                        Log.d("uid1: ", "> " + start);
                                        EN006_etime.setText(etime);
                                        Log.d("dae: ", "> " + etime);
                                        final int finalI5 = i;
                                        EN006.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {

                                                SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putString("assid", assid.get(finalI5));
                                                editor.putString("start", start);
                                                editor.putString("duration", duration.get(finalI5));
                                                editor.putString("topicname", name.get(finalI5));
                                                editor.commit();
                                                Intent intent = new Intent(getActivity(), Quizinstructions_ps.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {

                                    }

                                } else {

                                }

                            } else {

                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (ParseException e) {
                    e.printStackTrace();

                }

            } else {
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


}