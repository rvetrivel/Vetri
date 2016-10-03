package com.qib.qibhr1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Fragment4Activities extends Fragment {

    //private static String url = "http://10.20.30.105:2020/gaapi/ApiScorecard?userid=17&lang=EN";
    //private static String urlpsychometric = "http://10.20.30.105:2020/gaapi/ApiPsychometricScore?userid=17&lang=EN";
    Button netxt;
    //private String Assementidpsy,Psymessagepsy,Psycodepsy,PsyUrlpsy,Statuspsy;
    private ArrayList<String> topicCode = new ArrayList<>();
    private ArrayList<String> topicName = new ArrayList<>();
    private ArrayList<String> startDate = new ArrayList<>();
    private ArrayList<String> startTime = new ArrayList<>();
    private ArrayList<String> endTime = new ArrayList<>();
    private ArrayList<String> duration1 = new ArrayList<>();
    private ArrayList<String> accuracyc = new ArrayList<>();
    private ArrayList<String> timespend = new ArrayList<>();
    private ArrayList<String> Psymessagepsy = new ArrayList<>();
    private ArrayList<String> PsyUrlpsy = new ArrayList<>();

    private LinearLayout Gendral, scorecard;//score and normal layout
    private LinearLayout General_Knowledgenormal, Numerical_Abilitynor, Logical_Reasoningnor, English_Communicationnor, Basic_Computer_Skillsnor, Psychometric_Quiz;//normal layout for 6 catagories
    private TextView gke, numericale, logical, english, computerbasic, psychometricte;//normal layout textview
    private Button nextgk, nextnu, nextlr, nextec, nextbc, nextpq;
    private LinearLayout General_Knowledgenormalscore, Numerical_Abilityscore, Logical_Reasoningnorscore, English_Communicationnorscore, Basic_Computerscore_Skillsscore, Psychometric_Quizscore;
    private TextView gk, score, timing, accuracy;//Gendral knowledge
    private TextView nu, score1, accuracy1, timing1;//numerical ability
    private TextView logical1, score2, accuracy2, timing2;//logical resening
    private TextView englis, score3, accuracy3, timing3;//english communication
    private TextView computersk, score4, accuracy4, timining4;//computer skills
    private TextView psychometrictete, score5, accuracy5, timining5;//psychometric test
    private ImageView psychometricimage;
    private Typeface myTypeface;
    private TextView l2, l3, l4, l5, a1, a2, a3, a4, e1, e2, e3, e4;
    private TextView b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4;
    ListView list;
    TextView name, Tittle, noacticity;
    TextView stime, date, duration;
    TextView etime;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5;
    private ImageButton award1, award2, award3, award4, award5;

    //URL to get JSON Array


    //JSON Node Names
    private static final String TAG_LIST = "schedulelst";
    private static final String TAG_NAME = "TopicName";
    private static final String TAG_CODE = "TopicCode";
    private static final String TAG_STATUS = "Status";
    private static final String TAG_START = "startDate";
    private static final String TAG_STIME = "startTime";
    private static final String TAG_ETIME = "endTime";
    private static final String TAG_IMAGE = "imageid";
    private static final String tag = "Instruction Response";
    JSONArray android = null;
    private String lang;


    String userid, roleid;
    JSONParser jsonParser = new JSONParser();
    private ArrayList<String> img = new ArrayList<>();

    // this Fragment will be called from MainActivity
    public Fragment4Activities() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.apple_fragment,
                container, false);
        SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        Log.d(tag, "lang " + lang);
        userid = settings.getString("userid", null);

        myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "dax_regular.ttf");
        General_Knowledgenormal = (LinearLayout) rootView.findViewById(R.id.gendral);
        General_Knowledgenormal.setVisibility(View.GONE);

        Numerical_Abilitynor = (LinearLayout) rootView.findViewById(R.id.numerical);
        Numerical_Abilitynor.setVisibility(View.GONE);

        Logical_Reasoningnor = (LinearLayout) rootView.findViewById(R.id.Logical);
        Logical_Reasoningnor.setVisibility(View.GONE);

        English_Communicationnor = (LinearLayout) rootView.findViewById(R.id.English);
        English_Communicationnor.setVisibility(View.GONE);

        noacticity = (TextView) rootView.findViewById(R.id.noactivities);
        noacticity.setVisibility(View.GONE);

        Basic_Computer_Skillsnor = (LinearLayout) rootView.findViewById(R.id.computerskil);
        Basic_Computer_Skillsnor.setVisibility(View.GONE);


        Psychometric_Quiz = (LinearLayout) rootView.findViewById(R.id.psy);
        Psychometric_Quiz.setVisibility(View.GONE);


        l2 = (TextView) rootView.findViewById(R.id.start);
        l3 = (TextView) rootView.findViewById(R.id.stime);

        l4 = (TextView) rootView.findViewById(R.id.etime);
        l5 = (TextView) rootView.findViewById(R.id.duration1);

        a1 = (TextView) rootView.findViewById(R.id.start1);

        a2 = (TextView) rootView.findViewById(R.id.stime1);

        a3 = (TextView) rootView.findViewById(R.id.etime1);
        a4 = (TextView) rootView.findViewById(R.id.duration2);


        b1 = (TextView) rootView.findViewById(R.id.start2);

        b2 = (TextView) rootView.findViewById(R.id.stime2);

        b3 = (TextView) rootView.findViewById(R.id.etime2);

        b4 = (TextView) rootView.findViewById(R.id.duration3);

        c1 = (TextView) rootView.findViewById(R.id.start3);

        c2 = (TextView) rootView.findViewById(R.id.stime3);

        c3 = (TextView) rootView.findViewById(R.id.etime3);

        c4 = (TextView) rootView.findViewById(R.id.duration4);
        d1 = (TextView) rootView.findViewById(R.id.start4);

        d2 = (TextView) rootView.findViewById(R.id.stime4);

        d3 = (TextView) rootView.findViewById(R.id.etime4);
        d4 = (TextView) rootView.findViewById(R.id.duration5);

        e1 = (TextView) rootView.findViewById(R.id.start5);

        e2 = (TextView) rootView.findViewById(R.id.stime5);

        e3 = (TextView) rootView.findViewById(R.id.etime5);
        e4 = (TextView) rootView.findViewById(R.id.duration6);

        psychometricimage = (ImageView) rootView.findViewById(R.id.psychometricresult);
        //normal page text view
        gke = (TextView) rootView.findViewById(R.id.gkn);
        gke.setTypeface(myTypeface);
        numericale = (TextView) rootView.findViewById(R.id.numericalab);
        numericale.setTypeface(myTypeface);
        logical = (TextView) rootView.findViewById(R.id.logicalre);
        logical.setTypeface(myTypeface);
        english = (TextView) rootView.findViewById(R.id.english);
        english.setTypeface(myTypeface);
        computerbasic = (TextView) rootView.findViewById(R.id.computer);
        computerbasic.setTypeface(myTypeface);
        psychometricte = (TextView) rootView.findViewById(R.id.psychometric);
        psychometricte.setTypeface(myTypeface);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);


        if (isNetworkAvailable()) {
            AsyncJsonObject asyncObject = new AsyncJsonObject();
            asyncObject.execute("");
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
    }


    private class AsyncJsonObject extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN+"gaapi/ApiExamInfo?lang=" + lang + "&userid=" + userid;
            Log.d("Activities url: ", "> " + url);
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
            //String jsonResult = "";
            return jsonStr;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), Fragment4Activities.this.getString(R.string.plz), Fragment4Activities.this.getString(R.string.lodingacti), true);
        }

        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);
            progressDialog.dismiss();

            System.out.println("Returned Json object " + jsonResult);
            if (jsonResult != null) {
                JSONObject sctivity = null;
                try {
                    sctivity = new JSONObject(jsonResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonarray = new JSONArray();
                    jsonarray = sctivity.getJSONArray("schedulelst");
                    JSONObject object = null;
                    for (int n = 0; n < jsonarray.length(); n++) {
                        object = jsonarray.getJSONObject(n);
                        System.out.println("Json object value" + object);
                        topicCode.add(object.getString("TopicCode"));
                        System.out.println("TopicCode" + topicCode);
                        topicName.add(object.getString("TopicName"));
                        System.out.println("TopicName" + topicName);
                        startDate.add(object.getString("startDate"));
                        System.out.println("AssessmentId" + startDate);
                        startTime.add(object.getString("startTime"));
                        System.out.println("AssementName" + startTime);
                        endTime.add(object.getString("endTime"));
                        System.out.println("TotalScore" + endTime);
                        duration1.add(object.getString("Duration"));
                        System.out.println("TotalScore" + duration1);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                scorecard();
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                // set dialog message
                alertDialogBuilder
                        .setMessage(getActivity().getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), Login_Screen.class);
                                startActivity(intent);
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


    private void scorecard() {
        System.out.println("Sore ard" + topicCode.size());
        if (topicCode.size() == 0) {
            noacticity.setVisibility(View.VISIBLE);
        } else {
            noacticity.setVisibility(View.GONE);

            for (int i = 0; i < topicCode.size(); i++) {
                System.out.println("first looop odition accuracy" + topicCode.size());

                if ((topicCode.get(i)).equals("EN-001") || (topicCode.get(i)).equals("AR-001")) {

                    General_Knowledgenormal.setVisibility(View.VISIBLE);
                    //gke.setText(topicName.get(i));
                    System.out.println("Genearal know" + topicCode.size());
                    l2.setText(startDate.get(i));
                    l3.setText(startTime.get(i));
                    l4.setText(endTime.get(i));
                    l5.setText(duration1.get(i));

                } else if ((topicCode.get(i)).equals("EN-002") || (topicCode.get(i)).equals("AR-002")) {
                    Numerical_Abilitynor.setVisibility(View.VISIBLE);
                    //numericale.setText(topicName.get(i));
                    a1.setText(startDate.get(i));
                    a2.setText(startTime.get(i));
                    a3.setText(endTime.get(i));
                    a4.setText(duration1.get(i));

                } else if ((topicCode.get(i)).equals("EN-003") || (topicCode.get(i)).equals("AR-003")) {
                    Logical_Reasoningnor.setVisibility(View.VISIBLE);
                    //logical.setText(topicName.get(i));
                    b1.setText(startDate.get(i));
                    b2.setText(startTime.get(i));
                    b3.setText(endTime.get(i));
                    b4.setText(duration1.get(i));

                } else if ((topicCode.get(i)).equals("EN-004") || (topicCode.get(i)).equals("AR-004")) {
                    English_Communicationnor.setVisibility(View.VISIBLE);
                    //english.setText(topicName.get(i));
                    c1.setText(startDate.get(i));
                    c2.setText(startTime.get(i));
                    c3.setText(endTime.get(i));
                    c4.setText(duration1.get(i));


                } else if ((topicCode.get(i)).equals("EN-005") || (topicCode.get(i)).equals("AR-005")) {
                    Basic_Computer_Skillsnor.setVisibility(View.VISIBLE);
                    //computerbasic.setText(topicName.get(i));
                    d1.setText(startDate.get(i));
                    d2.setText(startTime.get(i));
                    d3.setText(endTime.get(i));
                    d4.setText(duration1.get(i));

                } else if ((topicCode.get(i)).equals("EN-006") || (topicCode.get(i)).equals("AR-006")) {
                    Psychometric_Quiz.setVisibility(View.VISIBLE);
                    //psychometricte.setText(topicName.get(i));
                    e1.setText(startDate.get(i));
                    e2.setText(startTime.get(i));
                    e3.setText(endTime.get(i));
                    e4.setText(duration1.get(i));
                }

            }
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView psychometricimage;

        public DownloadImageTask(ImageView psychometricimage) {
            this.psychometricimage = psychometricimage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {

                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            psychometricimage.setImageBitmap(result);
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



