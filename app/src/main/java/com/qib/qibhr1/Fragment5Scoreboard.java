package com.qib.qibhr1;

import android.app.Activity;
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

public class Fragment5Scoreboard extends Fragment {
    private ArrayList<String> topicCode = new ArrayList<>();
    private ArrayList<String> topicName = new ArrayList<>();
    private ArrayList<String> assessmentId = new ArrayList<>();
    private ArrayList<String> assementName = new ArrayList<>();
    private ArrayList<String> totalScore = new ArrayList<>();
    private ArrayList<String> obtainedScore = new ArrayList<>();
    private ArrayList<String> accuracyc = new ArrayList<>();
    private ArrayList<String> timespend = new ArrayList<>();
    private ArrayList<String> Psymessagepsy = new ArrayList<>();
    private ArrayList<String> PsyUrlpsy = new ArrayList<>();

    private LinearLayout Gendral, scorecard;//score and normal layout
    private LinearLayout General_Knowledgenormal, Numerical_Abilitynor, Logical_Reasoningnor, English_Communicationnor, Basic_Computer_Skillsnor, Psychometric_Quiz;//normal layout for 6 catagories
    private TextView gke, numericale, logical, english, computerbasic, psychometricte;//normal layout textview
    private ImageView nextgk, nextnu, nextlr, nextec, nextbc, nextpq;
    private LinearLayout General_Knowledgenormalscore, Numerical_Abilityscore, Logical_Reasoningnorscore, English_Communicationnorscore, Basic_Computerscore_Skillsscore, Psychometric_Quizscore;
    private TextView gk, score, timing, accuracy;//Gendral knowledge
    private TextView nu, score1, accuracy1, timing1;//numerical ability
    private TextView logical1, score2, accuracy2, timing2;//logical resening
    private TextView englis, score3, accuracy3, timing3;//english communication
    private TextView computersk, score4, accuracy4, timining4;//computer skills
    private TextView psychometrictete, score5, accuracy5, timining5, noscore;//psychometric test
    private ImageView psychometricimage;
    private Typeface myTypeface;
    private TextView l1, l2, l3, l4, l5, a1, a2, a3, a4, a5, s1, s2, s3, s4, s5;
    ListView list;
    TextView name, Tittle;
    TextView stime, date, duration;
    TextView etime;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5;
    private ImageView award1, award2, award3, award4, award5;

    //URL to get JSON Array


    //JSON Node Names
    private static final String TAG_LIST = "schedulelst";
    private static final String TAG_NAME = "TopicName";
    private static final String TAG_CODE = "TopicCode";
    private static final String TAG_STATUS = "Status";
    private static final String TAG_START = "startDate";
    private static final String TAG_STIME = "startTime";
    private static final String TAG_ETIME = "endTime";
    private static final String TAG_DURATION = "Duration";
    private static final String tag = "Instruction Response";
    JSONArray android = null;
    private String lang;


    String userid, roleid;
    JSONParser jsonParser = new JSONParser();
    private ArrayList<String> img = new ArrayList<>();

    // this Fragment will be called from MainActivity
    public Fragment5Scoreboard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.scorecard,
                container, false);

        SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        Log.d(tag, "lang " + lang);
        userid = settings.getString("userid", null);

        myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "dax_regular.ttf");
        General_Knowledgenormal = (LinearLayout) rootView.findViewById(R.id.General_Knowledgenormal);
        General_Knowledgenormal.setVisibility(View.GONE);
        General_Knowledgenormalscore = (LinearLayout) rootView.findViewById(R.id.gksore);
        General_Knowledgenormalscore.setVisibility(View.GONE);

        Numerical_Abilitynor = (LinearLayout) rootView.findViewById(R.id.Numerical_Abilitynor);
        Numerical_Abilitynor.setVisibility(View.GONE);
        Numerical_Abilityscore = (LinearLayout) rootView.findViewById(R.id.numerialso);
        Numerical_Abilityscore.setVisibility(View.GONE);


        Logical_Reasoningnor = (LinearLayout) rootView.findViewById(R.id.Logical_Reasoningnor);
        Logical_Reasoningnor.setVisibility(View.GONE);
        Logical_Reasoningnorscore = (LinearLayout) rootView.findViewById(R.id.logialsore);
        Logical_Reasoningnorscore.setVisibility(View.GONE);

        noscore = (TextView) rootView.findViewById(R.id.scoreboard);
        noscore.setVisibility(View.GONE);

        English_Communicationnor = (LinearLayout) rootView.findViewById(R.id.English_Communicationnor);
        English_Communicationnor.setVisibility(View.GONE);
        English_Communicationnorscore = (LinearLayout) rootView.findViewById(R.id.englissore);
        English_Communicationnorscore.setVisibility(View.GONE);


        Basic_Computer_Skillsnor = (LinearLayout) rootView.findViewById(R.id.Basic_Computer_Skillsnor);
        Basic_Computer_Skillsnor.setVisibility(View.GONE);
        Basic_Computerscore_Skillsscore = (LinearLayout) rootView.findViewById(R.id.basiComputerskils);
        Basic_Computerscore_Skillsscore.setVisibility(View.GONE);

        Psychometric_Quiz = (LinearLayout) rootView.findViewById(R.id.Psychometric_Quiz);
        Psychometric_Quiz.setVisibility(View.GONE);
        Psychometric_Quizscore = (LinearLayout) rootView.findViewById(R.id.psysore);
        Psychometric_Quizscore.setVisibility(View.GONE);

        nextgk = (ImageView) rootView.findViewById(R.id.gkbutton);
        nextnu = (ImageView) rootView.findViewById(R.id.nubutton);
        nextlr = (ImageView) rootView.findViewById(R.id.lobutton);
        nextec = (ImageView) rootView.findViewById(R.id.engbutton);
        nextbc = (ImageView) rootView.findViewById(R.id.cpbutton);
        nextpq = (ImageView) rootView.findViewById(R.id.psbutton);

        award1 = (ImageView) rootView.findViewById(R.id.award1);
        award2 = (ImageView) rootView.findViewById(R.id.award2);
        award3 = (ImageView) rootView.findViewById(R.id.award3);
        award4 = (ImageView) rootView.findViewById(R.id.award4);
        award5 = (ImageView) rootView.findViewById(R.id.award5);

        l1 = (TextView) rootView.findViewById(R.id.l1);
        l1.setTypeface(myTypeface);
        l2 = (TextView) rootView.findViewById(R.id.l2);
        l2.setTypeface(myTypeface);
        l3 = (TextView) rootView.findViewById(R.id.l3);
        l3.setTypeface(myTypeface);
        l4 = (TextView) rootView.findViewById(R.id.l4);
        l4.setTypeface(myTypeface);
        l5 = (TextView) rootView.findViewById(R.id.l5);
        l5.setTypeface(myTypeface);

        a1 = (TextView) rootView.findViewById(R.id.a1);
        a1.setTypeface(myTypeface);
        a2 = (TextView) rootView.findViewById(R.id.a2);
        a2.setTypeface(myTypeface);
        a3 = (TextView) rootView.findViewById(R.id.a3);
        a3.setTypeface(myTypeface);
        a4 = (TextView) rootView.findViewById(R.id.a4);
        a4.setTypeface(myTypeface);
        a5 = (TextView) rootView.findViewById(R.id.a5);
        a5.setTypeface(myTypeface);

        s1 = (TextView) rootView.findViewById(R.id.s1);
        s1.setTypeface(myTypeface);
        s2 = (TextView) rootView.findViewById(R.id.s2);
        s2.setTypeface(myTypeface);
        s3 = (TextView) rootView.findViewById(R.id.s3);
        s3.setTypeface(myTypeface);
        s4 = (TextView) rootView.findViewById(R.id.s4);
        s4.setTypeface(myTypeface);
        s5 = (TextView) rootView.findViewById(R.id.s5);
        s5.setTypeface(myTypeface);


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


        gk = (TextView) rootView.findViewById(R.id.gk);
        gk.setTypeface(myTypeface);

        score = (TextView) rootView.findViewById(R.id.score);
        score.setTypeface(myTypeface);
        timing = (TextView) rootView.findViewById(R.id.timing);
        timing.setTypeface(myTypeface);
        accuracy = (TextView) rootView.findViewById(R.id.accuracy);
        accuracy.setTypeface(myTypeface);


        nu = (TextView) rootView.findViewById(R.id.nu);
        nu.setTypeface(myTypeface);
        score1 = (TextView) rootView.findViewById(R.id.score1);
        score1.setTypeface(myTypeface);
        timing1 = (TextView) rootView.findViewById(R.id.timing1);
        timing1.setTypeface(myTypeface);
        accuracy1 = (TextView) rootView.findViewById(R.id.accuracy1);
        accuracy1.setTypeface(myTypeface);


        logical1 = (TextView) rootView.findViewById(R.id.logical);
        logical1.setTypeface(myTypeface);

        score2 = (TextView) rootView.findViewById(R.id.score2);
        score2.setTypeface(myTypeface);
        timing2 = (TextView) rootView.findViewById(R.id.timing2);
        timing2.setTypeface(myTypeface);
        accuracy2 = (TextView) rootView.findViewById(R.id.accuracy2);
        accuracy2.setTypeface(myTypeface);


        englis = (TextView) rootView.findViewById(R.id.englis);
        englis.setTypeface(myTypeface);
        score3 = (TextView) rootView.findViewById(R.id.score3);
        score3.setTypeface(myTypeface);
        timing3 = (TextView) rootView.findViewById(R.id.timing3);
        timing3.setTypeface(myTypeface);
        accuracy3 = (TextView) rootView.findViewById(R.id.accuracy3);
        accuracy3.setTypeface(myTypeface);


        computersk = (TextView) rootView.findViewById(R.id.computersk);
        computersk.setTypeface(myTypeface);
        score4 = (TextView) rootView.findViewById(R.id.score4);
        score4.setTypeface(myTypeface);
        timining4 = (TextView) rootView.findViewById(R.id.timing4);
        timining4.setTypeface(myTypeface);
        accuracy4 = (TextView) rootView.findViewById(R.id.accuracy4);
        accuracy4.setTypeface(myTypeface);


        psychometrictete = (TextView) rootView.findViewById(R.id.psychometricte);
        psychometrictete.setTypeface(myTypeface);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }


        General_Knowledgenormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-001") || (topicCode.get(i)).equals("AR-001")) {
                        General_Knowledgenormal.setVisibility(View.GONE);
                        General_Knowledgenormalscore.setVisibility(View.VISIBLE);


                        System.out.println("General knowledge " + topicName);
                        gk.setText(topicName.get(i));
                        score.setText(obtainedScore.get(i));
                        timing.setText(timespend.get(i));
                        accuracy.setText(accuracyc.get(i));
                    }
                }

            }
        });

        General_Knowledgenormalscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-001") || (topicCode.get(i)).equals("AR-001")) {
                        General_Knowledgenormal.setVisibility(View.VISIBLE);
                        General_Knowledgenormalscore.setVisibility(View.GONE);
                        gke.setText(topicName.get(i));

                        System.out.println("General knowledge gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        Numerical_Abilityscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-002") || (topicCode.get(i)).equals("AR-002")) {
                        Numerical_Abilitynor.setVisibility(View.VISIBLE);
                        Numerical_Abilityscore.setVisibility(View.GONE);
                        numericale.setText(topicName.get(i));

                        System.out.println("Numerical ability gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        Logical_Reasoningnorscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-003") || (topicCode.get(i)).equals("AR-003")) {

                        Logical_Reasoningnorscore.setVisibility(View.GONE);
                        Logical_Reasoningnor.setVisibility(View.VISIBLE);

                        logical.setText(topicName.get(i));
                        System.out.println("logical communication gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        English_Communicationnorscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-004") || (topicCode.get(i)).equals("AR-004")) {


                        English_Communicationnorscore.setVisibility(View.GONE);
                        English_Communicationnor.setVisibility(View.VISIBLE);
                        english.setText(topicName.get(i));

                        System.out.println("Englis ommuniation gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        Basic_Computerscore_Skillsscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-005") || (topicCode.get(i)).equals("AR-005")) {
                        Basic_Computerscore_Skillsscore.setVisibility(View.GONE);
                        Basic_Computer_Skillsnor.setVisibility(View.VISIBLE);
                        computerbasic.setText(topicName.get(i));

                        System.out.println("Basic computer skills gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        Psychometric_Quizscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-006") || (topicCode.get(i)).equals("AR-006")) {
                        Psychometric_Quizscore.setVisibility(View.GONE);
                        Psychometric_Quiz.setVisibility(View.VISIBLE);
                        psychometricte.setText(topicName.get(i));

                        System.out.println("Psychometric gfhgfhgfhgfh " + topicName);
                    }
                }

            }
        });
        Numerical_Abilitynor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-002") || (topicCode.get(i)).equals("AR-002")) {

                        Numerical_Abilitynor.setVisibility(View.GONE);

                        Numerical_Abilityscore.setVisibility(View.VISIBLE);


                        System.out.println("Numerical Ability " + topicName);
                        nu.setText(topicName.get(i));
                        score1.setText(obtainedScore.get(i));
                        timing1.setText(timespend.get(i));
                        accuracy1.setText(accuracyc.get(i));
                    }

                }

            }
        });

        Logical_Reasoningnor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-003") || (topicCode.get(i)).equals("AR-003")) {


                        Logical_Reasoningnor.setVisibility(View.GONE);


                        Logical_Reasoningnorscore.setVisibility(View.VISIBLE);


                        System.out.println("Logical Reasoning " + topicName);
                        logical1.setText(topicName.get(i));
                        score2.setText(obtainedScore.get(i));
                        System.out.println("Logical Reasoning obtained score" + obtainedScore.get(i));
                        timing2.setText(timespend.get(i));
                        accuracy2.setText(accuracyc.get(i));
                    }
                }

            }
        });
        English_Communicationnor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-004") || (topicCode.get(i)).equals("AR-004")) {


                        English_Communicationnor.setVisibility(View.GONE);
                        English_Communicationnorscore.setVisibility(View.VISIBLE);


                        System.out.println("English communication" + topicName);
                        englis.setText(topicName.get(i));
                        score3.setText(obtainedScore.get(i));
                        timing3.setText(timespend.get(i));
                        accuracy3.setText(accuracyc.get(i));
                    }
                }
            }
        });
        Basic_Computer_Skillsnor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-005") || (topicCode.get(i)).equals("AR-005")) {


                        Basic_Computer_Skillsnor.setVisibility(View.GONE);
                        Basic_Computerscore_Skillsscore.setVisibility(View.VISIBLE);

                        System.out.println("Basic computer skills " + topicName);
                        computersk.setText(topicName.get(i));
                        score4.setText(obtainedScore.get(i));
                        timining4.setText(timespend.get(i));
                        accuracy4.setText(accuracyc.get(i));
                    }
                }
            }
        });
        Psychometric_Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < topicCode.size(); i++) {

                    if ((topicCode.get(i)).equals("EN-006") || (topicCode.get(i)).equals("AR-006")) {
                        Psychometric_Quiz.setVisibility(View.GONE);

                        Psychometric_Quizscore.setVisibility(View.VISIBLE);

                /*AsyncJsonObject1 asyncObject = new AsyncJsonObject1();
                asyncObject.execute("");*/

                        System.out.println("Psychometric " + topicName);
                        psychometrictete.setText(topicName.get(i));

                        new DownloadImageTask(psychometricimage, getActivity()).execute(PsyUrlpsy.get(i));


                    }
                }
            }
        });
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
            String url = AppUrl.URL_COMMAN+"gaapi/ApiScorecard?userid=" + userid + "&lang=" + lang;
            Log.d("Scoreboard url: ", "> " + url);
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            return jsonStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), Fragment5Scoreboard.this.getString(R.string.plz), Fragment5Scoreboard.this.getString(R.string.lodingsrc), true);
        }

        protected void onPostExecute(String jsonResult) {
            super.onPostExecute(jsonResult);
            progressDialog.dismiss();
            System.out.println("Returned Json object " + jsonResult);
            if (jsonResult != null) {
                try {
                    JSONArray jsonarray = new JSONArray(jsonResult);
                    JSONObject object = null;
                    for (int n = 0; n < jsonarray.length(); n++) {
                        object = jsonarray.getJSONObject(n);
                        System.out.println("Json object value" + object);
                        topicCode.add(object.getString("TopicCode"));
                        System.out.println("TopicCode" + topicCode);
                        topicName.add(object.getString("TopicName"));
                        System.out.println("TopicName" + topicName);
                        assessmentId.add(object.getString("AssessmentId"));
                        System.out.println("AssessmentId" + assessmentId);
                        assementName.add(object.getString("AssementName"));
                        System.out.println("AssementName" + assementName);
                        totalScore.add(object.getString("TotalScore"));
                        System.out.println("TotalScore" + totalScore);
                        obtainedScore.add(object.getString("ObtainedScore"));
                        System.out.println("ObtainedScore" + obtainedScore);
                        accuracyc.add(object.getString("accuracy"));
                        System.out.println("accuracy" + accuracyc);
                        timespend.add(object.getString("timespend"));
                        System.out.println("timespend" + timespend);
                        Psymessagepsy.add(object.getString("Psymessage"));
                        System.out.println("Psymessage" + Psymessagepsy);
                        PsyUrlpsy.add(object.getString("PsyUrl"));
                        System.out.println("Image url for psy" + PsyUrlpsy);

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
            noscore.setVisibility(View.VISIBLE);
        } else {
            noscore.setVisibility(View.GONE);
            for (int i = 0; i < topicCode.size(); i++) {
                System.out.println("first looop odition accuracy" + topicCode.size());

                if ((topicCode.get(i)).equals("EN-001") || (topicCode.get(i)).equals("AR-001")) {

                    General_Knowledgenormal.setVisibility(View.VISIBLE);
                    gke.setText(topicName.get(i));
                    System.out.println("" + topicCode.size());


                } else if ((topicCode.get(i)).equals("EN-002") || (topicCode.get(i)).equals("AR-002")) {
        /*Gendral.setVisibility(Gendral.VISIBLE);*/
                    Numerical_Abilitynor.setVisibility(View.VISIBLE);
                    numericale.setText(topicName.get(i));
                } else if ((topicCode.get(i)).equals("EN-003") || (topicCode.get(i)).equals("AR-003")) {
        /*Gendral.setVisibility(Gendral.VISIBLE);*/
                    Logical_Reasoningnor.setVisibility(View.VISIBLE);
                    logical.setText(topicName.get(i));
                } else if ((topicCode.get(i)).equals("EN-004") || (topicCode.get(i)).equals("AR-004")) {
        /*Gendral.setVisibility(Gendral.VISIBLE);*/
                    English_Communicationnor.setVisibility(View.VISIBLE);
                    english.setText(topicName.get(i));
                } else if ((topicCode.get(i)).equals("EN-005") || (topicCode.get(i)).equals("AR-005")) {
        /*Gendral.setVisibility(Gendral.VISIBLE);*/
                    Basic_Computer_Skillsnor.setVisibility(View.VISIBLE);
                    computerbasic.setText(topicName.get(i));
                } else if ((topicCode.get(i)).equals("EN-006") || (topicCode.get(i)).equals("AR-006")) {
       /* Gendral.setVisibility(Gendral.VISIBLE);*/
                    Psychometric_Quiz.setVisibility(View.VISIBLE);
                    psychometricte.setText(topicName.get(i));
                }

            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView psychometricimage;
        Context mcontext;
        private ProgressDialog progressDialog;

        public DownloadImageTask(ImageView psychometricimage, Activity activity) {
            this.psychometricimage = psychometricimage;
            this.mcontext = activity;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getActivity().getString(R.string.lodingsrc));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
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
            super.onPostExecute(result);
            progressDialog.dismiss();
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



