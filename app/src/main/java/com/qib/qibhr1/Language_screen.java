package com.qib.qibhr1;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Locale;

@SuppressLint("NewApi")
public class Language_screen extends Activity {
    SessionManager session;
    Button btn;
    Locale myLocale;
    public static String lang;
    String langu;
    private static final String tag = "SignUp Instruction";
    SharedPreferences settings;
    private Activity mContext;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* Mediaplayer stopped */
        session = new SessionManager(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        ActionBar bar = getActionBar();
        // for color
        // bar.setBackgroundDrawable(new
        // ColorDrawable(Color.parseColor("#CFA47B")));
        bar.setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.setting));
        getActionBar().setIcon(R.drawable.ic_launcher);


        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().hide();
        session.isLoggedIn();

    }


    public void english(View view) {
        lang = "en";
        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("lang", lang);
        //editor.putString("lang1",lang);
        editor.commit();
        Intent intent = new Intent(this, Login_Screen.class);
        startActivity(intent);
        setLocale("en");
    }


    public void Arabic(View view) {
        lang = "ar";
        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("lang", lang);
        editor.commit();

        Intent intent = new Intent(this, Login_Screen.class);
        startActivity(intent);
        Log.d(tag, "lang " + lang);

        setLocale("ar");
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Login_Screen.class);
        startActivity(refresh);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Language_screen.this);
        // set dialog message
        alertDialogBuilder
                .setMessage(Language_screen.this.getString(R.string.areyou))
                .setCancelable(false)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
