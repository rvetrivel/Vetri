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
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;

public class QuizActivity extends Activity implements OnClickListener {


    private CheckBox ch1, ch2, ch3, ch4, ch5, checkboxgroup;
    private TextView quizQuestion, myCounter;
    private CountDownTimer mCountDown;
    private TextView op1;
    private TextView op2;
    private TextView op3;
    private TextView op4;
    private TextView op5;
    private TextView quizQuestion2;
    private TextView quizQuestion3;
    private Button nextButton;
    private RadioGroup radioGroup;
    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour, optionfifth;

    private JSONObject request = null;
    private JSONObject holeresponce = null;
    private int currentQuizQuestion;
    private int quizCount;
    private static final String tag = "SignUp Instruction";
    //private static String url = "http://10.20.30.105:2020/gaapi/ApiQuestionRequest?Assid=15&userid=17";
    // private static String urlresponce = "http://www.ems.belmonttek.com/gaapi/ApiAnswersFeatch";
    private static String urlresponce = AppUrl.URL_COMMAN+"gaapi/ApiAnswersFeatch";
    // private static String urlresponce = "http://10.20.30.137:83/gaapi/ApiAnswersFeatch";
    private QuizWrapper firstQuestion;
    // private int questiontypeid;
    private List<QuizWrapper> parsedObject;
    private int questiontypeid;
    private String Questionactivity;
    private String Language = null;
    private ImageView questioncontent, tr1, fal, yes, no;
    private String questioncontent1, examstartTime;
    private int checkboxselected;
    private JSONParser jsonParser = new JSONParser();
    private JSONObject json;
    private static final String TAG_SUCCESS = "TopicCode";
    private static final String TAG_ERROR = "TopicName";
    private static final String TAG_MESSAGE = "AssessmentId";
    private static final String TAG_SCORE = "score";
    private ProgressDialog pDialog;
    private String qun;
    private JSONArray result = new JSONArray();
    private ArrayList<Integer> ans = new ArrayList<Integer>();
    private LayoutInflater inflate;
    private LinearLayout choose, checked, trf, filltheblank, match, question, yesrno, essaywriting, mediaplayer;
    private RelativeLayout layoutLeft, layoutRight;
    private RelativeLayout.LayoutParams relParam;
    // private ViewSwitcher switcher;
    private static final int REFRESH_SCREEN = 1;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private Spinner wright_sp;
    private MediaPlayer mp;
    private int option;
    //private Toolbar toolbar;
    private Typeface myTypeface;
    private ArrayList<Integer> checkSelected = new ArrayList<>();
    private int answerSelected = 0;
    private String SelectedAnswer, assid, userid, lang, examstartdate, examendtime, durationtime;
    private int buttonSelected;
    private int time;
    private ImageView timeline, chcorrect1, chcorrect2, chcorrect3, chcorrect4, chcorrect5;
    private ArrayList<Integer> timeline1 = new ArrayList<>();
    private String spinneran1, spinneran2, spinneran3, spinneran4, spinneran5;
    private int answerSelectedtrf = 0;
    private int answerSelectedYrN = 0;
    private EditText fill1, fill2, fill3, fill4, fill5, essay;
    private String blank1, blank2, blank3, blank4, blank5;
    private String answer;
    private String essayanswer;
    private ImageButton play, stop, pause;
    private ArrayList<String> optionidch = new ArrayList<String>();
    private ArrayList<String> optionidmr = new ArrayList<String>();
    private ArrayList<Integer> timelineimg = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
    private JSONArray Matchingans;
    private String duration;
    private long countdowntime;
    private String responseServer;
    private int status;
    private String startdate;
    private String topic;
    private Handler handler;
    private Timer autoUpdate;
    private int upload = 1;
    private LinearLayout l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15;
    long totalTimeCountInMilliseconds;      // total count down time in milliseconds
    long timeBlinkInMilliseconds;
    TextView textViewShowTime;              // will show the time
    CountDownTimer countDownTimer;
    boolean blink;
    LinearLayout Progresbar;
    private int Timeup;
    private LinearLayout chans1, chans2, chans3, chans4, chans5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }


        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        assid = settings.getString("assid", null);
        userid = settings.getString("userid", null);
        lang = settings.getString("lang", null);
        examstartdate = settings.getString("start", null);
        durationtime = settings.getString("duration", null);
        topic = settings.getString("topicname", null);

        Log.d(tag, "examstartdate " + examstartdate);
        String date = examstartdate;
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date newstartDate = input.parse(date);                 // parse input
            startdate = output.format(newstartDate);        // format output
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");


        Log.d(tag, "assid " + assid);
        Log.d(tag, "examstartdate changed " + startdate);

        Progresbar = (LinearLayout) findViewById(R.id.progress);
        Progresbar.setVisibility(View.VISIBLE);
        question = (LinearLayout) findViewById(R.id.questi);
        choose = (LinearLayout) findViewById(R.id.choose);
        checked = (LinearLayout) findViewById(R.id.optional);
        trf = (LinearLayout) findViewById(R.id.trufal);
        filltheblank = (LinearLayout) findViewById(R.id.filltheblank);
        match = (LinearLayout) findViewById(R.id.matching);
        yesrno = (LinearLayout) findViewById(R.id.yesrno);
        essaywriting = (LinearLayout) findViewById(R.id.essaywrite);
        mediaplayer = (LinearLayout) findViewById(R.id.mediaplayer);
        choose.setVisibility(View.GONE);
        checked.setVisibility(View.GONE);
        trf.setVisibility(View.GONE);
        filltheblank.setVisibility(View.GONE);
        match.setVisibility(View.GONE);
        yesrno.setVisibility(View.GONE);
        essaywriting.setVisibility(View.GONE);
        mediaplayer.setVisibility(View.GONE);
        l1 = (LinearLayout) findViewById(R.id.lin1);
        l1.setBackgroundColor(Color.parseColor("#00BFFF"));

        l2 = (LinearLayout) findViewById(R.id.lin2);
        l2.setBackgroundColor(Color.parseColor("#00BFFF"));

        l3 = (LinearLayout) findViewById(R.id.lin3);
        l3.setBackgroundColor(Color.parseColor("#00BFFF"));

        l4 = (LinearLayout) findViewById(R.id.lin4);
        l4.setBackgroundColor(Color.parseColor("#00BFFF"));

        l5 = (LinearLayout) findViewById(R.id.lin5);
        l5.setBackgroundColor(Color.parseColor("#00BFFF"));

        l6 = (LinearLayout) findViewById(R.id.lin6);
        l6.setBackgroundColor(Color.parseColor("#00BFFF"));

        l7 = (LinearLayout) findViewById(R.id.lin7);
        l7.setBackgroundColor(Color.parseColor("#00BFFF"));

        l8 = (LinearLayout) findViewById(R.id.lin8);
        l8.setBackgroundColor(Color.parseColor("#00BFFF"));

        l9 = (LinearLayout) findViewById(R.id.lin9);
        l9.setBackgroundColor(Color.parseColor("#00BFFF"));

        l10 = (LinearLayout) findViewById(R.id.lin10);
        l10.setBackgroundColor(Color.parseColor("#00BFFF"));

        l11 = (LinearLayout) findViewById(R.id.lin11);
        l11.setBackgroundColor(Color.parseColor("#00BFFF"));

        l12 = (LinearLayout) findViewById(R.id.lin12);
        l12.setBackgroundColor(Color.parseColor("#00BFFF"));

        l13 = (LinearLayout) findViewById(R.id.lin13);
        l13.setBackgroundColor(Color.parseColor("#00BFFF"));

        l14 = (LinearLayout) findViewById(R.id.lin14);
        l14.setBackgroundColor(Color.parseColor("#00BFFF"));

        l15 = (LinearLayout) findViewById(R.id.lin15);
        l15.setBackgroundColor(Color.parseColor("#00BFFF"));

        chans1 = (LinearLayout) findViewById(R.id.chans1);
        chans2 = (LinearLayout) findViewById(R.id.chans2);
        chans3 = (LinearLayout) findViewById(R.id.chans3);
        chans4 = (LinearLayout) findViewById(R.id.chans4);
        chans5 = (LinearLayout) findViewById(R.id.chans5);

        chcorrect1 = (ImageView) findViewById(R.id.chc1);
        chcorrect1.setVisibility(View.GONE);
        chcorrect2 = (ImageView) findViewById(R.id.chc2);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3 = (ImageView) findViewById(R.id.chc3);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4 = (ImageView) findViewById(R.id.chc4);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5 = (ImageView) findViewById(R.id.chc5);
        chcorrect5.setVisibility(View.GONE);

        timeline = (ImageView) findViewById(R.id.timeline);

        timeline.setImageResource(R.drawable.timeline_1);
        timelineimg.add(2);
        quizQuestion = (TextView) findViewById(R.id.question);
        quizQuestion.setTypeface(myTypeface);
        optionOne = (Button) findViewById(R.id.answer1);
        optionOne.setTypeface(myTypeface);
        optionTwo = (Button) findViewById(R.id.answer2);
        optionTwo.setTypeface(myTypeface);
        optionThree = (Button) findViewById(R.id.answer3);
        optionThree.setTypeface(myTypeface);
        optionFour = (Button) findViewById(R.id.answer4);
        optionFour.setTypeface(myTypeface);
        optionfifth = (Button) findViewById(R.id.answer5);
        optionfifth.setTypeface(myTypeface);

        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch1.setTypeface(myTypeface);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        ch2.setTypeface(myTypeface);
        ch3 = (CheckBox) findViewById(R.id.ch3);
        ch3.setTypeface(myTypeface);
        ch4 = (CheckBox) findViewById(R.id.ch4);
        ch4.setTypeface(myTypeface);
        ch5 = (CheckBox) findViewById(R.id.ch5);
        ch5.setTypeface(myTypeface);

        tr1 = (ImageView) findViewById(R.id.tr1);
        tr1.setVisibility(View.GONE);

        //tr1.setTypeface(myTypeface);
        fal = (ImageView) findViewById(R.id.fa1);
        fal.setVisibility(View.GONE);
        // fal.setTypeface(myTypeface);
        yes = (ImageView) findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        //yes.setTypeface(myTypeface);
        no = (ImageView) findViewById(R.id.no);
        no.setVisibility(View.GONE);
        // no.setTypeface(myTypeface);

        fill1 = (EditText) filltheblank.findViewById(R.id.blanks1);
        fill1.setTypeface(myTypeface);
        fill2 = (EditText) filltheblank.findViewById(R.id.blanks2);
        fill2.setTypeface(myTypeface);
        fill3 = (EditText) filltheblank.findViewById(R.id.blanks3);
        fill3.setTypeface(myTypeface);
        fill4 = (EditText) filltheblank.findViewById(R.id.blanks4);
        fill4.setTypeface(myTypeface);
        fill5 = (EditText) filltheblank.findViewById(R.id.blanks5);
        fill5.setTypeface(myTypeface);

        essay = (EditText) findViewById(R.id.essay);
        essay.setTypeface(myTypeface);


        questioncontent = (ImageView) findViewById(R.id.image1);

        op1 = (TextView) findViewById(R.id.opt1);
        op1.setTypeface(myTypeface);
        op2 = (TextView) findViewById(R.id.opt2);
        op2.setTypeface(myTypeface);
        op3 = (TextView) findViewById(R.id.opt3);
        op3.setTypeface(myTypeface);
        op4 = (TextView) findViewById(R.id.opt4);
        op4.setTypeface(myTypeface);
        op5 = (TextView) findViewById(R.id.opt5);
        op5.setTypeface(myTypeface);

        spinner1 = (Spinner) findViewById(R.id.sp1);
        spinner2 = (Spinner) findViewById(R.id.sp2);
        spinner3 = (Spinner) findViewById(R.id.sp3);
        spinner4 = (Spinner) findViewById(R.id.sp4);
        spinner5 = (Spinner) findViewById(R.id.sp5);
        List<String> optrin1 = new ArrayList<String>();
        optrin1.add("Select one");
        System.out.println("Option to wright answer " + optrin1);
        ArrayAdapter<String> dataAdapte = new ArrayAdapter<String>(QuizActivity.this,
                android.R.layout.simple_spinner_item, optrin1);
        dataAdapte.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(dataAdapte);
        spinner2.setAdapter(dataAdapte);
        spinner3.setAdapter(dataAdapte);
        spinner4.setAdapter(dataAdapte);
        spinner5.setAdapter(dataAdapte);

        spinneran1 = spinner1.getSelectedItem().toString();
        spinneran2 = spinner2.getSelectedItem().toString();
        spinneran3 = spinner3.getSelectedItem().toString();
        spinneran4 = spinner4.getSelectedItem().toString();
        spinneran5 = spinner5.getSelectedItem().toString();


        pause = (ImageButton) findViewById(R.id.pause);


        mp = new MediaPlayer();

        myCounter = (TextView) findViewById(R.id.time);
        myCounter.setTypeface(myTypeface);
        duration = durationtime;
        Log.d("durationtime", "durationtime" + durationtime);
        String[] separated = duration.split(":");
        int hours = Integer.valueOf(separated[0]);
        hours = hours * 60 * 60;
        int mins = Integer.valueOf(separated[1]);
        int minss = mins * 60 * 1000;
        int sec = Integer.valueOf(separated[2]);
        countdowntime = hours + minss + sec;
        Log.d("Countdown", "long value timer" + countdowntime);

        textViewShowTime = (TextView) findViewById(R.id.timecont);
        getReferenceOfViews();                         // get all views
        setActionListeners();

        totalTimeCountInMilliseconds = 60 * 1000;      // time count for 3 minutes = 180 seconds
        timeBlinkInMilliseconds = 15 * 1000;

        if (isNetworkAvailable()) {

            //handler.post(timedTask);
            AsyncJsonObject asyncObject = new AsyncJsonObject();
            asyncObject.execute("");
            new LogoutTask(QuizActivity.this).execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    QuizActivity.this);
            // set dialog message
            alertDialogBuilder
                    .setMessage(QuizActivity.this.getString(R.string.network))
                    .setCancelable(false)
                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Progresbar.setVisibility(View.GONE);
                            nextButton.setVisibility(View.GONE);
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            dialog.cancel();
                            Intent i = new Intent(QuizActivity.this, MainActivity.class);
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


        nextButton = (Button) findViewById(R.id.nextBtn);


        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Question type id onclick" + questiontypeid);

                Log.d(Questionactivity, "value next" + currentQuizQuestion);
                if (currentQuizQuestion == 14) {
                    //lastresponce();
                    //currentQuizQuestion++;
                    if (questiontypeid == 1) {
                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        System.out.println("time UP value" + Timeup);
                        System.out.println("Selected answer question type=12  " + answerSelected);
                        if (answerSelected == 0) {
                            // correct answer
                            //nextQuestiontime();
                            Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Timeup = 0;
                            int answerSelected1 = answerSelected - 1;
                            System.out.println("option array value  " + optionidch);
                            String selected = optionidch.get(answerSelected1);
                            System.out.println("JSON countdowntime" + countdowntime);
                            System.out.println("JSON time" + time);
                            long jsontime = (countdowntime / 1000) - time;

                            System.out.println("JSON time spend" + jsontime);

                            try {
                                request = new JSONObject();
                                request.put("QuestionId", qun);
                                request.put("question_typeid", String.valueOf(questiontypeid));
                                request.put("matchingans", Matchingans);
                                request.put("correctAnswer", answer);
                                request.put("SelectedAnswer", selected);
                                request.put("Timespend", String.valueOf(jsontime));


                                System.out.println("JSON responce" + request);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            result.put(request);
                            System.out.println("JSON responce choose the best answer" + result);
                            answerSelected = 0;
                            currentQuizQuestion++;

                        }
                    }
                    if (questiontypeid == 3) {

                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        if (ch1.isChecked()) {
                            checkSelected.add(0);

                        }
                        if (ch2.isChecked()) {
                            checkSelected.add(1);

                        }
                        if (ch3.isChecked()) {
                            checkSelected.add(2);

                        }
                        if (ch4.isChecked()) {
                            checkSelected.add(3);
                        }
                        if (ch5.isChecked()) {
                            checkSelected.add(4);

                        }
                        Log.d(Questionactivity, "userSelection checkbox if condition" + checkSelected);
                        if ((!ch1.isChecked()) && (!ch2.isChecked()) && (!ch3.isChecked()) && (!ch4.isChecked()) && (!ch5.isChecked())) {
                            Toast.makeText(QuizActivity.this, R.string.checkboxselect, Toast.LENGTH_LONG).show();
                            //nextQuestiontime();
                            return;
                        } else {
                            Timeup = 0;
                            String checkboxselect = null;
                            ArrayList<String> selected = new ArrayList<String>();
                            for (int i = 0; i < checkSelected.size(); i++) {
                                //int valueselect=checkSelected.get(i);
                                selected.add(optionidmr.get(checkSelected.get(i)));
                            }
                            if (selected.size() == 1) {
                                checkboxselect = selected.get(0) + "|";
                            }
                            if (selected.size() == 2) {
                                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|";
                            }
                            if (selected.size() == 3) {
                                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|";
                            }
                            if (selected.size() == 4) {
                                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|";
                            }
                            if (selected.size() == 5) {
                                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|" + selected.get(4) + "|";
                            }
                            System.out.println("Userselected answer" + checkboxselect);
                            System.out.println("JSON countdowntime" + countdowntime);
                            System.out.println("JSON time" + time);
                            long jsontime = (countdowntime / 1000) - time;

                            System.out.println("JSON time spend" + jsontime);

                            try {
                                request = new JSONObject();
                                request.put("QuestionId", qun);
                                request.put("question_typeid", String.valueOf(questiontypeid));
                                request.put("matchingans", Matchingans);
                                request.put("correctAnswer", answer);
                                request.put("SelectedAnswer", checkboxselect);
                                request.put("Timespend", String.valueOf(jsontime));
                                System.out.println("JSON responce" + request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("JSON responce for mrq" + request);
                            result.put(request);
                            optionidmr = new ArrayList<String>();
                            answerSelected = 0;
                            currentQuizQuestion++;

                            return;
                        }
                    }
                    if (questiontypeid == 4) {

                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        String truefalse = null;
                        if ((answerSelectedtrf == 0)) {
                            //nextQuestiontime();
                            Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Timeup = 0;
                            if (answerSelectedtrf == 1) {
                                truefalse = "False";
                            }
                            if (answerSelectedtrf == 2) {
                                truefalse = "True";
                            }

                            System.out.println("JSON countdowntime" + countdowntime);
                            System.out.println("JSON time" + time);
                            long jsontime = (countdowntime / 1000) - time;

                            System.out.println("JSON time spend" + jsontime);

                            try {
                                request = new JSONObject();
                                request.put("QuestionId", qun);
                                request.put("question_typeid", String.valueOf(questiontypeid));
                                request.put("matchingans", Matchingans);
                                request.put("correctAnswer", answer);
                                request.put("SelectedAnswer", truefalse);
                                request.put("Timespend", String.valueOf(jsontime));
                                System.out.println("JSON responce" + request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("JSON responce true or false" + request);
                            result.put(request);
                            answerSelected = 0;
                            answerSelectedtrf = 0;
                            currentQuizQuestion++;
                            return;
                        }
                    }
                    if (questiontypeid == 5) {


                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        String yesrno = null;
                        if ((answerSelectedYrN == 0)) {
                            //nextQuestiontime();
                            Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Timeup = 0;
                            if (answerSelectedYrN == 1) {
                                yesrno = "No";
                            }
                            if (answerSelectedYrN == 2) {
                                yesrno = "Yes";
                            }

                            System.out.println("JSON countdowntime" + countdowntime);
                            System.out.println("JSON time" + time);
                            long jsontime = (countdowntime / 1000) - time;

                            System.out.println("JSON time spend" + jsontime);

                            try {
                                request = new JSONObject();
                                request.put("QuestionId", qun);
                                request.put("question_typeid", String.valueOf(questiontypeid));
                                request.put("matchingans", Matchingans);
                                request.put("correctAnswer", answer);
                                request.put("SelectedAnswer", yesrno);
                                request.put("Timespend", String.valueOf(jsontime));
                                System.out.println("JSON responce" + request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("JSON responce yes or no" + request);
                            result.put(request);
                            answerSelected = 0;
                            answerSelectedYrN = 0;
                            currentQuizQuestion++;

                            return;
                        }

                    }
                    if (questiontypeid == 6) {

                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        essayanswer = essay.getText().toString();
                        Log.d(Questionactivity, "Essay question answer" + essayanswer);

                        if (!isValidEssay(essayanswer)) {
                            return;
                        } else {
                            answerSelected = 0;

                            return;
                        }
                    }
                    if ((questiontypeid == 8)) {

                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        blank1 = fill1.getText().toString().trim();
                        Log.d(Questionactivity, "Fill in the blanks1" + blank1);
                        blank2 = fill2.getText().toString().trim();
                        Log.d(Questionactivity, "Fill in the blanks2" + blank2);
                        blank3 = fill3.getText().toString().trim();
                        Log.d(Questionactivity, "Fill in the blanks2" + blank3);
                        blank4 = fill4.getText().toString().trim();
                        Log.d(Questionactivity, "Fill in the blanks2" + blank4);
                        blank5 = fill5.getText().toString().trim();
                        Log.d(Questionactivity, "Fill in the blanks2" + blank5);
                        //if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals(""))
                        if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals("")) {
                            Toast.makeText(QuizActivity.this, R.string.filloption, Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Timeup = 0;
                            String fillansewer = null;
                            if (option == 1) {
                                fillansewer = blank1 + "|";
                            }
                            if (option == 2) {
                                fillansewer = blank1 + "|" + blank2 + "|";
                            }
                            if (option == 3) {
                                fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|";
                            }
                            if (option == 4) {
                                fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|";
                            }
                            if (option == 5) {
                                fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|" + blank5 + "|";
                            }
                            System.out.println("JSON countdowntime" + countdowntime);
                            System.out.println("JSON time" + time);
                            long jsontime = (countdowntime / 1000) - time;

                            System.out.println("JSON time spend" + jsontime);

                            try {
                                request = new JSONObject();
                                request.put("QuestionId", qun);
                                request.put("question_typeid", String.valueOf(questiontypeid));
                                request.put("matchingans", Matchingans);
                                request.put("correctAnswer", answer);
                                request.put("SelectedAnswer", fillansewer);
                                request.put("Timespend", String.valueOf(jsontime));
                                System.out.println("JSON responce" + request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("JSON responce fill in the blank" + request);
                            result.put(request);
                            answerSelected = 0;
                            blank1 = "";
                            blank2 = "";
                            blank3 = "";
                            blank4 = "";
                            blank5 = "";

                            currentQuizQuestion++;
                            return;
                        }
                    }
                    if (questiontypeid == 10) {

                        if (!(mp == null)) {
                            if (mp.isPlaying()) {
                                mp.stop();
                            }
                        }
                        System.out.println("option Count  questiontypeid==10   " + option);

                        spinneran1 = spinner1.getSelectedItem().toString();
                        spinneran2 = spinner2.getSelectedItem().toString();
                        spinneran3 = spinner3.getSelectedItem().toString();
                        spinneran4 = spinner4.getSelectedItem().toString();
                        spinneran5 = spinner5.getSelectedItem().toString();
                        System.out.println("option Count  questiontypeid==10   " + spinneran1);
                        System.out.println("option Count  questiontypeid==10   " + spinneran2);
                        System.out.println("option Count  questiontypeid==10   " + spinneran3);
                        System.out.println("option Count  questiontypeid==10   " + spinneran4);
                        System.out.println("option Count  questiontypeid==10   " + spinneran5);

                        if ((option == 2)) {

                            if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one"))) || (Timeup == 0)) {
                                //nextQuestiontime();
                                Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Timeup = 0;
                                String spinnerselect = spinneran1 + "|" + spinneran2 + "|";
                                System.out.println("JSON countdowntime" + countdowntime);
                                System.out.println("JSON time" + time);
                                long jsontime = (countdowntime / 1000) - time;

                                System.out.println("JSON time spend" + jsontime);

                                try {
                                    request = new JSONObject();
                                    request.put("QuestionId", qun);
                                    request.put("question_typeid", String.valueOf(questiontypeid));
                                    request.put("matchingans", Matchingans);
                                    request.put("SelectedAnswer", spinnerselect);
                                    request.put("Timespend", String.valueOf(jsontime));
                                    System.out.println("JSON responce" + request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("JSON responce Match the following" + request);
                                result.put(request);
                                //System.out.println("JSON responce Match the following" + result);
                                answerSelected = 0;

                                currentQuizQuestion++;
                                return;
                            }

                        } else if (option == 3) {
                            System.out.println("option Count  questiontypeid==10  f  " + option);
                            if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one"))) || (Timeup == 0)) {
                                System.out.println("option Count  questiontypeid==10  o1  " + spinneran1);
                                System.out.println("option Count  questiontypeid==10  o2  " + spinneran2);
                                System.out.println("option Count  questiontypeid==10  o3  " + spinneran3);
                                //nextQuestiontime();
                                Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Timeup = 0;
                                String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|";
                                System.out.println("JSON countdowntime" + countdowntime);
                                System.out.println("JSON time" + time);
                                long jsontime = (countdowntime / 1000) - time;

                                System.out.println("JSON time spend" + jsontime);

                                try {
                                    request = new JSONObject();
                                    request.put("QuestionId", qun);
                                    request.put("question_typeid", String.valueOf(questiontypeid));
                                    request.put("matchingans", Matchingans);
                                    request.put("SelectedAnswer", spinnerselect);
                                    request.put("Timespend", String.valueOf(jsontime));
                                    System.out.println("JSON responce" + request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("JSON responce Match the following" + request);
                                result.put(request);
                                answerSelected = 0;
                                currentQuizQuestion++;

                                return;
                            }
                        } else if (option == 4) {
                            if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one"))) || (Timeup == 0)) {
                                //nextQuestiontime();
                                Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Timeup = 0;
                                String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|";
                                System.out.println("JSON countdowntime" + countdowntime);
                                System.out.println("JSON time" + time);
                                long jsontime = (countdowntime / 1000) - time;

                                System.out.println("JSON time spend" + jsontime);

                                try {
                                    request = new JSONObject();
                                    request.put("QuestionId", qun);
                                    request.put("question_typeid", String.valueOf(questiontypeid));
                                    request.put("matchingans", Matchingans);
                                    request.put("SelectedAnswer", spinnerselect);
                                    request.put("Timespend", String.valueOf(jsontime));
                                    System.out.println("JSON responce" + request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("JSON responce Match the following" + request);
                                result.put(request);
                                answerSelected = 0;
                                currentQuizQuestion++;
                                return;
                            }

                        } else if (option == 5) {
                            if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one")) || (spinneran5.equals("Select one"))) || (Timeup == 0)) {
                                //nextQuestiontime();
                                Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Timeup = 0;
                                String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|" + spinneran5 + "|";
                                System.out.println("JSON countdowntime" + countdowntime);
                                System.out.println("JSON time" + time);
                                long jsontime = (countdowntime / 1000) - time;

                                System.out.println("JSON time spend" + jsontime);

                                try {
                                    request = new JSONObject();
                                    request.put("QuestionId", qun);
                                    request.put("question_typeid", String.valueOf(questiontypeid));
                                    request.put("matchingans", Matchingans);
                                    request.put("SelectedAnswer", spinnerselect);
                                    request.put("Timespend", String.valueOf(jsontime));
                                    System.out.println("JSON responce" + request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("JSON responce Match the following" + request);
                                result.put(request);
                                answerSelected = 0;
                                currentQuizQuestion++;
                                return;
                            }
                        }
                    }

                }
                Log.d(Questionactivity, "value next after adding" + currentQuizQuestion);
                if (currentQuizQuestion >= quizCount) {

                    if (mCountDown != null) {
                        mCountDown.cancel();
                    }
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
                    //TextView myMsg = new TextView(QuizActivity.this);
                    //String msg=R.string.youcompleted+topic+R.string.check;

                    // myMsg.setText(String.valueOf(R.string.youcompleted)+topic+String.valueOf(R.string.check));
                    alertDialogBuilder.setTitle(R.string.end);
                    alertDialogBuilder
                            .setMessage(QuizActivity.this.getString(R.string.youcompleted) + " " + topic + " " + QuizActivity.this.getString(R.string.check))
                            .setCancelable(false)
                            .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity

                                    //Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();
                                    if (isNetworkAvailable()) {
                                        Date now = new Date();
                                        DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                        df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                                        examendtime = df.format(now);
                                        //handler.post(timedTask);
                                        //new LogoutTask(QuizActivity.this).execute();
                                        /*int logou=logout;
                                        System.out.println("Login task form" + logout);
                                        if(logou==0){
                                            new LogoutTask(QuizActivity.this).execute();
                                        }else if(logou==2){
                                            new LogoutTask(QuizActivity.this).execute();
                                        }else{*/
                                        new UploadASyncTask().execute();
                                        //  }


                                    } else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                QuizActivity.this);
                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage(QuizActivity.this.getString(R.string.network))
                                                .setCancelable(false)
                                                .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //nextButton.setVisibility(nextButton.GONE);
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
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                } else {
                    nextQuestion();
                }

            }
        });

        /*// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) QuizActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    private void lastresponce() {
        if (questiontypeid == 1) {
            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }

            String selected;
            if (answerSelected == 0) {
                selected = " ";
            } else {
                int answerSelected1 = answerSelected - 1;
                selected = optionidch.get(answerSelected1);
            }
            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", selected);
                request.put("Timespend", String.valueOf(jsontime));


                System.out.println("JSON responce" + request);
                result.put(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("JSON responce" + request);
            answerSelected = 0;
            optionidch = new ArrayList<String>();


        }
        if (questiontypeid == 3) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            if (ch1.isChecked()) {
                checkSelected.add(0);

            }
            if (ch2.isChecked()) {
                checkSelected.add(1);

            }
            if (ch3.isChecked()) {
                checkSelected.add(2);

            }
            if (ch4.isChecked()) {
                checkSelected.add(3);
            }
            if (ch5.isChecked()) {
                checkSelected.add(4);

            }
            Log.d(Questionactivity, "userSelection checkbox if condition" + checkSelected);

            String checkboxselect = null;
            ArrayList<String> selected = new ArrayList<String>();
            if (checkSelected.size() == 0) {
                if (optionidmr.size() == 1) {
                    checkboxselect = "|";
                }
                if (optionidmr.size() == 2) {
                    checkboxselect = "||";
                }
                if (optionidmr.size() == 3) {
                    checkboxselect = "|||";
                }
                if (optionidmr.size() == 4) {
                    checkboxselect = "||||";
                }
                if (optionidmr.size() == 5) {
                    checkboxselect = "|||||";
                }
            } else {
                for (int i = 0; i < checkSelected.size(); i++) {
                    //int valueselect=checkSelected.get(i);
                    selected.add(optionidmr.get(checkSelected.get(i)));
                }
            }

            if (selected.size() == 1) {
                checkboxselect = selected.get(0) + "|";
            }
            if (selected.size() == 2) {
                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|";
            }
            if (selected.size() == 3) {
                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|";
            }
            if (selected.size() == 4) {
                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|";
            }
            if (selected.size() == 5) {
                checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|" + selected.get(4) + "|";
            }
            System.out.println("Userselected answer" + checkboxselect);
            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", checkboxselect);
                request.put("Timespend", String.valueOf(jsontime));
                System.out.println("JSON responce" + request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("JSON responce for mrq" + request);
            result.put(request);
            optionidmr = new ArrayList<String>();
            answerSelected = 0;


            return;

        }
        if (questiontypeid == 4) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            String truefalse = null;
            if (answerSelectedtrf == 0) {
                truefalse = " ";
            } else {
                if (answerSelectedtrf == 1) {
                    truefalse = "False";
                }
                if (answerSelectedtrf == 2) {
                    truefalse = "True";
                }
            }

            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", truefalse);
                request.put("Timespend", String.valueOf(jsontime));
                System.out.println("JSON responce" + request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("JSON responce true or false" + request);
            result.put(request);
            answerSelected = 0;
            answerSelectedtrf = 0;

            return;

        }
        if (questiontypeid == 5) {


            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            String yesrno = null;
            if (answerSelectedYrN == 0) {
                yesrno = " ";
            } else {
                if (answerSelectedYrN == 1) {
                    yesrno = "No";
                }
                if (answerSelectedYrN == 2) {
                    yesrno = "Yes";
                }
            }
            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", yesrno);
                request.put("Timespend", String.valueOf(jsontime));
                System.out.println("JSON responce" + request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("JSON responce yes or no" + request);
            result.put(request);
            answerSelected = 0;
            answerSelectedYrN = 0;

            return;


        }
        if (questiontypeid == 6) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            essayanswer = essay.getText().toString();
            Log.d(Questionactivity, "Essay question answer" + essayanswer);

            if (!isValidEssay(essayanswer)) {
                return;
            } else {
                answerSelected = 0;

                return;
            }
        }
        if (questiontypeid == 8) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            blank1 = fill1.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks1" + blank1);
            blank2 = fill2.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank2);
            blank3 = fill3.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank3);
            blank4 = fill4.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank4);
            blank5 = fill5.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank5);
            //if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals(""))

            String fillansewer = null;

            if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals("")) {
                if (option == 1) {
                    fillansewer = "|";
                }
                if (option == 2) {
                    fillansewer = "||";
                }
                if (option == 3) {
                    fillansewer = "|||";
                }
                if (option == 4) {
                    fillansewer = "||||";
                }
                if (option == 5) {
                    fillansewer = "|||||";
                }
            } else {

                if (option == 1) {
                    fillansewer = blank1 + "|";
                }
                if (option == 2) {
                    fillansewer = blank1 + "|" + blank2 + "|";
                }
                if (option == 3) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|";
                }
                if (option == 4) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|";
                }
                if (option == 5) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|" + blank5 + "|";
                }
            }
            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", fillansewer);
                request.put("Timespend", String.valueOf(jsontime));
                System.out.println("JSON responce" + request);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("JSON responce fill in the blank" + request);
            result.put(request);
            answerSelected = 0;
            blank1 = "";
            blank2 = "";
            blank3 = "";
            blank4 = "";
            blank5 = "";

            return;

        }
        if (questiontypeid == 10) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            System.out.println("option Count  questiontypeid==10   " + option);

            spinneran1 = spinner1.getSelectedItem().toString();
            spinneran2 = spinner2.getSelectedItem().toString();
            spinneran3 = spinner3.getSelectedItem().toString();
            spinneran4 = spinner4.getSelectedItem().toString();
            spinneran5 = spinner5.getSelectedItem().toString();
            System.out.println("option Count  questiontypeid==10   " + spinneran1);
            System.out.println("option Count  questiontypeid==10   " + spinneran2);
            System.out.println("option Count  questiontypeid==10   " + spinneran3);
            System.out.println("option Count  questiontypeid==10   " + spinneran4);
            System.out.println("option Count  questiontypeid==10   " + spinneran5);

            if ((option == 2)) {
                String spinnerselect;
                if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one"))) {
                    spinnerselect = "||";

                } else {
                    spinnerselect = spinneran1 + "|" + spinneran2 + "|";
                }

                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("SelectedAnswer", spinnerselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce Match the following" + request);
                result.put(request);
                answerSelected = 0;

                return;


            } else if (option == 3) {
                String spinnerselect;
                System.out.println("option Count  questiontypeid==10  f  " + option);

                if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one"))) {
                    spinnerselect = "|||";
                } else {
                    spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|";
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("SelectedAnswer", spinnerselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce Match the following" + request);
                result.put(request);
                answerSelected = 0;

                return;

            } else if (option == 4) {

                String spinnerselect;
                if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one"))) {
                    spinnerselect = "||||";
                } else {
                    spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|";
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("SelectedAnswer", spinnerselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce Match the following" + request);
                result.put(request);
                answerSelected = 0;

                return;


            } else if (option == 5) {
                String spinnerselect;
                if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one")) || (spinneran5.equals("Select one"))) {
                    spinnerselect = "|||||";
                } else {

                    spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|" + spinneran5 + "|";
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("SelectedAnswer", spinnerselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce Match the following" + request);
                result.put(request);
                answerSelected = 0;

                return;

            }
        }


        return;
    }

    private void nextQuestion() {

        if (questiontypeid == 1) {
            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            System.out.println("time UP value" + Timeup);
            System.out.println("Selected answer question type=1  " + answerSelected);
            if (answerSelected == 0) {

                // correct answer
                //nextQuestiontime();
                Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (questiontypeid == 3) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            if (ch1.isChecked()) {
                checkSelected.add(0);

            }
            if (ch2.isChecked()) {
                checkSelected.add(1);

            }
            if (ch3.isChecked()) {
                checkSelected.add(2);

            }
            if (ch4.isChecked()) {
                checkSelected.add(3);
            }
            if (ch5.isChecked()) {
                checkSelected.add(4);

            }
            Log.d(Questionactivity, "userSelection checkbox if condition" + checkSelected);
            if ((!ch1.isChecked()) && (!ch2.isChecked()) && (!ch3.isChecked()) && (!ch4.isChecked()) && (!ch5.isChecked())) {
                //nextQuestiontime();
                Toast.makeText(QuizActivity.this, R.string.checkboxselect, Toast.LENGTH_LONG).show();
                return;
            } else {
                Timeup = 0;
                String checkboxselect = null;
                ArrayList<String> selected = new ArrayList<String>();
                for (int i = 0; i < checkSelected.size(); i++) {
                    //int valueselect=checkSelected.get(i);
                    selected.add(optionidmr.get(checkSelected.get(i)));
                }
                if (selected.size() == 1) {
                    checkboxselect = selected.get(0) + "|";
                }
                if (selected.size() == 2) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|";
                }
                if (selected.size() == 3) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|";
                }
                if (selected.size() == 4) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|";
                }
                if (selected.size() == 5) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|" + selected.get(4) + "|";
                }
                System.out.println("Userselected answer" + checkboxselect);
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", checkboxselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce for mrq" + request);
                result.put(request);
                optionidmr = new ArrayList<String>();
                answerSelected = 0;

                currentQuizQuestion++;

                questioncreatinge();
                return;
            }
        }
        if (questiontypeid == 4) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            String truefalse = null;
            if ((answerSelectedtrf == 0)) {
                //nextQuestiontime();
                Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                return;
            } else {
                Timeup = 0;
                if (answerSelectedtrf == 1) {
                    truefalse = "False";
                }
                if (answerSelectedtrf == 2) {
                    truefalse = "True";
                }

                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", truefalse);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce true or false" + request);
                result.put(request);
                answerSelected = 0;
                answerSelectedtrf = 0;

                currentQuizQuestion++;

                questioncreatinge();
                return;
            }
        }
        if (questiontypeid == 5) {


            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            String yesrno = null;
            if ((answerSelectedYrN == 0)) {
                //nextQuestiontime();
                Toast.makeText(QuizActivity.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                return;
            } else {
                Timeup = 0;
                if (answerSelectedYrN == 1) {
                    yesrno = "No";
                }
                if (answerSelectedYrN == 2) {
                    yesrno = "Yes";
                }

                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", yesrno);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce yes or no" + request);
                result.put(request);
                answerSelected = 0;
                answerSelectedYrN = 0;

                currentQuizQuestion++;

                questioncreatinge();
                return;
            }

        }
        if (questiontypeid == 6) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            essayanswer = essay.getText().toString();
            Log.d(Questionactivity, "Essay question answer" + essayanswer);

            if (!isValidEssay(essayanswer)) {
                return;
            } else {
                answerSelected = 0;
                questioncreatinge();
                return;
            }
        }
        if ((questiontypeid == 8)) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            blank1 = fill1.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks1" + blank1);
            blank2 = fill2.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank2);
            blank3 = fill3.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank3);
            blank4 = fill4.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank4);
            blank5 = fill5.getText().toString().trim();
            Log.d(Questionactivity, "Fill in the blanks2" + blank5);
            //if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals(""))
            if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals("")) {
                //nextQuestiontime();
                Toast.makeText(QuizActivity.this, R.string.filloption, Toast.LENGTH_LONG).show();
                return;
            } else {
                Timeup = 0;
                String fillansewer = null;
                if (option == 1) {
                    fillansewer = blank1 + "|";
                }
                if (option == 2) {
                    fillansewer = blank1 + "|" + blank2 + "|";
                }
                if (option == 3) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|";
                }
                if (option == 4) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|";
                }
                if (option == 5) {
                    fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|" + blank5 + "|";
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", fillansewer);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce fill in the blank" + request);
                result.put(request);
                answerSelected = 0;
                blank1 = "";
                blank2 = "";
                blank3 = "";
                blank4 = "";
                blank5 = "";

                currentQuizQuestion++;

                questioncreatinge();
                return;
            }
        }
        if (questiontypeid == 10) {

            if (!(mp == null)) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
            }
            System.out.println("option Count  questiontypeid==10   " + option);

            spinneran1 = spinner1.getSelectedItem().toString();
            spinneran2 = spinner2.getSelectedItem().toString();
            spinneran3 = spinner3.getSelectedItem().toString();
            spinneran4 = spinner4.getSelectedItem().toString();
            spinneran5 = spinner5.getSelectedItem().toString();
            System.out.println("option Count  questiontypeid==10   " + spinneran1);
            System.out.println("option Count  questiontypeid==10   " + spinneran2);
            System.out.println("option Count  questiontypeid==10   " + spinneran3);
            System.out.println("option Count  questiontypeid==10   " + spinneran4);
            System.out.println("option Count  questiontypeid==10   " + spinneran5);

            if ((option == 2)) {

                if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one"))) || (Timeup == 0)) {
                    //nextQuestiontime();
                    Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Timeup = 0;
                    String spinnerselect = spinneran1 + "|" + spinneran2 + "|";
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    //System.out.println("JSON responce Match the following" + result);
                    answerSelected = 0;

                    currentQuizQuestion++;

                    questioncreatinge();
                    return;
                }

            } else if (option == 3) {
                System.out.println("option Count  questiontypeid==10  f  " + option);
                if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one"))) || (Timeup == 0)) {
                    System.out.println("option Count  questiontypeid==10  o1  " + spinneran1);
                    System.out.println("option Count  questiontypeid==10  o2  " + spinneran2);
                    System.out.println("option Count  questiontypeid==10  o3  " + spinneran3);
                    //nextQuestiontime();
                    Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Timeup = 0;
                    String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|";
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;

                    currentQuizQuestion++;

                    questioncreatinge();
                    return;
                }
            } else if (option == 4) {
                if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one"))) || (Timeup == 0)) {
                    //nextQuestiontime();
                    Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Timeup = 0;
                    String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|";
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;

                    currentQuizQuestion++;

                    questioncreatinge();
                    return;
                }

            } else if (option == 5) {
                if (((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one")) || (spinneran5.equals("Select one"))) || (Timeup == 0)) {
                    //nextQuestiontime();
                    Toast.makeText(QuizActivity.this, R.string.pleaseselcetspiner, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Timeup = 0;
                    String spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|" + spinneran5 + "|";
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;
                    currentQuizQuestion++;
                    questioncreatinge();
                    return;
                }
            }
        } else {
            Timeup = 0;
            int answerSelected1 = answerSelected - 1;
            String selected = optionidch.get(answerSelected1);
            System.out.println("JSON countdowntime" + countdowntime);
            System.out.println("JSON time" + time);
            long jsontime = (countdowntime / 1000) - time;

            System.out.println("JSON time spend" + jsontime);

            try {
                request = new JSONObject();
                request.put("QuestionId", qun);
                request.put("question_typeid", String.valueOf(questiontypeid));
                request.put("matchingans", Matchingans);
                request.put("correctAnswer", answer);
                request.put("SelectedAnswer", selected);
                request.put("Timespend", String.valueOf(jsontime));


                System.out.println("JSON responce" + request);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            result.put(request);
            System.out.println("JSON responce choose the best answer" + result);
            answerSelected = 0;
            optionidch = new ArrayList<String>();

            currentQuizQuestion++;
            questioncreatinge();
        }
        return;
    }


    private void setActionListeners() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        textViewShowTime.setTextAppearance(getApplicationContext(), R.style.normalText);
        totalTimeCountInMilliseconds = 60 * 1000;
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(), R.style.blinkText);
                    // change the style of the textview .. giving a red alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        textViewShowTime.setTextSize(18);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setTextAppearance(getApplicationContext(), R.style.blink);
                        //textViewShowTime.setTextSize(15);
                        textViewShowTime.setVisibility(View.VISIBLE);
                    }

                    blink = !blink;         // toggle the value of blink
                }

                //textViewShowTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
                textViewShowTime.setText(String.format("%02d", seconds % 60) + " " + QuizActivity.this.getString(R.string.sec));
                textViewShowTime.setTypeface(myTypeface);
                String timer = String.format("%02d", seconds);
                int time = Integer.parseInt(timer);
                System.out.println("Time spend" + time);
                // format the textview to show the easily readable format
                if (time == 57) {
                    l15.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 53) {
                    l14.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 49) {
                    l13.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 45) {
                    l12.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 41) {
                    l11.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 37) {
                    l10.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 33) {
                    l9.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 29) {
                    l8.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 25) {
                    l7.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 24) {
                    l6.setBackgroundColor(Color.parseColor("#FF8000"));
                    l5.setBackgroundColor(Color.parseColor("#FF8000"));
                    l4.setBackgroundColor(Color.parseColor("#FF8000"));
                    l3.setBackgroundColor(Color.parseColor("#FF8000"));
                    l2.setBackgroundColor(Color.parseColor("#FF8000"));
                    l1.setBackgroundColor(Color.parseColor("#FF8000"));
                } else if (time == 21) {
                    l6.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 17) {
                    l5.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 15) {
                    l4.setBackgroundColor(Color.parseColor("#FF0000"));
                    l3.setBackgroundColor(Color.parseColor("#FF0000"));
                    l2.setBackgroundColor(Color.parseColor("#FF0000"));
                    l1.setBackgroundColor(Color.parseColor("#FF0000"));
                } else if (time == 13) {
                    l4.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 9) {
                    l3.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 5) {
                    l2.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else if (time == 1) {
                    l1.setBackgroundColor(Color.parseColor("#BDBDBD"));
                }
            }

            @Override
            public void onFinish() {
                Timeup = 1;
                // this function will be called when the timecount is finished
                //textViewShowTime.setText("Time up!");
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                //textViewShowTime.setVisibility(View.VISIBLE);
                nextQuestiontime();

            }

        }.start();
    }

    private void nextQuestiontime() {

        if (currentQuizQuestion == 14) {
            lastresponce();
            currentQuizQuestion++;
        }
        if (currentQuizQuestion >= quizCount) {

            if (mCountDown != null) {
                mCountDown.cancel();
            }
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            //TextView myMsg = new TextView(QuizActivity.this);
            //String msg=R.string.youcompleted+topic+R.string.check;

            // myMsg.setText(String.valueOf(R.string.youcompleted)+topic+String.valueOf(R.string.check));
            alertDialogBuilder.setTitle(R.string.end);
            alertDialogBuilder
                    .setMessage(QuizActivity.this.getString(R.string.youcompleted) + " " + topic + " " + QuizActivity.this.getString(R.string.check))
                    .setCancelable(false)
                    .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (isNetworkAvailable()) {
                                Date now = new Date();
                                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                                examendtime = df.format(now);
                                //handler.post(timedTask);
                                /*new LogoutTask(QuizActivity.this).execute();
                                int logou=logout;
                                System.out.println("Login task form" + logout);
                                if(logou==0){
                                    new LogoutTask(QuizActivity.this).execute();
                                }else if(logou==2){
                                    new LogoutTask(QuizActivity.this).execute();
                                }else{
                                    new UploadASyncTask().execute();
                                }*/
                                ///new LogoutTask(QuizActivity.this).execute();
                                new UploadASyncTask().execute();

                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        QuizActivity.this);
                                // set dialog message
                                alertDialogBuilder
                                        .setMessage(QuizActivity.this.getString(R.string.network))
                                        .setCancelable(false)
                                        .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //nextButton.setVisibility(nextButton.GONE);
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
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        } else {
            if (questiontypeid == 1) {
                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }

                String selected;
                if (answerSelected == 0) {
                    selected = " ";
                } else {
                    int answerSelected1 = answerSelected - 1;
                    selected = optionidch.get(answerSelected1);
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", selected);
                    request.put("Timespend", String.valueOf(jsontime));


                    System.out.println("JSON responce" + request);
                    result.put(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce" + request);


                answerSelected = 0;
                optionidch = new ArrayList<String>();
                currentQuizQuestion++;
                questioncreatinge();
            }
            if (questiontypeid == 3) {

                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                if (ch1.isChecked()) {
                    checkSelected.add(0);

                }
                if (ch2.isChecked()) {
                    checkSelected.add(1);

                }
                if (ch3.isChecked()) {
                    checkSelected.add(2);

                }
                if (ch4.isChecked()) {
                    checkSelected.add(3);
                }
                if (ch5.isChecked()) {
                    checkSelected.add(4);

                }
                Log.d(Questionactivity, "userSelection checkbox if condition" + checkSelected);

                String checkboxselect = null;
                ArrayList<String> selected = new ArrayList<String>();
                if (checkSelected.size() == 0) {
                    if (optionidmr.size() == 1) {
                        checkboxselect = " ";
                    }
                    if (optionidmr.size() == 2) {
                        checkboxselect = " ";
                    }
                    if (optionidmr.size() == 3) {
                        checkboxselect = " ";
                    }
                    if (optionidmr.size() == 4) {
                        checkboxselect = " ";
                    }
                    if (optionidmr.size() == 5) {
                        checkboxselect = " ";
                    }
                } else {
                    for (int i = 0; i < checkSelected.size(); i++) {
                        //int valueselect=checkSelected.get(i);
                        selected.add(optionidmr.get(checkSelected.get(i)));
                    }
                }

                if (selected.size() == 1) {
                    checkboxselect = selected.get(0) + "|";
                }
                if (selected.size() == 2) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|";
                }
                if (selected.size() == 3) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|";
                }
                if (selected.size() == 4) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|";
                }
                if (selected.size() == 5) {
                    checkboxselect = selected.get(0) + "|" + selected.get(1) + "|" + selected.get(2) + "|" + selected.get(3) + "|" + selected.get(4) + "|";
                }
                System.out.println("Userselected answer" + checkboxselect);
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", checkboxselect);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce for mrq" + request);
                result.put(request);
                optionidmr = new ArrayList<String>();
                answerSelected = 0;
                currentQuizQuestion++;
                questioncreatinge();
                return;

            }
            if (questiontypeid == 4) {

                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                String truefalse = null;
                if (answerSelectedtrf == 0) {
                    truefalse = " ";
                } else {
                    if (answerSelectedtrf == 1) {
                        truefalse = "False";
                    }
                    if (answerSelectedtrf == 2) {
                        truefalse = "True";
                    }
                }

                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", truefalse);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce true or false" + request);
                result.put(request);
                answerSelected = 0;
                answerSelectedtrf = 0;
                currentQuizQuestion++;
                questioncreatinge();
                return;

            }
            if (questiontypeid == 5) {


                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                String yesrno = null;
                if (answerSelectedYrN == 0) {
                    yesrno = " ";
                } else {
                    if (answerSelectedYrN == 1) {
                        yesrno = "No";
                    }
                    if (answerSelectedYrN == 2) {
                        yesrno = "Yes";
                    }
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", yesrno);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce yes or no" + request);
                result.put(request);
                answerSelected = 0;
                answerSelectedYrN = 0;
                currentQuizQuestion++;
                questioncreatinge();
                return;


            }
            if (questiontypeid == 6) {

                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                essayanswer = essay.getText().toString();
                Log.d(Questionactivity, "Essay question answer" + essayanswer);

                if (!isValidEssay(essayanswer)) {
                    return;
                } else {
                    answerSelected = 0;
                    questioncreatinge();
                    return;
                }
            }
            if (questiontypeid == 8) {

                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                blank1 = fill1.getText().toString().trim();
                Log.d(Questionactivity, "Fill in the blanks1" + blank1);
                blank2 = fill2.getText().toString().trim();
                Log.d(Questionactivity, "Fill in the blanks2" + blank2);
                blank3 = fill3.getText().toString().trim();
                Log.d(Questionactivity, "Fill in the blanks2" + blank3);
                blank4 = fill4.getText().toString().trim();
                Log.d(Questionactivity, "Fill in the blanks2" + blank4);
                blank5 = fill5.getText().toString().trim();
                Log.d(Questionactivity, "Fill in the blanks2" + blank5);
                //if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals(""))

                String fillansewer = null;

                if (blank1.equals("") && blank2.equals("") && blank3.equals("") && blank4.equals("") && blank5.equals("")) {
                    if (option == 1) {
                        fillansewer = "|";
                    }
                    if (option == 2) {
                        fillansewer = "||";
                    }
                    if (option == 3) {
                        fillansewer = "|||";
                    }
                    if (option == 4) {
                        fillansewer = "||||";
                    }
                    if (option == 5) {
                        fillansewer = "|||||";
                    }
                } else {

                    if (option == 1) {
                        fillansewer = blank1 + "|";
                    }
                    if (option == 2) {
                        fillansewer = blank1 + "|" + blank2 + "|";
                    }
                    if (option == 3) {
                        fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|";
                    }
                    if (option == 4) {
                        fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|";
                    }
                    if (option == 5) {
                        fillansewer = blank1 + "|" + blank2 + "|" + blank3 + "|" + blank4 + "|" + blank5 + "|";
                    }
                }
                System.out.println("JSON countdowntime" + countdowntime);
                System.out.println("JSON time" + time);
                long jsontime = (countdowntime / 1000) - time;

                System.out.println("JSON time spend" + jsontime);

                try {
                    request = new JSONObject();
                    request.put("QuestionId", qun);
                    request.put("question_typeid", String.valueOf(questiontypeid));
                    request.put("matchingans", Matchingans);
                    request.put("correctAnswer", answer);
                    request.put("SelectedAnswer", fillansewer);
                    request.put("Timespend", String.valueOf(jsontime));
                    System.out.println("JSON responce" + request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSON responce fill in the blank" + request);
                result.put(request);
                answerSelected = 0;
                blank1 = "";
                blank2 = "";
                blank3 = "";
                blank4 = "";
                blank5 = "";
                currentQuizQuestion++;
                questioncreatinge();
                return;

            }
            if (questiontypeid == 10) {

                if (!(mp == null)) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                }
                System.out.println("option Count  questiontypeid==10   " + option);

                spinneran1 = spinner1.getSelectedItem().toString();
                spinneran2 = spinner2.getSelectedItem().toString();
                spinneran3 = spinner3.getSelectedItem().toString();
                spinneran4 = spinner4.getSelectedItem().toString();
                spinneran5 = spinner5.getSelectedItem().toString();
                System.out.println("option Count  questiontypeid==10   " + spinneran1);
                System.out.println("option Count  questiontypeid==10   " + spinneran2);
                System.out.println("option Count  questiontypeid==10   " + spinneran3);
                System.out.println("option Count  questiontypeid==10   " + spinneran4);
                System.out.println("option Count  questiontypeid==10   " + spinneran5);

                if ((option == 2)) {
                    String spinnerselect;
                    if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one"))) {
                        spinnerselect = "||";

                    } else {
                        spinnerselect = spinneran1 + "|" + spinneran2 + "|";
                    }

                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;
                    currentQuizQuestion++;
                    questioncreatinge();
                    return;


                } else if (option == 3) {
                    String spinnerselect;
                    System.out.println("option Count  questiontypeid==10  f  " + option);

                    if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one"))) {
                        spinnerselect = "|||";
                    } else {
                        spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|";
                    }
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;
                    currentQuizQuestion++;
                    questioncreatinge();
                    return;

                } else if (option == 4) {

                    String spinnerselect;
                    if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one"))) {
                        spinnerselect = "||||";
                    } else {
                        spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|";
                    }
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;
                    currentQuizQuestion++;
                    questioncreatinge();
                    return;


                } else if (option == 5) {
                    String spinnerselect;
                    if ((spinneran1.equals("Select one")) || (spinneran2.equals("Select one")) || (spinneran3.equals("Select one")) || (spinneran4.equals("Select one")) || (spinneran5.equals("Select one"))) {
                        spinnerselect = "|||||";
                    } else {

                        spinnerselect = spinneran1 + "|" + spinneran2 + "|" + spinneran3 + "|" + spinneran4 + "|" + spinneran5 + "|";
                    }
                    System.out.println("JSON countdowntime" + countdowntime);
                    System.out.println("JSON time" + time);
                    long jsontime = (countdowntime / 1000) - time;

                    System.out.println("JSON time spend" + jsontime);

                    try {
                        request = new JSONObject();
                        request.put("QuestionId", qun);
                        request.put("question_typeid", String.valueOf(questiontypeid));
                        request.put("matchingans", Matchingans);
                        request.put("SelectedAnswer", spinnerselect);
                        request.put("Timespend", String.valueOf(jsontime));
                        System.out.println("JSON responce" + request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JSON responce Match the following" + request);
                    result.put(request);
                    answerSelected = 0;
                    currentQuizQuestion++;
                    questioncreatinge();
                    return;

                }
            }
        }
        return;

    }

    private void getReferenceOfViews() {
        textViewShowTime = (TextView) findViewById(R.id.timecont);
    }

    private void checkanswer() {
        if (questiontypeid == 1) {
            System.out.println("Check answer " + answer);
            System.out.println("Check answer 1" + optionidch.get(0).toString());
            System.out.println("Check answer 2" + optionidch.get(1).toString());

            System.out.println("Check answer 3" + optionidch.get(2).toString());

            System.out.println("Check answer 4" + optionidch.get(3).toString());
            System.out.println("Check answer 5" + optionidch.get(4).toString());


            if (answer.equals(optionidch.get(0).toString())) {
                if (answerSelected == 0) {

                }
                System.out.println("Check answer option1 " + optionidch.get(0));
                optionOne.setBackgroundResource(R.drawable.rounded_cornerseleect);
                optionTwo.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionThree.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionFour.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionfifth.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            }
            if (answer.equals(optionidch.get(1).toString())) {
                System.out.println("Check answer option1 " + optionidch.get(1));
                optionTwo.setBackgroundResource(R.drawable.rounded_cornerseleect);
                optionOne.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionThree.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionFour.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionfifth.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            }
            if (answer.equals(optionidch.get(2).toString())) {
                System.out.println("Check answer option1 " + optionidch.get(2));
                optionOne.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionTwo.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionThree.setBackgroundResource(R.drawable.rounded_cornerseleect);
                optionFour.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionfifth.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            }
            if (answer.equals(optionidch.get(3).toString())) {
                System.out.println("Check answer option1 " + optionidch.get(3));
                optionOne.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionTwo.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionThree.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionFour.setBackgroundResource(R.drawable.rounded_cornerseleect);
                optionfifth.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            }
            if (answer.equals(optionidch.get(4).toString())) {
                System.out.println("Check answer option1 " + optionidch.get(4));
                optionOne.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionTwo.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionThree.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionFour.setBackgroundResource(R.drawable.rounded_cornerseleectred);
                optionfifth.setBackgroundResource(R.drawable.rounded_cornerseleect);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    optionidch = new ArrayList<String>();
                    questioncreatinge();
                }
            }, 6000);

        }


    }


    private void questioncreatinge() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        getReferenceOfViews();                         // get all views
        setActionListeners();
        int userSelection = answerSelected;
        Log.d(Questionactivity, "userSelection button" + userSelection);
        Log.d(Questionactivity, "userSelection checkbox" + checkSelected);

        /*currentQuizQuestion++;

        Log.d(Questionactivity, "value" + currentQuizQuestion);*/
        //qun=String.valueOf(firstQuestion.getId());
        ans.add(userSelection);
        //timeline1.add(firstQuestion.gettimeline());
        System.out.println("Question ID " + qun);
        System.out.println("Selected answer " + ans);
        System.out.println("Selected answer " + ans.get(0));
        //System.out.println("Selected answer " + ans.get(0));

        if (timeline1.size() == 1) {
            if (timelineimg.get(0).equals(timeline1.get(0))) {
                System.out.println("Option to wright answer " + timelineimg.get(1));
                timeline.setImageResource(R.drawable.timeline_2);
                timelineimg.add(3);
            }
        }
        if (timeline1.size() == 2) {
            if (timelineimg.get(1).equals(timeline1.get(1))) {
                System.out.println("Option to wright answer " + timelineimg.get(2));
                timeline.setImageResource(R.drawable.timeline_3);
                timelineimg.add(4);

            }
        }
        if (timeline1.size() == 3) {
            if (timelineimg.get(2).equals(timeline1.get(2))) {
                System.out.println("Option to wright answer " + timelineimg.get(3));
                timeline.setImageResource(R.drawable.timeline_4);
                timelineimg.add(5);
            }
        }
        if (timeline1.size() == 4)
            if (timelineimg.get(3).equals(timeline1.get(3))) {
                System.out.println("Option to wright answer " + timelineimg.get(4));
                timeline.setImageResource(R.drawable.timeline_5);
                timelineimg.add(6);
            }
        if (timeline1.size() == 5) {
            if (timelineimg.get(4).equals(timeline1.get(4))) {
                System.out.println("Option to wright answer " + timelineimg.get(5));
                timeline.setImageResource(R.drawable.timeline_6);
                timelineimg.add(7);
            }
        }
        if (timeline1.size() == 6) {
            if (timelineimg.get(5).equals(timeline1.get(5))) {
                System.out.println("Option to wright answer " + timelineimg.get(6));
                timeline.setImageResource(R.drawable.timeline_7);
                timelineimg.add(8);
            }
        }
        if (timeline1.size() == 7) {
            if (timelineimg.get(6).equals(timeline1.get(6))) {
                System.out.println("Option to wright answer " + timelineimg.get(7));
                timeline.setImageResource(R.drawable.timeline_8);
                timelineimg.add(9);
            }
        }
        if (timeline1.size() == 8) {

            if (timelineimg.get(7).equals(timeline1.get(7))) {
                System.out.println("Option to wright answer " + timelineimg.get(8));
                timeline.setImageResource(R.drawable.timeline_9);
                timelineimg.add(10);
            }
        }
        if (timeline1.size() == 9) {
            if (timelineimg.get(8).equals(timeline1.get(8))) {

                System.out.println("Option to wright answer " + timelineimg.get(9));
                timeline.setImageResource(R.drawable.timeline_10);
                timelineimg.add(11);
            }
        }
        if (timeline1.size() == 10) {

            if (timelineimg.get(9).equals(timeline1.get(9))) {
                System.out.println("Option to wright answer " + timelineimg.get(10));
                timeline.setImageResource(R.drawable.timeline_11);
                timelineimg.add(12);
            }
        }
        if (timeline1.size() == 11) {
            if (timelineimg.get(10).equals(timeline1.get(10))) {

                System.out.println("Option to wright answer " + timelineimg.get(11));
                timeline.setImageResource(R.drawable.timeline_12);
                timelineimg.add(13);
            }
        }
        if (timeline1.size() == 12) {
            if (timelineimg.get(11).equals(timeline1.get(11))) {

                System.out.println("Option to wright answer " + timelineimg.get(12));
                timeline.setImageResource(R.drawable.timeline_13);
                timelineimg.add(14);
            }
        }
        if (timeline1.size() == 13) {
            if (timelineimg.get(12).equals(timeline1.get(12))) {

                System.out.println("Option to wright answer " + timelineimg.get(13));
                timeline.setImageResource(R.drawable.timeline_14);
                timelineimg.add(15);
            }
        }
        if (timeline1.size() == 14) {
            if (timelineimg.get(13).equals(timeline1.get(13))) {

                System.out.println("Option to wright answer " + timelineimg.get(14));
                timeline.setImageResource(R.drawable.timeline_15);
            }
        }
        firstQuestion = parsedObject.get(currentQuizQuestion);
        questiontypeid = firstQuestion.getQuestiontypeid();
        System.out.println("next answer " + answer);
        System.out.println("next question " + questiontypeid);

        switch (questiontypeid) {
            case 1:

                choose.setVisibility(View.VISIBLE);
                checked.setVisibility(View.GONE);
                trf.setVisibility(View.GONE);
                match.setVisibility(View.GONE);
                essaywriting.setVisibility(View.GONE);
                filltheblank.setVisibility(View.GONE);
                yesrno.setVisibility(View.GONE);

                qun = String.valueOf(firstQuestion.getId());
                quizQuestion.setText(firstQuestion.getQuestion());
                option = firstQuestion.getOptioncount();

                System.out.println("option Count pre " + option);
                String content = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content);

                List<String> type = new ArrayList<>();
                type.add("Image");
                type.add("Media");
                System.out.println("Image Question Count Type method " + type.get(0));
                if (content.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type.get(0).equals(content)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type.get(1).equals(content)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                resetbutton();
                if (option == 4) {
                    optionThree.setVisibility(View.VISIBLE);
                    optionFour.setVisibility(View.VISIBLE);
                    optionfifth.setVisibility(View.GONE);
                    chans1.setVisibility(View.VISIBLE);
                    chans2.setVisibility(View.VISIBLE);
                    chans3.setVisibility(View.VISIBLE);
                    chans4.setVisibility(View.VISIBLE);
                    chans5.setVisibility(View.GONE);

                }
                if (option == 3) {
                    optionThree.setVisibility(View.VISIBLE);
                    optionFour.setVisibility(View.GONE);
                    optionfifth.setVisibility(View.GONE);
                    chans1.setVisibility(View.VISIBLE);
                    chans2.setVisibility(View.VISIBLE);
                    chans3.setVisibility(View.VISIBLE);
                    chans4.setVisibility(View.GONE);
                    chans5.setVisibility(View.GONE);
                }
                if (option == 2) {

                    optionThree.setVisibility(View.GONE);
                    optionFour.setVisibility(View.GONE);
                    optionfifth.setVisibility(View.GONE);
                    chans3.setVisibility(View.GONE);
                    chans4.setVisibility(View.GONE);
                    chans5.setVisibility(View.GONE);
                }
                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();

                timeline1.add(firstQuestion.gettimeline());
                System.out.println("Image timeline count " + timeline1);
                String option1 = firstQuestion.getAnswers1();
                optionOne.setText(option1);
                optionidch.add(firstQuestion.getOption1id());
                String option2 = firstQuestion.getAnswers2();
                optionTwo.setText(option2);
                optionidch.add(firstQuestion.getOption2id());
                String option3 = firstQuestion.getAnswers3();
                optionThree.setText(option3);
                optionidch.add(firstQuestion.getOption3id());
                String option4 = firstQuestion.getAnswers4();
                optionFour.setText(option4);
                optionidch.add(firstQuestion.getOption4id());
                String option5 = firstQuestion.getAnswers5();
                optionfifth.setText(option5);
                optionidch.add(firstQuestion.getOption5id());
                System.out.println("Option ids" + optionidch);
                break;

            case 3:
                checked.setVisibility(View.VISIBLE);
                choose.setVisibility(View.GONE);
                trf.setVisibility(View.GONE);
                match.setVisibility(View.GONE);
                essaywriting.setVisibility(View.GONE);
                yesrno.setVisibility(View.GONE);
                filltheblank.setVisibility(View.GONE);

                qun = String.valueOf(firstQuestion.getId());
                quizQuestion.setText(firstQuestion.getQuestion());
                System.out.println("next question switchcase 3 " + questiontypeid);

                option = firstQuestion.getOptioncount();
                String content1 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content1);

                List<String> type1 = new ArrayList<>();
                type1.add("Image");
                type1.add("Media");
                System.out.println("Image Question Count Type method " + type1.get(0));
                if (content1.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type1.get(0).equals(content1)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type1.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type1.get(1).equals(content1)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                System.out.println("option Count pre " + option);
                option = firstQuestion.getOptioncount();
                System.out.println("option Count " + option);
                resetbutton();
                if (option == 4) {
                    ch3.setVisibility(View.VISIBLE);
                    ch4.setVisibility(View.VISIBLE);
                    ch5.setVisibility(View.GONE);
                }
                if (option == 3) {
                    ch3.setVisibility(View.VISIBLE);
                    ch4.setVisibility(View.GONE);
                    ch5.setVisibility(View.GONE);
                }
                if (option == 2) {
                    ch4.setVisibility(View.GONE);
                    ch5.setVisibility(View.GONE);
                    ch3.setVisibility(View.GONE);
                }
                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();
                System.out.println("answer for checkbox " + answer);
                timeline1.add(firstQuestion.gettimeline());
                System.out.println("Image timeline count " + timeline1);
                String che1 = firstQuestion.getAnswers1();
                ch1.setText(che1);
                optionidmr.add(firstQuestion.getOption1id());
                String che2 = firstQuestion.getAnswers2();
                ch2.setText(che2);
                optionidmr.add(firstQuestion.getOption2id());
                String che3 = firstQuestion.getAnswers3();
                ch3.setText(che3);
                optionidmr.add(firstQuestion.getOption3id());
                String che4 = firstQuestion.getAnswers4();
                ch4.setText(che4);
                optionidmr.add(firstQuestion.getOption4id());
                String che5 = firstQuestion.getAnswers5();
                ch5.setText(che5);
                optionidmr.add(firstQuestion.getOption5id());
                System.out.println("OPtion id for mrq " + optionidmr);
                break;
            case 4:
                trf.setVisibility(View.VISIBLE);
                choose.setVisibility(View.GONE);
                checked.setVisibility(View.GONE);
                match.setVisibility(View.GONE);
                essaywriting.setVisibility(View.GONE);
                yesrno.setVisibility(View.GONE);

                qun = String.valueOf(firstQuestion.getId());
                filltheblank.setVisibility(View.GONE);
                System.out.println("next question switchcase 4 " + questiontypeid);

                quizQuestion.setText(firstQuestion.getQuestion());

                option = firstQuestion.getOptioncount();
                System.out.println("option Count pre" + option);
                String content2 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content2);

                List<String> type2 = new ArrayList<>();
                type2.add("Image");
                type2.add("Media");
                System.out.println("Image Question Count Type method " + type2.get(0));
                if (content2.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type2.get(0).equals(content2)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type2.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type2.get(1).equals(content2)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }

                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();
                System.out.println("True are false answer" + answer);
                timeline1.add(firstQuestion.gettimeline());
                System.out.println("Image timeline count " + timeline1);
                resetbutton();
                break;
            case 5:
                trf.setVisibility(View.GONE);
                yesrno.setVisibility(View.VISIBLE);
                choose.setVisibility(View.GONE);
                checked.setVisibility(View.GONE);
                essaywriting.setVisibility(View.GONE);
                filltheblank.setVisibility(View.GONE);
                match.setVisibility(View.GONE);
                System.out.println("next question switchcase 5 " + questiontypeid);

                qun = String.valueOf(firstQuestion.getId());
                quizQuestion.setText(firstQuestion.getQuestion());

                option = firstQuestion.getOptioncount();
                System.out.println("option Count pre" + option);
                String content3 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content3);

                List<String> type3 = new ArrayList<>();
                type3.add("Image");
                type3.add("Media");
                System.out.println("Image Question Count Type method " + type3.get(0));
                if (content3.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type3.get(0).equals(content3)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type3.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type3.get(1).equals(content3)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();

                timeline1.add(firstQuestion.gettimeline());

                resetbutton();

                break;
            case 6:
                trf.setVisibility(View.GONE);
                yesrno.setVisibility(View.GONE);
                choose.setVisibility(View.GONE);
                checked.setVisibility(View.GONE);
                filltheblank.setVisibility(View.GONE);
                match.setVisibility(View.GONE);
                essaywriting.setVisibility(View.VISIBLE);

                qun = String.valueOf(firstQuestion.getId());

                System.out.println("next question switchcase 5 " + questiontypeid);

                quizQuestion.setText(firstQuestion.getQuestion());
                option = firstQuestion.getOptioncount();
                String content4 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content4);

                List<String> type4 = new ArrayList<>();
                type4.add("Image");
                type4.add("Media");
                System.out.println("Image Question Count Type method " + type4.get(0));
                if (content4.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type4.get(0).equals(content4)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type4.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type4.get(1).equals(content4)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                timeline1.add(firstQuestion.gettimeline());

                System.out.println("option Count pre" + option);
                resetbutton();
                String essayanswer = essay.getText().toString();
                System.out.println("essay answer " + essayanswer);
                break;
            case 8:
                yesrno.setVisibility(View.GONE);
                trf.setVisibility(View.GONE);
                choose.setVisibility(View.GONE);
                checked.setVisibility(View.GONE);
                essaywriting.setVisibility(View.GONE);
                filltheblank.setVisibility(View.VISIBLE);
                match.setVisibility(View.GONE);

                System.out.println("next question switchcase4 " + questiontypeid);

                qun = String.valueOf(firstQuestion.getId());
                quizQuestion.setText(firstQuestion.getQuestion());
                option = firstQuestion.getOptioncount();
                System.out.println("option Count pre" + option);
                String content5 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content5);

                List<String> type5 = new ArrayList<>();
                type5.add("Image");
                type5.add("Media");
                System.out.println("Image Question Count Type method " + type5.get(0));
                if (content5.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type5.get(0).equals(content5)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type5.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type5.get(1).equals(content5)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                timeline1.add(firstQuestion.gettimeline());

                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();

                if (option == 4) {
                    fill3.setVisibility(View.VISIBLE);
                    fill4.setVisibility(View.VISIBLE);
                    fill5.setVisibility(View.GONE);
                }
                if (option == 3) {
                    fill3.setVisibility(View.VISIBLE);
                    fill4.setVisibility(View.GONE);
                    fill5.setVisibility(View.GONE);
                }
                if (option == 2) {
                    fill2.setVisibility(View.VISIBLE);
                    fill3.setVisibility(View.GONE);
                    fill4.setVisibility(View.GONE);
                    fill5.setVisibility(View.GONE);
                }
                if (option == 1) {
                    fill2.setVisibility(View.GONE);
                    fill3.setVisibility(View.GONE);
                    fill4.setVisibility(View.GONE);
                    fill5.setVisibility(View.GONE);
                }
                resetbutton();
                break;
            case 10:
                choose.setVisibility(View.GONE);
                checked.setVisibility(View.GONE);
                trf.setVisibility(View.GONE);
                yesrno.setVisibility(View.GONE);
                filltheblank.setVisibility(View.GONE);
                match.setVisibility(View.VISIBLE);
                essaywriting.setVisibility(View.GONE);

                qun = String.valueOf(firstQuestion.getId());
                quizQuestion.setText(firstQuestion.getQuestion());
                questioncontent1 = firstQuestion.getQuestionurl();
                System.out.println("Image URL " + questioncontent1);

                option = firstQuestion.getOptioncount();
                System.out.println("option Count pre " + option);
                String content6 = firstQuestion.getQuestioncontent();
                System.out.println("Question Count Type method " + content6);

                List<String> type6 = new ArrayList<>();
                type6.add("Image");
                type6.add("Media");
                System.out.println("Image Question Count Type method " + type6.get(0));
                if (content6.equals("null")) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.GONE);
                }
                if (type6.get(0).equals(content6)) {
                    mediaplayer.setVisibility(View.GONE);
                    questioncontent.setVisibility(View.VISIBLE);
                    System.out.println("Image Question Count Type method if" + type6.get(0));
                    new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                }
                if (type6.get(1).equals(content6)) {
                    questioncontent.setVisibility(View.GONE);
                    mediaplayer.setVisibility(View.VISIBLE);
                    new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                }
                Matchingans = firstQuestion.getMatchingans();
                answer = firstQuestion.getAnswer();

                timeline1.add(firstQuestion.gettimeline());

                resetbutton();
                if (option == 2) {
                    op3.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                    op4.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    op5.setVisibility(View.GONE);
                    spinner5.setVisibility(View.GONE);
                }
                if (option == 4) {
                    op3.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                    op5.setVisibility(View.GONE);
                    spinner5.setVisibility(View.GONE);
                }
                if (option == 3) {
                    op3.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                    op4.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    op5.setVisibility(View.GONE);
                    spinner5.setVisibility(View.GONE);
                }
                List<String> optrin = new ArrayList<String>();
                optrin.add("Select one");
                JSONArray optinon = firstQuestion.getOptionsformatching();
                try {
                    for (int j = 0; j < optinon.length(); j++) {
                        String valueString = optinon.getString(j);
                        Log.e("json", j + "=" + valueString);
                        optrin.add(valueString);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("Option to wright answer " + optrin);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(QuizActivity.this,
                        android.R.layout.simple_spinner_item, optrin);
                dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
                spinner1.setAdapter(dataAdapter);
                spinner2.setAdapter(dataAdapter);
                spinner3.setAdapter(dataAdapter);
                spinner4.setAdapter(dataAdapter);
                spinner5.setAdapter(dataAdapter);

                String opt1 = firstQuestion.getAnswers1();
                op1.setText(opt1);
                String opt2 = firstQuestion.getAnswers2();
                op2.setText(opt2);
                String opt3 = firstQuestion.getAnswers3();
                op3.setText(opt3);
                String opt4 = firstQuestion.getAnswers4();
                op4.setText(opt4);
                String opt5 = firstQuestion.getAnswers5();
                op5.setText(opt5);

                break;
            default:
        }
        //}
    }

    private boolean isValidEssay(String essayanswer) {
        if ((essayanswer == null) || (essayanswer.length() < 50) || (essayanswer.length() > 2000)) {
            if (essayanswer == null) {
                Toast.makeText(QuizActivity.this, "Please enter your answer.", Toast.LENGTH_LONG).show();
            } else if (essayanswer.length() < 50) {
                Toast.makeText(QuizActivity.this, "Your answer should be in minimum 50 characters.", Toast.LENGTH_LONG).show();
            } else if (essayanswer.length() > 2000) {
                Toast.makeText(QuizActivity.this, "Your answer should be below 2000 characters only.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }


    public void timeUp(Context context) {
        if (mCountDown != null) {
            mCountDown.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        status = 0;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder.setTitle(R.string.timeup);
        alertDialogBuilder
                .setMessage(R.string.timeupresponce)
                .setCancelable(false)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        /*Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                        startActivity(intent);*/
                        if (isNetworkAvailable()) {
                            /*Date now = new Date();
                            DateFormat df = new SimpleDateFormat("HH:mm:ss");
                            df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                            examendtime = df.format(now);*/
                            //handler.post(timedTask);
                            /*new LogoutTask(QuizActivity.this).execute();
                            int logou=logout;
                            System.out.println("Login task form" + logout);
                            if(logou==0){
                                new LogoutTask(QuizActivity.this).execute();
                            }else if(logou==2){
                                new LogoutTask(QuizActivity.this).execute();
                            }else{
                                new UploadASyncTask().execute();
                           / }*/
                            //new LogoutTask(QuizActivity.this).execute();
                            new UploadASyncTask().execute();

                        } else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    QuizActivity.this);
                            // set dialog message
                            alertDialogBuilder
                                    .setMessage(QuizActivity.this.getString(R.string.network))
                                    .setCancelable(false)
                                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //nextButton.setVisibility(nextButton.GONE);
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
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

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


    public void onClick1(View v) {


        answerSelected = 1;
        System.out.println("buttonSelected onclick1" + answerSelected);
        int answerSelected1 = answerSelected - 1;
        //String selected=optionidch.get(answerSelected1);

        chcorrect1.setVisibility(View.VISIBLE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);


    }

    public void onClick2(View v) {

        answerSelected = 2;
        System.out.println("buttonSelected onclick2" + answerSelected);
        int answerSelected1 = answerSelected - 1;
        //String selected=optionidch.get(answerSelected1);

        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.VISIBLE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);


    }

    public void onClick3(View v) {

        answerSelected = 3;
        System.out.println("buttonSelected onclick3" + answerSelected);
        int answerSelected1 = answerSelected - 1;
        //String selected=optionidch.get(answerSelected1);
        //checkanswer();
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.VISIBLE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);


    }

    public void onClick4(View v) {

        answerSelected = 4;
        System.out.println("buttonSelected onclick4" + answerSelected);
        int answerSelected1 = answerSelected - 1;
        //String selected=optionidch.get(answerSelected1);

        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.VISIBLE);
        chcorrect5.setVisibility(View.GONE);


    }

    public void onClick5(View v) {

        answerSelected = 5;


        System.out.println("buttonSelected onclick5" + answerSelected);
        int answerSelected1 = answerSelected - 1;
        //String selected=optionidch.get(answerSelected1);

        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.VISIBLE);


    }

    public void tru(View v) {
        /*tr1.setClickable(false);
        fal.setClickable(false);*/
        tr1.setVisibility(View.VISIBLE);
        fal.setVisibility(View.GONE);
        answerSelectedtrf = 2;
        System.out.println("buttonSelected onclick5" + answerSelected);

    }

    public void fals(View v) {
        /*tr1.setClickable(false);
        fal.setClickable(false);*/
        tr1.setVisibility(View.GONE);
        fal.setVisibility(View.VISIBLE);
        answerSelectedtrf = 1;
        System.out.println("buttonSelected onclick5" + answerSelected);
        /*if(answer.equals("False")) {
            //tr1.setImageResource(R.drawable.correct_button);
            //fal.setImageResource(R.drawable.wrong_selecter);
            fal.setBackgroundResource(R.drawable.rounded_cornerseleect);
        }
        else {
            fal.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            tr1.setBackgroundResource(R.drawable.rounded_cornerseleect);
        }*/

    }

    public void Yes(View v) {
       /* yes.setClickable(false);
        no.setClickable(false);*/
        answerSelectedYrN = 2;
        System.out.println("buttonSelected onclick5" + answerSelected);
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.GONE);
        /*if(answer.equals("Yes")) {
            //yes.setImageResource(R.drawable.image_selecter);
            yes.setBackgroundResource(R.drawable.rounded_cornerseleect);
            //no.setBackgroundResource(R.drawable.wrong_button);
        }
        else {
            yes.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            no.setBackgroundResource(R.drawable.rounded_cornerseleect);
        }*/
    }

    public void No(View v) {
      /*  yes.setClickable(false);
        no.setClickable(false);*/
        answerSelectedYrN = 1;
        System.out.println("buttonSelected onclick5" + answerSelected);
        yes.setVisibility(View.GONE);
        no.setVisibility(View.VISIBLE);
        /*if(answer.equals("No")) {
            //yes.setImageResource(R.drawable.correct_button);
            no.setBackgroundResource(R.drawable.rounded_cornerseleect);
            //no.setImageResource(R.drawable.wrong_selecter);
        }
        else {
            no.setBackgroundResource(R.drawable.rounded_cornerseleectred);
            yes.setBackgroundResource(R.drawable.rounded_cornerseleect);
        }*/
    }

    public void pause(View v) {
        if (!(mp == null))

            if (mp.isPlaying()) {
                pause.setImageResource(R.drawable.play_icon);
                mp.pause();
            } else {
                pause.setImageResource(R.drawable.pause_icon);
                mp.start();
            }

    }


    public void onClick(View v) {

    }

    /*@Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Quiz Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.gasofttech.qib/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Quiz Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.gasofttech.qib/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/


    private class AsyncJsonObject extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN+"gaapi/ApiQuestionRequest?Assid=" + assid + "&userid=" + userid;
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: url", "> " + url);

            Log.d("Response: ", "> " + jsonStr);
            /*String jsonResult = "";

            jsonResult = jsonStr;
            System.out.println("Returned Json object " + jsonResult.toString());*/
            return jsonStr;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(QuizActivity.this, QuizActivity.this.getString(R.string.download), QuizActivity.this.getString(R.string.wait), true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (result != null) {
                Date now = new Date();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                examstartTime = df.format(now);
                System.out.println("Current Time" + examstartTime);
                System.out.println("Resulted Value:post execute " + result);


                System.out.println("countdownTimer  " + countdowntime);
                mCountDown = new CountDownTimer(countdowntime, 1000) {
                    @Override
                    public void onTick(long leftTimeInMilliseconds) {
                        Log.d("seconds leftTimeInMilliseconds", "-----" + leftTimeInMilliseconds);

                        long seconds = leftTimeInMilliseconds / 1000;
                        Log.d("seconds leftTimeInMilliseconds", "1000 -----" + seconds);

                        //myCounter.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
                        myCounter.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
                        String timer = String.format("%02d", seconds);
                        time = Integer.parseInt(timer);
                        Log.d("Convert string to int", "integer value timer" + time);
                    }

                    @Override
                    public void onFinish() {
                        if (mCountDown != null) {
                            mCountDown.cancel();
                        }
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        Toast.makeText(QuizActivity.this, R.string.timeup, Toast.LENGTH_LONG).show();
                        timeUp(QuizActivity.this);
                    }
                }.start();
                parsedObject = returnParsedJsonObject(result);
                System.out.println("Resulted Value:parsedObject" + parsedObject);
                if (parsedObject == null) {
                /*Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);*/
                    return;
                }
                quizCount = parsedObject.size();
                System.out.println("Resulted Value:Question total " + quizCount);
                if (parsedObject.size() == 0) {
                    if (mCountDown != null) {
                        mCountDown.cancel();
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
                    alertDialogBuilder.setTitle(R.string.end);
                    alertDialogBuilder
                            .setMessage(R.string.change)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                /*Toast.makeText(QuizActivity.this,
                        R.string.change,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);*/
                } else {
                    firstQuestion = parsedObject.get(0);

                    questiontypeid = firstQuestion.getQuestiontypeid();
                    qun = String.valueOf(firstQuestion.getId());
                    System.out.println("Resulted Value:Question id post excute " + qun);
                    System.out.println("Resulted Value:post execute " + questiontypeid);
                    switch (questiontypeid) {
                        case 1:
                            //timelineimg.add(2);

                            choose.setVisibility(View.VISIBLE);
                            checked.setVisibility(View.GONE);
                            trf.setVisibility(View.GONE);
                            match.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.GONE);
                            yesrno.setVisibility(View.GONE);

                            quizQuestion.setText(firstQuestion.getQuestion());
                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count post excute " + option);


                            timeline1.add(firstQuestion.gettimeline());

                            String content = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content);
                            List<String> type = new ArrayList<>();
                            type.add("Image");
                            type.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type.get(0));
                            if (type.get(0).equals(content)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type.get(1).equals(content)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }
                            resetbutton();
                            if (option == 4) {
                                optionThree.setVisibility(View.VISIBLE);
                                optionFour.setVisibility(View.VISIBLE);
                                optionfifth.setVisibility(View.GONE);
                                chans1.setVisibility(View.VISIBLE);
                                chans2.setVisibility(View.VISIBLE);
                                chans3.setVisibility(View.VISIBLE);
                                chans4.setVisibility(View.VISIBLE);
                                chans5.setVisibility(View.GONE);
                            }
                            if (option == 3) {
                                optionThree.setVisibility(View.VISIBLE);
                                optionFour.setVisibility(View.GONE);
                                optionfifth.setVisibility(View.GONE);
                                chans1.setVisibility(View.VISIBLE);
                                chans2.setVisibility(View.VISIBLE);
                                chans3.setVisibility(View.VISIBLE);
                                chans4.setVisibility(View.GONE);
                                chans5.setVisibility(View.GONE);
                            }
                            if (option == 2) {
                                optionThree.setVisibility(View.GONE);
                                optionFour.setVisibility(View.GONE);
                                optionfifth.setVisibility(View.GONE);
                                chans3.setVisibility(View.GONE);
                                chans4.setVisibility(View.GONE);
                                chans5.setVisibility(View.GONE);
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            System.out.println("Image Question Timeline " + timeline1);
                            String option1 = firstQuestion.getAnswers1();
                            optionOne.setText(option1);
                            optionidch.add(firstQuestion.getOption1id());
                            String option2 = firstQuestion.getAnswers2();
                            optionTwo.setText(option2);
                            optionidch.add(firstQuestion.getOption2id());
                            String option3 = firstQuestion.getAnswers3();
                            optionThree.setText(option3);
                            optionidch.add(firstQuestion.getOption3id());
                            String option4 = firstQuestion.getAnswers4();
                            optionFour.setText(option4);
                            optionidch.add(firstQuestion.getOption4id());
                            String option5 = firstQuestion.getAnswers5();
                            optionfifth.setText(option5);
                            optionidch.add(firstQuestion.getOption5id());
                            System.out.println("Option ids" + optionidch);

                            currentQuizQuestion = 0;
                            break;

                        case 3:
                            //  timelineimg.add(2);
                            checked.setVisibility(View.VISIBLE);
                            choose.setVisibility(View.GONE);
                            trf.setVisibility(View.GONE);
                            match.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.GONE);
                            yesrno.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.GONE);
                            quizQuestion.setText(firstQuestion.getQuestion());
                            System.out.println("next question switchcase 3  post execute" + questiontypeid);

                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count pre " + option);

                            timeline1.add(firstQuestion.gettimeline());

                            String content1 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content1);
                            List<String> type1 = new ArrayList<>();
                            type1.add("Image");
                            type1.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type1.get(0));
                            if (type1.get(0).equals(content1)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type1.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type1.get(1).equals(content1)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }

                            resetbutton();
                            if (option == 4) {
                                ch4.setVisibility(View.VISIBLE);
                                ch3.setVisibility(View.VISIBLE);
                                ch5.setVisibility(View.GONE);
                            }
                            if (option == 3) {
                                ch3.setVisibility(View.VISIBLE);
                                ch4.setVisibility(View.GONE);
                                ch5.setVisibility(View.GONE);
                            }
                            if (option == 2) {
                                ch4.setVisibility(View.GONE);
                                ch5.setVisibility(View.GONE);
                                ch3.setVisibility(View.GONE);
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            String che1 = firstQuestion.getAnswers1();
                            ch1.setText(che1);
                            String che2 = firstQuestion.getAnswers2();
                            ch2.setText(che2);
                            String che3 = firstQuestion.getAnswers3();
                            ch3.setText(che3);
                            String che4 = firstQuestion.getAnswers4();
                            ch4.setText(che4);
                            String che5 = firstQuestion.getAnswers5();
                            ch5.setText(che5);
                            break;
                        case 4:
                            // timelineimg.add(2);
                            trf.setVisibility(View.VISIBLE);
                            choose.setVisibility(View.GONE);
                            checked.setVisibility(View.GONE);
                            match.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.GONE);
                            yesrno.setVisibility(View.GONE);

                            filltheblank.setVisibility(View.GONE);
                            System.out.println("next question switchcase 4 post execute " + questiontypeid);

                            quizQuestion.setText(firstQuestion.getQuestion());

                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count pre post execute" + option);


                            timeline1.add(firstQuestion.gettimeline());

                            String content2 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content2);
                            List<String> type2 = new ArrayList<>();
                            type2.add("Image");
                            type2.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type2.get(0));
                            if (type2.get(0).equals(content2)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type2.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type2.get(1).equals(content2)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();

                            resetbutton();
                            break;
                        case 5:
                            // timelineimg.add(2);
                            trf.setVisibility(View.GONE);
                            yesrno.setVisibility(View.VISIBLE);
                            choose.setVisibility(View.GONE);
                            checked.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.GONE);
                            match.setVisibility(View.GONE);
                            System.out.println("next question switchcase 5 post execute" + questiontypeid);

                            quizQuestion.setText(firstQuestion.getQuestion());

                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count pre post execute" + option);


                            timeline1.add(firstQuestion.gettimeline());

                            String content3 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content3);
                            List<String> type3 = new ArrayList<>();
                            type3.add("Image");
                            type3.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type3.get(0));
                            if (type3.get(0).equals(content3)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type3.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type3.get(1).equals(content3)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }

                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            resetbutton();

                            break;
                        case 6:
                            //  timelineimg.add(2);

                            trf.setVisibility(View.GONE);
                            yesrno.setVisibility(View.GONE);
                            choose.setVisibility(View.GONE);
                            checked.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.GONE);
                            match.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.VISIBLE);


                            System.out.println("next question switchcase 5 post execute " + questiontypeid);

                            quizQuestion.setText(firstQuestion.getQuestion());
                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count pre post execute" + option);


                            timeline1.add(firstQuestion.gettimeline());

                            String content4 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content4);
                            List<String> type4 = new ArrayList<>();
                            type4.add("Image");
                            type4.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type4.get(0));
                            if (type4.get(0).equals(content4)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type4.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type4.get(1).equals(content4)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            resetbutton();
                            String essayanswer = essay.getText().toString();
                            System.out.println("essay answer post execute " + essayanswer);
                            break;
                        case 8:
                            // timelineimg.add(2);
                            yesrno.setVisibility(View.GONE);
                            trf.setVisibility(View.GONE);
                            choose.setVisibility(View.GONE);
                            checked.setVisibility(View.GONE);
                            essaywriting.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.VISIBLE);
                            match.setVisibility(View.GONE);

                            System.out.println("next question switchcase4 post execute " + questiontypeid);

                            quizQuestion.setText(firstQuestion.getQuestion());
                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count pre post execute" + option);

                            timeline1.add(firstQuestion.gettimeline());

                            String content5 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content5);
                            List<String> type5 = new ArrayList<>();
                            type5.add("Image");
                            type5.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type5.get(0));
                            if (type5.get(0).equals(content5)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type5.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type5.get(1).equals(content5)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            resetbutton();
                            if (option == 4) {
                                fill3.setVisibility(View.VISIBLE);
                                fill4.setVisibility(View.VISIBLE);
                                fill5.setVisibility(View.GONE);
                            }
                            if (option == 3) {
                                fill3.setVisibility(View.VISIBLE);
                                fill4.setVisibility(View.GONE);
                                fill5.setVisibility(View.GONE);
                            }
                            if (option == 2) {
                                fill2.setVisibility(View.VISIBLE);
                                fill3.setVisibility(View.GONE);
                                fill4.setVisibility(View.GONE);
                                fill5.setVisibility(View.GONE);
                            }
                            if (option == 1) {
                                fill2.setVisibility(View.GONE);
                                fill3.setVisibility(View.GONE);
                                fill4.setVisibility(View.GONE);
                                fill5.setVisibility(View.GONE);
                            }
                            break;
                        case 10:

                            choose.setVisibility(View.GONE);
                            checked.setVisibility(View.GONE);
                            trf.setVisibility(View.GONE);
                            yesrno.setVisibility(View.GONE);
                            filltheblank.setVisibility(View.GONE);
                            match.setVisibility(View.VISIBLE);
                            essaywriting.setVisibility(View.GONE);

                            quizQuestion.setText(firstQuestion.getQuestion());
                            questioncontent1 = firstQuestion.getQuestionurl();
                            System.out.println("Image URL post execute" + questioncontent1);
                            //questioncontent.setImageResource(questio);
                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count post execute  " + option);
                            String content6 = firstQuestion.getQuestioncontent();
                            System.out.println("Question Count Type method post excute" + content6);
                            List<String> type6 = new ArrayList<>();
                            type6.add("Image");
                            type6.add("Media");
                            System.out.println("Image Question Count Type method post execute " + type6.get(0));
                            if (type6.get(0).equals(content6)) {
                                mediaplayer.setVisibility(View.GONE);
                                questioncontent.setVisibility(View.VISIBLE);
                                System.out.println("Image Question Count Type method if post execute" + type6.get(0));
                                new DownloadImageTask(questioncontent, QuizActivity.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type6.get(1).equals(content6)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }

                            timeline1.add(firstQuestion.gettimeline());
                            resetbutton();
                            if (option == 2) {

                                op3.setVisibility(View.GONE);
                                spinner3.setVisibility(View.GONE);
                                op4.setVisibility(View.GONE);
                                spinner4.setVisibility(View.GONE);
                                op5.setVisibility(View.GONE);
                                spinner5.setVisibility(View.GONE);
                            }
                            if (option == 4) {
                                op3.setVisibility(View.VISIBLE);
                                spinner3.setVisibility(View.VISIBLE);
                                op4.setVisibility(View.VISIBLE);
                                spinner4.setVisibility(View.VISIBLE);
                                op5.setVisibility(View.GONE);
                                spinner5.setVisibility(View.GONE);
                            }
                            if (option == 3) {
                                op3.setVisibility(View.VISIBLE);
                                spinner3.setVisibility(View.VISIBLE);
                                op4.setVisibility(View.GONE);
                                spinner4.setVisibility(View.GONE);
                                op5.setVisibility(View.GONE);
                                spinner5.setVisibility(View.GONE);
                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            List<String> optrin = new ArrayList<String>();
                            optrin.add("Select one");
                            JSONArray optinon = firstQuestion.getOptionsformatching();
                            try {

                                for (int j = 0; j < optinon.length(); j++) {
                                    String valueString = optinon.getString(j);
                                    Log.e("json", j + "=" + valueString);
                                    optrin.add(valueString);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Option to wright answer post execute" + optrin);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(QuizActivity.this,
                                    android.R.layout.simple_spinner_item, optrin);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
                            spinner1.setAdapter(dataAdapter);
                            spinner2.setAdapter(dataAdapter);
                            spinner3.setAdapter(dataAdapter);
                            spinner4.setAdapter(dataAdapter);
                            spinner5.setAdapter(dataAdapter);

                            String opt1 = firstQuestion.getAnswers1();
                            op1.setText(opt1);
                            String opt2 = firstQuestion.getAnswers2();
                            op2.setText(opt2);
                            String opt3 = firstQuestion.getAnswers3();
                            op3.setText(opt3);
                            String opt4 = firstQuestion.getAnswers4();
                            op4.setText(opt4);
                            String opt5 = firstQuestion.getAnswers5();
                            op5.setText(opt5);

                            break;
                        default:
                    }
                }
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        QuizActivity.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(QuizActivity.this.getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Progresbar.setVisibility(View.GONE);
                                nextButton.setVisibility(View.GONE);
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                dialog.cancel();
                                Intent i = new Intent(QuizActivity.this, MainActivity.class);
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
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }

    private List<QuizWrapper> returnParsedJsonObject(String result) {

        List<QuizWrapper> jsonObject = new ArrayList<QuizWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            System.out.println("Testing the water " + resultObject.toString());
            jsonArray = resultObject.optJSONArray("questionanswerlst");
            Log.d("Response:jsonArray value ", "> " + jsonArray);
            //int code=resultObject.getInt("code");
            if (jsonArray == null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
                alertDialogBuilder.setTitle(R.string.end);
                alertDialogBuilder
                        .setMessage(R.string.change)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                    /*Toast.makeText(QuizActivity.this,
                            R.string.change,
                            Toast.LENGTH_SHORT).show();*/
                    /*Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();*/
            } else if (jsonArray != null) {
                jsonArray = resultObject.optJSONArray("questionanswerlst");
                Log.d("Response:jsonArray value ", "> " + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonChildNode = null;

                    try {

                        jsonChildNode = jsonArray.getJSONObject(i);
                        int id = jsonChildNode.getInt("question_id");
                        Log.d("Response:id ", "> " + id);
                        String question = jsonChildNode.getString("question");
                        Log.d("Response:question ", "> " + question);
                        String questionurl = jsonChildNode.getString("questionURL");
                        Log.d("Response:questionurl ", "> " + questionurl);
                        int questiontypeid = jsonChildNode.getInt("question_typeid");
                        Log.d("Response:questiontypeid ", "> " + questiontypeid);
                        String questioncontent = jsonChildNode.getString("question_Content");
                        Log.d("Response:questioncontent ", "> " + questioncontent);
                        int optioncount = jsonChildNode.getInt("option_count");
                        Log.d("Response:optioncount ", "> " + optioncount);
                        JSONArray optionsformatching = jsonChildNode.getJSONArray("optionsformatching");
                        Log.d("Response:optionsformatching ", "> " + optionsformatching);
                        String option1 = jsonChildNode.getString("option1");
                        Log.d("Response:option1 ", "> " + option1);
                        String option1id = jsonChildNode.getString("option1_id");
                        Log.d("Response:option1id ", "> " + option1id);
                        String option2 = jsonChildNode.getString("option2");
                        Log.d("Response:option2 ", "> " + option2);
                        String option2id = jsonChildNode.getString("option2_id");
                        Log.d("Response:option2id ", "> " + option2id);
                        String option3 = jsonChildNode.getString("option3");
                        Log.d("Response:option3 ", "> " + option3);
                        String option3id = jsonChildNode.getString("option3_id");
                        Log.d("Response:option3id ", "> " + option3id);
                        String option4 = jsonChildNode.getString("option4");
                        Log.d("Response:option4 ", "> " + option4);
                        String option4id = jsonChildNode.getString("option4_id");
                        Log.d("Response:option4id ", "> " + option4id);
                        String option5 = jsonChildNode.getString("option5");
                        Log.d("Response:option5 ", "> " + option5);
                        String option5id = jsonChildNode.getString("option5_id");
                        Log.d("Response:option5id ", "> " + option5id);
                        String answer = jsonChildNode.getString("answer");
                        Log.d("Response:answer ", "> " + answer);
                        //List<String> matchingans = new ArrayList<String>();
                        JSONArray matchingans = jsonChildNode.getJSONArray("matchingans");
                        int timeline = i;
                        //  matchingans.add(matching.getJSONObject(i).getString("name"));
                        Log.d("Response:matchingans ", "> " + matchingans);

                        newItemObject = new QuizWrapper(id, question, questionurl, questiontypeid, questioncontent, optioncount, optionsformatching, option1, option1id, option2, option2id, option3, option3id, option4, option4id, option5, option5id, answer, matchingans, timeline);
                        jsonObject.add(newItemObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    private int gettruefalse(int truefalse) {

        answerSelectedtrf = 0;
        if (truefalse == R.id.tr1) {
            answerSelectedtrf = 1;
        }
        if (truefalse == R.id.fa1) {
            answerSelectedtrf = 0;
        }
        return answerSelectedtrf;
    }

    private class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
        MediaPlayer mp = new MediaPlayer();

        public DownloadFileFromURL(MediaPlayer mp) {
            this.mp = mp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            URL u = null;
            InputStream is = null;
            String audiourl = null;

            try {
                u = new URL(f_url[0]);
                is = u.openStream();
                HttpURLConnection huc = (HttpURLConnection) u.openConnection();//to know the size of video
                int size = huc.getContentLength();

                if (huc != null) {
                    String fileName = "qib.mp3";
                    File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/qib/Audio/");
                    wallpaperDirectory.mkdirs();
                    if (!wallpaperDirectory.exists()) {
                        wallpaperDirectory.mkdirs();
                        System.out.println("Directory created");
                    }
                    File outputFile = new File(wallpaperDirectory, fileName);
                    System.out.println("Video path URL file" + outputFile);

                    audiourl = String.valueOf(outputFile);
                    FileOutputStream fos = new FileOutputStream(outputFile);

                    byte[] buffer = new byte[1024];
                    long total = 0;
                    int len1 = 0;
                    if (is != null) {
                        while ((len1 = is.read(buffer)) > 0) {
                            total += len1;
                            publishProgress((int) ((total * 100) / size));
                            fos.write(buffer, 0, len1);
                        }
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
            }
            return audiourl;
        }

        protected void onPostExecute(String file_url) {
            System.out.println("Video path URL file_url" + file_url);
            /*try {
                mp.setDataSource(file_url);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void resetbutton() {
        /*optionOne.setClickable(true);
        optionTwo.setClickable(true);
        optionThree.setClickable(true);
        optionFour.setClickable(true);
        optionfifth.setClickable(true);*/
        optionOne.setBackgroundResource(R.drawable.rounded_corner);
        optionTwo.setBackgroundResource(R.drawable.rounded_corner);
        optionThree.setBackgroundResource(R.drawable.rounded_corner);
        optionFour.setBackgroundResource(R.drawable.rounded_corner);
        optionfifth.setBackgroundResource(R.drawable.rounded_corner);
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);
        ch1.setChecked(false);
        ch2.setChecked(false);
        ch3.setChecked(false);
        ch4.setChecked(false);
        ch5.setChecked(false);
        tr1.setVisibility(View.GONE);
        fal.setVisibility(View.GONE);
        /*tr1.setBackgroundResource(R.color.textColorPrimary);
        tr1.setImageResource(R.drawable.correct_button);
        tr1.setClickable(true);
        fal.setBackgroundResource(R.color.textColorPrimary);
        fal.setImageResource(R.drawable.wrong_button);
        fal.setClickable(true);*/
        yes.setBackgroundResource(R.color.textColorPrimary);
        yes.setImageResource(R.drawable.tick_icon);
        yes.setClickable(true);
        no.setBackgroundResource(R.color.textColorPrimary);
        no.setImageResource(R.drawable.wrong_button);
        no.setClickable(true);
        fill1.setText("");
        fill2.setText("");
        fill3.setText("");
        fill4.setText("");
        fill5.setText("");
        essay.setText("");
        l1.setBackgroundColor(Color.parseColor("#00BFFF"));
        l2.setBackgroundColor(Color.parseColor("#00BFFF"));
        l3.setBackgroundColor(Color.parseColor("#00BFFF"));
        l4.setBackgroundColor(Color.parseColor("#00BFFF"));
        l5.setBackgroundColor(Color.parseColor("#00BFFF"));
        l6.setBackgroundColor(Color.parseColor("#00BFFF"));
        l7.setBackgroundColor(Color.parseColor("#00BFFF"));
        l8.setBackgroundColor(Color.parseColor("#00BFFF"));
        l9.setBackgroundColor(Color.parseColor("#00BFFF"));
        l10.setBackgroundColor(Color.parseColor("#00BFFF"));
        l11.setBackgroundColor(Color.parseColor("#00BFFF"));
        l12.setBackgroundColor(Color.parseColor("#00BFFF"));
        l13.setBackgroundColor(Color.parseColor("#00BFFF"));
        l14.setBackgroundColor(Color.parseColor("#00BFFF"));
        l15.setBackgroundColor(Color.parseColor("#00BFFF"));

    }

    private class UploadASyncTask extends AsyncTask<JSONObject, Void, String> {

        private ProgressDialog progressDialog;
        HttpResponse response;
        HttpPost post = new HttpPost(urlresponce);
        InputStream inputStream = null;
        String result1 = "";
        int server;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(QuizActivity.this, "L", "Wait....", true);
            pDialog = new ProgressDialog(QuizActivity.this);
            pDialog.setMessage(QuizActivity.this.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(JSONObject... request) {
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
            if (status == 0) {
                status = 0;
            } else {
                status = 1;
            }
            try {
                try {
                    holeresponce = new JSONObject();

                    holeresponce.put("UserID", userid);
                    holeresponce.put("AssessmentID", assid);
                    holeresponce.put("Language", lang);
                    holeresponce.put("starttime", startdate + " " + examstartTime);
                    holeresponce.put("endtime", startdate + " " + examendtime);

                    holeresponce.put("status", status);
                    System.out.println("converting json hole responce send " + holeresponce);
                    holeresponce.put("Answerlist", result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String jsongb = "";
                jsongb = holeresponce.toString();
                StringEntity se = new StringEntity(jsongb);
                System.out.println("converting json hole responce send " + jsongb);
                post.setEntity(se);
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json");
                // se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


                response = client.execute(post);
                server = response.getStatusLine().getStatusCode();
                if (server == 200) {
                    inputStream = response.getEntity().getContent();
                    if (inputStream != null) {
                        result1 = convertInputStreamToString(inputStream);
                    } else {
                        result1 = "Did not work!";
                    }
                } else {
                    result1 = null;
                }

                //Log.d("RESULT", temp);
            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                Log.e("ERROR IN SEVER UPLOAD", e.getMessage());
            }

            return result1;

        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        }

        protected void onPostExecute(String result1) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            if (result1 != null) {
                //response = client.execute(post);
                System.out.println("converting json getting value " + result1);
                try {
                    JSONObject temp1 = new JSONObject(result1);
                    System.out.println("Converting " + temp1);

                    String topicode = temp1.getString("TopicCode");
                    System.out.println("TopicCode " + topicode);
                    String topicName = temp1.getString("TopicName");
                    System.out.println("TopicName " + topicName);

                    int assessmentid = temp1.getInt("AssessmentId");
                    System.out.println("assessmentid " + assessmentid);

                    String assementname = temp1.getString("AssementName");
                    System.out.println("AssementName " + assementname);
                    int totalscore = temp1.getInt("TotalScore");
                    System.out.println("TotalScore " + totalscore);
                    int obtainedscore = temp1.getInt("ObtainedScore");
                    System.out.println("ObtainedScore " + obtainedscore);
                    String accuracy = temp1.getString("accuracy");
                    System.out.println("accuracy " + accuracy);
                    int timespend = temp1.getInt("timespend");
                    System.out.println("timespend " + timespend);
                    int staus = temp1.getInt("Status");
                    System.out.println("status " + staus);
                    int correctans = temp1.getInt("CorrectAns");
                    System.out.println("CorrectAnswer " + correctans);
                    int wrong = temp1.getInt("IncorrectAns");
                    System.out.println("wrong answer " + wrong);
                    if (staus == 1) {
                        // successfully created student
                        Intent i = new Intent(QuizActivity.this, PieChartActivity.class);
                        i.putExtra("score", obtainedscore);
                        i.putExtra("accuracy", accuracy);
                        i.putExtra("totaltime", timespend);
                        i.putExtra("correctans", correctans);
                        i.putExtra("wronganswer", wrong);

                        startActivity(i);
                    } else {
                        Intent i = new Intent(QuizActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        QuizActivity.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(QuizActivity.this.getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Progresbar.setVisibility(View.VISIBLE);
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView questioncontent;
        Context mContext;

        public DownloadImageTask(ImageView questioncontent, Activity activity) {
            this.questioncontent = questioncontent;
            this.mContext = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(QuizActivity.this, "L", "Wait....", true);
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage(mContext.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {

                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            pDialog.dismiss();
            questioncontent.setImageBitmap(result);
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

}
