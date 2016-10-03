
package com.qib.qibhr1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.qib.qibhr1.charting.animation.Easing;
import com.qib.qibhr1.charting.charts.PieChart;
import com.qib.qibhr1.charting.components.Legend;
import com.qib.qibhr1.charting.data.DataSet;
import com.qib.qibhr1.charting.data.Entry;
import com.qib.qibhr1.charting.data.PieData;
import com.qib.qibhr1.charting.data.PieDataSet;
import com.qib.qibhr1.charting.formatter.PercentFormatter;
import com.qib.qibhr1.charting.utils.ColorTemplate;
import com.qib.qibhr1.quiz.Constants;

import java.util.ArrayList;

public class PieChartActivity extends Activity {

    private PieChart mChart;
    private Typeface myTypeface;
    int time;
    //private Toolbar toolbar;
    private Typeface tf;
    int totaltime;
    int total;
    private String correct;
    private String score;
    private String wrong;
    private String accuracy;
    int numberOfQuestions;
    private String crorecte;
    private String wronge;
    protected String[]  mParties = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");
        //crorecte=getResources().getString(R.string.correct);
        //wronge=getResources().getString(R.string.wrong);
        mParties = new String[] {
                "",""
        };

        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        String topic=settings.getString("topicname", null);
        String suname = settings.getString("useremail", null);

        TextView username=(TextView)findViewById(R.id.user);
        username.setTypeface(myTypeface);
        username.setText(suname);

        /*TextView efficiency = (TextView) findViewById(R.id.efficiency);
        efficiency.setTypeface(myTypeface);*/
        TextView level = (TextView) findViewById(R.id.lsc);
        level.setTypeface(myTypeface);
        TextView sec = (TextView) findViewById(R.id.sec);
        sec.setTypeface(myTypeface);
        TextView sc1 = (TextView) findViewById(R.id.score1);
        sc1.setTypeface(myTypeface);
        TextView tim = (TextView) findViewById(R.id.timing);
        tim.setTypeface(myTypeface);

        TextView lavelscr = (TextView) findViewById(R.id.level);
        lavelscr.setTypeface(myTypeface);
        lavelscr.setText(topic);


        Button next=(Button) findViewById(R.id.next);
        next.setTypeface(myTypeface);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PieChartActivity.this,MainActivity.class);
                startActivity(i);

            }

        });

        int remaining = 30;
        total=30;
        ///int score = 3;
        numberOfQuestions=5;

        Intent intent = getIntent();
        time = intent.getIntExtra("totaltime", 0);
        Log.d("Quiz activity", "Passing value total time" + time);
        int score1 = intent.getIntExtra("score", 0);
        Log.d("Quiz activity", "Passing value total score" + score1);
        score= String.valueOf(score1);
        String accuracy1 = intent.getStringExtra("accuracy");
        Log.d("Quiz activity", "Passing value accuracy" + accuracy1);
        accuracy= String.valueOf(accuracy1);
        int correct1 = intent.getIntExtra("correctans", 0);
        Log.d("Quiz activity", "Passing value correct answer" + correct1);
        correct= String.valueOf(correct1);

        int wronganswer = intent.getIntExtra("wronganswer", 0);
        Log.d("Quiz activity", "Passing value wrong answer" + wronganswer);
        wrong= String.valueOf(wronganswer);
        if(correct.equals(0)){
            correct="15";
            wrong="0";
        }

        String timing= String.valueOf(time);


        // int percent = (int)(((double)totaltime/(double)total)*((double)write/(double)numberOfQuestions) * 100);
        //int percent = (remaining/total)*(score/numberOfQuestions)*100;
        //efficiency.setText(score);
        sc1.setText(score);
        tim.setText(timing);


        // result.setText(score+"/"+numberOfQuestions+"  "+percent+"%");
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setCenterText(PieChartActivity.this.getString(R.string.accuracy) + "\n" + accuracy + "%");

        mChart.setCenterTextSize(15);
        mChart.setCenterTextColor(Color.WHITE);
        mChart.setHoleColor(Color.parseColor("#0091e6"));
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "dax_regular.ttf"));

        //mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(false);

        mChart.setTransparentCircleColor(Color.BLUE);

        mChart.setHoleRadius(48f);
        mChart.setTransparentCircleRadius(42f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);


        setData(100, correct, wrong);
        Log.d("Circle value", "circle value" + correct + wrong);
        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private int getDifficultySettings() {
        SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
        int diff = settings.getInt(Constants.DIFFICULTY, 2);
        return diff;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (DataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1800);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1800);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1800, 1800);
                break;
            }
        }
        return true;
    }


    private void setData(int count, String present_percentage, String absent_percentage) {


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(2f);

        if(!present_percentage.equalsIgnoreCase("0"))
        {     //present 23
            yVals1.add( new Entry( Float.parseFloat(present_percentage) ,0));
        }




        if(!absent_percentage.equalsIgnoreCase("0"))
        {      //absent 33
            yVals1.add( new Entry(Float.parseFloat(absent_percentage),0));
        }



        dataSet = new PieDataSet(yVals1, "");


        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);




        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);



        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onBackPressed() {

    }
}