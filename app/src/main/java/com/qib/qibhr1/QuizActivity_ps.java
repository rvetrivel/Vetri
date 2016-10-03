package com.qib.qibhr1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class QuizActivity_ps extends Activity implements OnClickListener {


    private CheckBox ch1, ch2, ch3, ch4, ch5, checkboxgroup;
    private TextView quizQuestion, myCounter;
    private CountDownTimer mCountDown;
    private TextView op1;
    private TextView op2;
    private TextView op3;
    private TextView op4;
    private TextView op5;
    private JSONArray result = new JSONArray();
    private Button nextButton;
    private LinearLayout mediaplayer, lin1, lin2, lin3, lin4, lin5;
    private JSONObject request = null;
    private int currentQuizQuestion;
    private int quizCount;
    private static final String tag = "SignUp Instruction";
    // private static String url = "http://10.20.30.105:2020/gaapi/ApiQuestionRequest?Assid=20&userid=23";
    private QuizWrapper_ps firstQuestion;
    // private int questiontypeid;
    private List<QuizWrapper_ps> parsedObject;
    private int questiontypeid;
    private String Questionactivity;
    private String Language = null;
    private ImageView questioncontent, chcorrect1, chcorrect2, chcorrect3, chcorrect4, chcorrect5;
    private String questioncontent1;
    private JSONObject holeresponce = null;
    private ArrayList<String> optionidch = new ArrayList<String>();
    private ProgressDialog pDialog;
    private String qun;
    private int status = 0;
    private int ans;
    private static String urlresponce = AppUrl.URL_COMMAN+"gaapi/ApiPsytestGetAns";
    private RelativeLayout layoutLeft, layoutRight;
    private RelativeLayout.LayoutParams relParam;
    // private ViewSwitcher switcher;
    private static final int REFRESH_SCREEN = 1;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private Spinner wright_sp;
    private MediaPlayer mp;
    private int option;
    // private Toolbar toolbar;
    private Typeface myTypeface;
    private ArrayList<Integer> checkSelected = new ArrayList<>();
    private int answerSelected1 = 0;
    private String SelectedAnswer;
    private int buttonSelected;
    private int time;
    private String spinneran1, spinneran2, spinneran3, spinneran4, spinneran5;
    private int answerSelectedtrf = 0;
    private int answerSelectedYrN = 0;
    private EditText fill1, fill2, fill3, fill4, fill5, essay;
    private String blank1, blank2, blank3, blank4, blank5;
    private String answer;
    private String essayanswer;
    private ImageButton play, stop, pause;
    private ArrayList<String> optionid = new ArrayList<String>();
    private JSONArray Matchingans;
    private String duration;
    private long countdowntime;
    private RadioGroup rg;
    private Button c1, c2, c3, c4, c5;
    private String assid, userid, lang, examstarttime, examendtime, examstartdate, examduration;
    private String startdate;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_ps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");


        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        assid = settings.getString("assid", null);
        userid = settings.getString("userid", null);
        lang = settings.getString("lang", null);
        examstartdate = settings.getString("start", null);
        examduration = settings.getString("duration", null);
        Log.d(tag, "assid " + assid);
        Log.d(tag, "userid " + userid);
        Log.d(tag, "Duration " + examduration);

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
        /*if(lang == 1)
        {
        	 Language = "en";
        }
        if(lang == 2)
        {
        	 Language = "ar";

        }
        Log.d(tag, "Lang "  +Language);
        Log.d(tag, "userid "  +userid);
        Log.d(tag, "category "  +category);*/

        quizQuestion = (TextView) findViewById(R.id.question);
        quizQuestion.setTypeface(myTypeface);

        mediaplayer = (LinearLayout) findViewById(R.id.mediaplayer);
        mediaplayer.setVisibility(View.GONE);

        //rg = (RadioGroup) findViewById(R.id.group1);
        lin1 = (LinearLayout) findViewById(R.id.but1ps);
        lin2 = (LinearLayout) findViewById(R.id.but2ps);
        lin3 = (LinearLayout) findViewById(R.id.but3ps);
        lin4 = (LinearLayout) findViewById(R.id.but4ps);
        lin5 = (LinearLayout) findViewById(R.id.but5ps);

        c1 = (Button) findViewById(R.id.answer1);
        c1.setTypeface(myTypeface);
        c2 = (Button) findViewById(R.id.answer2);
        c2.setTypeface(myTypeface);
        c3 = (Button) findViewById(R.id.answer3);
        c3.setTypeface(myTypeface);
        c4 = (Button) findViewById(R.id.answer4);
        c4.setTypeface(myTypeface);
        c5 = (Button) findViewById(R.id.answer5);
        c5.setTypeface(myTypeface);

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

        pause = (ImageButton) findViewById(R.id.pause);
        questioncontent = (ImageView) findViewById(R.id.image1);
        questioncontent.setVisibility(View.GONE);
        mp = new MediaPlayer();

        myCounter = (TextView) findViewById(R.id.time);
        myCounter.setTypeface(myTypeface);
        myCounter.setTextColor(0xFF0000FF);
        duration = examduration;
        String[] separated = duration.split(":");
        int hours = Integer.valueOf(separated[0]);
        hours = hours * 60 * 60;
        int mins = Integer.valueOf(separated[1]);
        int minss = mins * 60 * 1000;
        int sec = Integer.valueOf(separated[2]);
        countdowntime = hours + minss + sec;
        Log.d("Countdown", "long value timer" + countdowntime);

        if (isNetworkAvailable()) {

            //handler.post(timedTask);
            AsyncJsonObject asyncObject = new AsyncJsonObject();
            asyncObject.execute("");
            new LogoutTask(QuizActivity_ps.this).execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    QuizActivity_ps.this);
            // set dialog message
            alertDialogBuilder
                    .setMessage(QuizActivity_ps.this.getString(R.string.network))
                    .setCancelable(false)
                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            nextButton.setVisibility(View.GONE);
                            dialog.cancel();
                            Intent i = new Intent(QuizActivity_ps.this, MainActivity.class);
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
                if (questiontypeid == 1) {
                    System.out.println("Question type id onclick if" + questiontypeid);
                    if (answerSelected1 == 0) {
                        // correct answer
                        Toast.makeText(QuizActivity_ps.this, R.string.pleaseselect, Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        System.out.println("Question type id onclick if" + questiontypeid);
                        System.out.println("selected answer in else" + answerSelected1);
                        int answerSelect = answerSelected1 - 1;
                        String selected = optionid.get(answerSelect);
                        System.out.println("JSON countdowntime" + countdowntime);
                        System.out.println("JSON time" + time);
                        long jsontime = (countdowntime / 1000) - time;

                        System.out.println("JSON time spend" + jsontime);

                        try {
                            request = new JSONObject();
                            request.put("QuestionId", qun);
                            request.put("question_typeid", String.valueOf(questiontypeid));
                            request.put("correctAnswer", answer);
                            request.put("SelectedAnswer", selected);
                            request.put("selectedOption", String.valueOf(answerSelected1));
                            request.put("Timespend", String.valueOf(jsontime));
                            System.out.println("JSON responce" + request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("JSON responce" + request);
                        result.put(request);
                        System.out.println("JSON responce" + result);
                        optionid = new ArrayList<String>();
                        answerSelected1 = 0;
                        questioncreatinge();
                    }

                }

                return;
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) QuizActivity_ps.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    private void questioncreatinge() {
        //  getSelectedAnswer();

       /* c1.setBackgroundResource(R.drawable.rounded_corner);
        c2.setBackgroundResource(R.drawable.rounded_corner);
        c3.setBackgroundResource(R.drawable.rounded_corner);
        c4.setBackgroundResource(R.drawable.rounded_corner);
        c5.setBackgroundResource(R.drawable.rounded_corner);*/
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);
        int userSelection = answerSelected1;
        Log.d(Questionactivity, "userSelection button" + userSelection);
        Log.d(Questionactivity, "userSelection checkbox" + checkSelected);

        currentQuizQuestion++;

        Log.d(Questionactivity, "value" + currentQuizQuestion);
        qun = String.valueOf(firstQuestion.getId());
        //ans.add(String.valueOf(userSelection));

        System.out.println("Question ID " + qun);
        System.out.println("Selected answer " + ans);
        //System.out.println("Selected answer " + ans.get(0));

        String currentQuizQuestion1 = String.valueOf(currentQuizQuestion + 1);

        if (currentQuizQuestion >= quizCount) {
            nextButton.setOnClickListener(new OnClickListener() {


                public void onClick(View v) {
                    if (mCountDown != null) {
                        mCountDown.cancel();
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity_ps.this);
                    alertDialogBuilder.setTitle(R.string.end);
                    alertDialogBuilder
                            .setMessage(QuizActivity_ps.this.getString(R.string.psystring))
                            .setCancelable(false)
                            .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    //Toast.makeText(QuizActivity_ps.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();


                                    //upload.execute(request);
                                    if (isNetworkAvailable()) {
                                        Date now = new Date();
                                        DateFormat df = new SimpleDateFormat("HH:mm:ss");
                                        df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                                        examendtime = df.format(now);
                                        //handler.post(timedTask);
                                        new HttpAsyncTask().execute(AppUrl.URL_COMMAN+"gaapi/ApiPsytestGetAns");
                                    } else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                QuizActivity_ps.this);
                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage(QuizActivity_ps.this.getString(R.string.network))
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
            });
            return;
        } else {
            firstQuestion = parsedObject.get(currentQuizQuestion);
            questiontypeid = firstQuestion.getQuestiontypeid();
            System.out.println("next answer " + answer);
            System.out.println("next question " + questiontypeid);

            mediaplayer.setVisibility(View.GONE);
            questioncontent.setVisibility(View.GONE);

            switch (questiontypeid) {
                case 1:
                    quizQuestion.setText(firstQuestion.getQuestion());
                    option = firstQuestion.getOptioncount();
                    System.out.println("option Count pre " + option);
                    qun = String.valueOf(firstQuestion.getId());
                    String content = firstQuestion.getQuestioncontent();
                    System.out.println("Question Count Type method " + content);
                    List<String> type = new ArrayList<>();
                    type.add("Image");
                    type.add("Media");
                    System.out.println("Image Question Count Type method " + type.get(0));
                    if (type.get(0).equals(content)) {
                        mediaplayer.setVisibility(View.GONE);
                        questioncontent.setVisibility(View.VISIBLE);
                        System.out.println("Image Question Count Type method if" + type.get(0));
                        new DownloadImageTask1(questioncontent, QuizActivity_ps.this).execute(firstQuestion.getQuestionurl());
                    }
                    if (type.get(1).equals(content)) {
                        questioncontent.setVisibility(View.GONE);
                        mediaplayer.setVisibility(View.VISIBLE);
                        new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                    }
                    if (option == 2) {
                        lin3.setVisibility(View.GONE);
                        c3.setVisibility(View.GONE);
                        lin4.setVisibility(View.GONE);
                        c4.setVisibility(View.GONE);
                        lin5.setVisibility(View.GONE);
                        c5.setVisibility(View.GONE);

                    }
                    if (option == 4) {
                        lin3.setVisibility(View.VISIBLE);
                        c3.setVisibility(View.VISIBLE);
                        lin4.setVisibility(View.VISIBLE);
                        c4.setVisibility(View.VISIBLE);

                        lin5.setVisibility(View.GONE);
                        c5.setVisibility(View.GONE);
                    }
                    if (option == 3) {
                        lin3.setVisibility(View.VISIBLE);
                        c3.setVisibility(View.VISIBLE);
                        lin4.setVisibility(View.GONE);
                        c4.setVisibility(View.GONE);
                        lin5.setVisibility(View.GONE);
                        c5.setVisibility(View.GONE);
                    }
                    Matchingans = firstQuestion.getMatchingans();
                    answer = firstQuestion.getAnswer();
                    String option1 = firstQuestion.getAnswers1();
                    c1.setText(option1);
                    optionid.add(firstQuestion.getOption1id());
                    String option2 = firstQuestion.getAnswers2();
                    c2.setText(option2);
                    optionid.add(firstQuestion.getOption2id());
                    String option3 = firstQuestion.getAnswers3();
                    c3.setText(option3);
                    optionid.add(firstQuestion.getOption3id());
                    String option4 = firstQuestion.getAnswers4();
                    c4.setText(option4);
                    optionid.add(firstQuestion.getOption4id());
                    String option5 = firstQuestion.getAnswers5();
                    c5.setText(option5);
                    optionid.add(firstQuestion.getOption5id());
                    System.out.println("Option ids" + optionid);
                    break;
                default:
            }
        }
    }

    public void timeUp(Context context) {
        if (mCountDown != null) {
            mCountDown.cancel();
        }
        status = 0;
        new HttpAsyncTask().execute(AppUrl.URL_COMMAN+"gaapi/ApiPsytestGetAns");
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

    public void pause(View v) {
        if (!(mp == null)) {

            if (mp.isPlaying()) {
                pause.setImageResource(R.drawable.play_icon);
                mp.pause();
            } else {
                pause.setImageResource(R.drawable.pause_icon);
                mp.start();
            }
        }

    }

    @Override
    public void onClick(View v) {

    }

    public void onClick1(View v) {
        answerSelected1 = 1;

        System.out.println("buttonSelected onclick1" + answerSelected1);
        /*c1.setBackgroundResource(R.drawable.rounded_cornerseleect);
        c2.setBackgroundResource(R.drawable.rounded_corner);
        c3.setBackgroundResource(R.drawable.rounded_corner);
        c4.setBackgroundResource(R.drawable.rounded_corner);
        c5.setBackgroundResource(R.drawable.rounded_corner);*/
        chcorrect1.setVisibility(View.VISIBLE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);

    }

    public void onClick2(View v) {
        answerSelected1 = 2;
        System.out.println("buttonSelected onclick2" + answerSelected1);
        /*c1.setBackgroundResource(R.drawable.rounded_corner);
        c2.setBackgroundResource(R.drawable.rounded_cornerseleect);
        c3.setBackgroundResource(R.drawable.rounded_corner);
        c4.setBackgroundResource(R.drawable.rounded_corner);
        c5.setBackgroundResource(R.drawable.rounded_corner);*/

        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.VISIBLE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);
    }

    public void onClick3(View v) {
        answerSelected1 = 3;
        System.out.println("buttonSelected onclick3" + answerSelected1);
        /*c1.setBackgroundResource(R.drawable.rounded_corner);
        c2.setBackgroundResource(R.drawable.rounded_corner);
        c3.setBackgroundResource(R.drawable.rounded_cornerseleect);
        c4.setBackgroundResource(R.drawable.rounded_corner);
        c5.setBackgroundResource(R.drawable.rounded_corner);*/
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.VISIBLE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.GONE);

    }

    public void onClick4(View v) {
        answerSelected1 = 4;
        System.out.println("buttonSelected onclick4" + answerSelected1);
        /*c1.setBackgroundResource(R.drawable.rounded_corner);
        c2.setBackgroundResource(R.drawable.rounded_corner);
        c3.setBackgroundResource(R.drawable.rounded_corner);
        c4.setBackgroundResource(R.drawable.rounded_cornerseleect);
        c5.setBackgroundResource(R.drawable.rounded_corner);*/
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.VISIBLE);
        chcorrect5.setVisibility(View.GONE);

    }

    public void onClick5(View v) {
        answerSelected1 = 5;
        System.out.println("buttonSelected onclick5" + answerSelected1);
        /*c1.setBackgroundResource(R.drawable.rounded_corner);
        c2.setBackgroundResource(R.drawable.rounded_corner);
        c3.setBackgroundResource(R.drawable.rounded_corner);
        c4.setBackgroundResource(R.drawable.rounded_corner);
        c5.setBackgroundResource(R.drawable.rounded_cornerseleect);*/
        chcorrect1.setVisibility(View.GONE);
        chcorrect2.setVisibility(View.GONE);
        chcorrect3.setVisibility(View.GONE);
        chcorrect4.setVisibility(View.GONE);
        chcorrect5.setVisibility(View.VISIBLE);

    }

    private class AsyncJsonObject extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN+"gaapi/ApiQuestionRequest?Assid=" + assid + "&userid=" + userid;
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
            //String jsonResult = "";


            return jsonStr;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(QuizActivity_ps.this, QuizActivity_ps.this.getString(R.string.download), QuizActivity_ps.this.getString(R.string.wait), true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result != null) {
                Date now = new Date();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                examstarttime = df.format(now);

                mCountDown = new CountDownTimer(countdowntime, 1000) {
                    @Override
                    public void onTick(long leftTimeInMilliseconds) {
                        Log.d("seconds leftTimeInMilliseconds", "-----" + leftTimeInMilliseconds);

                        long seconds = leftTimeInMilliseconds / 1000;
                        Log.d("seconds leftTimeInMilliseconds", "1000 -----" + seconds);

                        myCounter.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
                        String timer = String.format("%02d", seconds);
                        time = Integer.parseInt(timer);
                        Log.d("Convert string to int", "integer value timer" + time);
                    }

                    @Override
                    public void onFinish() {

                    }

               /* @Override
                public void onFinish() {
                    if (mCountDown != null) {
                        mCountDown.cancel();
                    }
                    Toast.makeText(QuizActivity_ps.this, "Time Up!", Toast.LENGTH_LONG).show();
                    timeUp(QuizActivity_ps.this);
                    finish();
                }*/
                }.start();
                System.out.println("Resulted Value:post execute " + result);

                parsedObject = returnParsedJsonObject(result);

                if (parsedObject == null) {
                    return;
                }
                quizCount = parsedObject.size();
                System.out.println("Resulted Value:Question total " + quizCount);
                if (parsedObject.size() == 0) {
                    if (mCountDown != null) {
                        mCountDown.cancel();
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity_ps.this);
                    alertDialogBuilder.setTitle(R.string.end);
                    alertDialogBuilder
                            .setMessage(R.string.change)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    Intent intent = new Intent(QuizActivity_ps.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                } else {
                    firstQuestion = parsedObject.get(0);
                    firstQuestion = parsedObject.get(0);

                    questiontypeid = firstQuestion.getQuestiontypeid();
                    qun = String.valueOf(firstQuestion.getId());
                    System.out.println("Resulted Value:post execute " + questiontypeid);
                    switch (questiontypeid) {
                        case 1:
                            quizQuestion.setText(firstQuestion.getQuestion());
                            option = firstQuestion.getOptioncount();
                            System.out.println("option Count post excute " + option);
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();

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
                                new DownloadImageTask1(questioncontent, QuizActivity_ps.this).execute(firstQuestion.getQuestionurl());
                            }
                            if (type.get(1).equals(content)) {
                                questioncontent.setVisibility(View.GONE);
                                mediaplayer.setVisibility(View.VISIBLE);
                                new DownloadFileFromURL(mp).execute(firstQuestion.getQuestionurl());
                            }
                            if (option == 2) {
                                lin3.setVisibility(View.GONE);
                                c3.setVisibility(View.GONE);
                                lin4.setVisibility(View.GONE);
                                c4.setVisibility(View.GONE);
                                lin5.setVisibility(View.GONE);
                                c5.setVisibility(View.GONE);

                            }
                            if (option == 4) {
                                lin3.setVisibility(View.VISIBLE);
                                c3.setVisibility(View.VISIBLE);
                                lin4.setVisibility(View.VISIBLE);
                                c4.setVisibility(View.VISIBLE);
                                lin5.setVisibility(View.GONE);
                                c5.setVisibility(View.GONE);

                            }
                            if (option == 3) {
                                lin3.setVisibility(View.VISIBLE);
                                c3.setVisibility(View.VISIBLE);
                                lin4.setVisibility(View.GONE);
                                c4.setVisibility(View.GONE);
                                lin5.setVisibility(View.GONE);
                                c5.setVisibility(View.GONE);

                            }
                            Matchingans = firstQuestion.getMatchingans();
                            answer = firstQuestion.getAnswer();
                            String option1 = firstQuestion.getAnswers1();
                            c1.setText(option1);
                            optionid.add(firstQuestion.getOption1id());
                            String option2 = firstQuestion.getAnswers2();
                            c2.setText(option2);
                            optionid.add(firstQuestion.getOption2id());
                            String option3 = firstQuestion.getAnswers3();
                            c3.setText(option3);
                            optionid.add(firstQuestion.getOption3id());
                            String option4 = firstQuestion.getAnswers4();
                            c4.setText(option4);
                            optionid.add(firstQuestion.getOption4id());
                            String option5 = firstQuestion.getAnswers5();
                            c5.setText(option5);
                            optionid.add(firstQuestion.getOption5id());
                            System.out.println("Option ids" + optionid);
                            break;
                        default:
                    }
                }
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        QuizActivity_ps.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(QuizActivity_ps.this.getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                nextButton.setVisibility(View.GONE);
                                Intent i = new Intent(QuizActivity_ps.this, MainActivity.class);
                                startActivity(i);
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

    private List<QuizWrapper_ps> returnParsedJsonObject(String result) {

        List<QuizWrapper_ps> jsonObject = new ArrayList<QuizWrapper_ps>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper_ps newItemObject = null;


        try {
            resultObject = new JSONObject(result);
            System.out.println("Testing the water " + resultObject.toString());
            jsonArray = resultObject.optJSONArray("questionanswerlst");
            Log.d("Response:jsonArray value ", "> " + jsonArray);
            if (jsonArray == null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity_ps.this);
                alertDialogBuilder.setTitle(R.string.end);
                alertDialogBuilder
                        .setMessage(R.string.change)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Intent intent = new Intent(QuizActivity_ps.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                /*Toast.makeText(QuizActivity_ps.this,
                        R.string.change,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizActivity_ps.this, MainActivity.class);
                startActivity(intent);*/
            } else if (jsonArray != null) {
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

                        //  matchingans.add(matching.getJSONObject(i).getString("name"));
                        Log.d("Response:matchingans ", "> " + matchingans);

                        newItemObject = new QuizWrapper_ps(id, question, questionurl, questiontypeid, questioncontent, optioncount, optionsformatching, option1, option1id, option2, option2id, option3, option3id, option4, option4id, option5, option5id, answer, matchingans);
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
                    File wallpaperDirectory = new File("/sdcard/qib/Audio/");
                    wallpaperDirectory.mkdirs();
                    if (!wallpaperDirectory.exists()) {
                        wallpaperDirectory.mkdirs();
                        System.out.println("Directory created");
                    }
                    File outputFile = new File(wallpaperDirectory, fileName);
                    System.out.println("Video path URL file" + outputFile);
                    audiourl = String.valueOf(outputFile);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    //System.out.println("Video path URL file fos" + fos);
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
                    }/*try {
                            mp.setDataSource(String.valueOf(outputFile));
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
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
            try {
                mp.setDataSource(file_url);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UploadASyncTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(QuizActivity_ps.this);
            pDialog.setMessage(QuizActivity_ps.this.getString(R.string.plz));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJSONFromUrl(urlresponce);
            return json;
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(QuizActivity_ps.this);
            pDialog.setMessage(QuizActivity_ps.this.getString(R.string.plz));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... urls) {
            if (status == 0) {
                status = 0;
            } else {
                status = 1;
            }
            try {
                holeresponce = new JSONObject();

                holeresponce.put("UserID", userid);
                holeresponce.put("AssessmentID", assid);
                holeresponce.put("Language", lang);
                holeresponce.put("starttime", startdate + " " + examstarttime);
                holeresponce.put("endtime", startdate + " " + examendtime);
                holeresponce.put("status", status);
                holeresponce.put("Answerlist", result);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return POST(urls[0], holeresponce);
        }

        private String POST(String url, JSONObject holeresponce) {
            InputStream inputStream = null;
            String result = "";
            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(urlresponce);

                String json = "";

                // 3. build jsonObject

                // 4. convert JSONObject to JSON to String
                json = holeresponce.toString();
                System.out.println("holeresponce.toString()" + holeresponce.toString());
                // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person); 

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content   
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);
                int server = httpResponse.getStatusLine().getStatusCode();
                if (server == 200) {
                    // 9. receive response as inputStream
                    inputStream = httpResponse.getEntity().getContent();

                    // 10. convert inputstream to string
                    if (inputStream != null)
                        result = convertInputStreamToString(inputStream);

                    else
                        result = "Did not work!";

                    System.out.println("result value" + result);
                } else {
                    result = null;
                }


            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return result;
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

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            System.out.println("result value" + result);

            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            if (result != null) {
                try {
                    JSONObject temp1 = new JSONObject(result);
                    System.out.println("Converting " + temp1);

                    String psychometricmsg = temp1.getString("Psymessage");
                    System.out.println("psychometricmsg" + psychometricmsg);
                    String psycode = temp1.getString("Psycode");
                    System.out.println("Psycode " + psycode);

                    int status = temp1.getInt("Status");
                    System.out.println("status " + status);

                    String psyurl = temp1.getString("PsyUrl");
                    System.out.println("Psychometric Url " + psyurl);


                /*int correctans=temp1.getInt("CorrectAns");
                System.out.println("CorrectAnswer " + correctans);
                int wrong=temp1.getInt("IncorrectAns");
                System.out.println("wrong answer " + wrong);*/
                    if (status == 1) {
                        // successfully created student

                        Intent i = new Intent(QuizActivity_ps.this, Psychometricresult.class);
                        i.putExtra("psymessage", psychometricmsg);
                        i.putExtra("psycode", psycode);
                        i.putExtra("psyurl", psyurl);
                   /* i.putExtra("correctans",correctans);
                    i.putExtra("wronganswer",wrong);*/

                        startActivity(i);
                    } else {
                        Intent i = new Intent(QuizActivity_ps.this, MainActivity.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        QuizActivity_ps.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(QuizActivity_ps.this.getString(R.string.server))
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
        // do nothing.
    }
}
