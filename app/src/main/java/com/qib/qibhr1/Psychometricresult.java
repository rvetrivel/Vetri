package com.qib.qibhr1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by vetrivel on 1/8/2016.
 */

public class Psychometricresult extends Activity {
    private Button button;
    private ImageView personality, person;
    private TextView name;
    private TextView sentence;
    private String psychometricmsg;
    private String url, type, email;
    private Typeface myTypeface;
    private SessionManager session;
    private String suname;
    private String TAG = " PushActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_maincategory.xmlgory.xml
        setContentView(R.layout.psychometricresult);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }

        myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");

        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        suname = settings.getString("useremail", null);
        Log.d(TAG, "Lang " + suname);

        name = (TextView) findViewById(R.id.name);
        name.setTypeface(myTypeface);
        name.setText(suname);
        sentence = (TextView) findViewById(R.id.type);
        sentence.setTypeface(myTypeface);
        button = (Button) findViewById(R.id.next);
        button.setTypeface(myTypeface);
        person = (ImageView) findViewById(R.id.person);
        personality = (ImageView) findViewById(R.id.personalityimge);

        Intent intent = getIntent();
        psychometricmsg = intent.getStringExtra("psymessage");
        System.out.println("Psychometric result passing value" + psychometricmsg);
        url = intent.getStringExtra("psyurl");
        System.out.println("Psychometric result passing value" + url);
        type = intent.getStringExtra("psyurl");
        System.out.println("Psychometric result passing value" + type);

        new DownloadImageTask1(personality, Psychometricresult.this).execute(url);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(Psychometricresult.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
