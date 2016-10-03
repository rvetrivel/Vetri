package com.qib.qibhr1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.qib.qibhr1.CommonUtilities.EXTRA_MESSAGE;
//import static com.gasofttech.qib.CommonUtilities.SERVER_URL;

public class SplashScreen extends Activity {
    Boolean login;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    EditText EmailId, Password;
    CheckBox cb1;

    private static String email;
    private static String password, suname, spassword,saved_email,saved_password;
    private static final String TAG_SESSION = "sessionid";
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_NAME = "username";
    private static final String TAG_ID = "userid";
    private static final String TAG_LANG = "lang";
    private static final String TAG_ROLE = "roleid";
    private static final String tag = "splash Instruction";
    JSONParser jsonParser = new JSONParser();
    private JSONObject json;
    Locale myLocale;
    private ProgressDialog pDialog;
    private String CurentTime, date, sessionid;
    private int roleid;
    String responseServer;
    private int fl = 0;

    public static String Android_device;
    public static String username;
    public static String ipaddress;
    public static String FinalData;
    public static String regId;
    String lang;
    public static String userid;
    private TextView msg;
    AsyncTask<Void, Void, Void> mRegisterTask;
    private String TAG = " PushActivity";
    private Typeface myTypeface;
    private TextView remember, user, toolbartitle;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        RelativeLayout splash = (RelativeLayout) findViewById(R.id.splash);
        Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.splash_screen_1);
        BitmapDrawable splashimage = new BitmapDrawable(homeIcon);
        splash.setBackground(splashimage);
        session = new SessionManager(getApplicationContext());


        //Log.d(tag, "isNetworkAvailable " + isNetworkAvailable());
        getActionBar().hide();
        SharedPreferences settings = getSharedPreferences("Qib", 0);
        login = settings.getBoolean("IsLoggedIn", false);
        Log.d(tag, "login " + login);
        SharedPreferences settings2 = getSharedPreferences("qibhr1", 0);
        lang = settings2.getString("lang", null);

        //userid = settings.getString("userid", null);
        Log.d(tag, "Lang " + lang);
        Android_device = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("Android", "Android ID : " + Android_device);
        if (isNetworkAvailable()) {
            if (login == true) {

                SharedPreferences settings1 = getSharedPreferences("Qib", 0);
                suname = settings1.getString("userName", null);
                spassword = settings1.getString("password1", null);
                Log.d(tag, "suname " + suname);
                Log.d(tag, "spassword " + spassword);
                try {
                    saved_email = Encryption.Encrypt(suname, "9%9Vt3");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(tag, "saved_email " + saved_email);
                try {
                    saved_password = Encryption.Encrypt(spassword, "9%9Vt3");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(tag, "saved_password " + saved_password);
                /*Intent intent = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(intent);*/
                new LoginNewuser().execute();
            } else {

                new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent i = new Intent(SplashScreen.this, Language_screen.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    SplashScreen.this);
            // set dialog message
            alertDialogBuilder
                    .setMessage(SplashScreen.this.getString(R.string.network))
                    .setCancelable(false)
                    .setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            Intent i = new Intent(SplashScreen.this, Language_screen.class);
                            startActivity(i);
                        }
                    })
                    .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
						/*Intent intent = new Intent(Language_screen.this, Language_screen.class);
						startActivity(intent);*/
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            //Log.d(LOG_TAG, "No network present");
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }


    class LoginNewuser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashScreen.this);
            pDialog.setMessage(SplashScreen.this.getString(R.string.loggin));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating student
         */
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", suname));
            params.add(new BasicNameValuePair("password", spassword));

            //String url = "http://www.ems.belmonttek.com/gaapi/ApiLogin?lang="+lang+"&username=" + suname + "&password=" + spassword;
            String url = AppUrl.URL_COMMAN+"gaapi/LOGINEN?lang=" + lang + "&username=" + saved_email + "&password=" + saved_password + "&FL=" + Android_device + "&XP=" + fl;
							/*try {
						InetAddress.getByName(url).isReachable(timeout);
					} catch (IOException e) {
						e.printStackTrace();
					}*/

            json = jsonParser.makeHttpRequest(
                    url, "POST", params);

            Log.d(tag, "url " + url);
            if (json == null) {
                System.out.println("errorrrrrrrrrrrrrrrrrrrr");
            } else {

                try {

                    int code = json.getInt(TAG_SUCCESS);
                    userid = json.getString(TAG_ID);
                    lang = json.getString(TAG_LANG);
                    int roleid = json.getInt(TAG_ROLE);

                    username = json.getString(TAG_NAME);
                    String[] test = username.split(" ");
                    FinalData = "";
                    for (int i = 0; i < test.length; i++) {

                        FinalData += test[i];

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(String file_url) {


            pDialog.dismiss();
            int code;
            if (json == null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        SplashScreen.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(SplashScreen.this.getString(R.string.server))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                Intent i = new Intent(SplashScreen.this, Language_screen.class);
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
            } else {
                try {
                    code = json.getInt(TAG_SUCCESS);
                    if (code == 00) {
                        if (lang.equals("en")) {
                            setLocale("en");
                            System.out.println("Splash screen lang en");
                        } else if (lang.equals("ar")) {
                            setLocale("ar");
                            System.out.println("Splash screen lang ar");
                        }
                        Intent i = new Intent(getApplicationContext(),
                                MainActivity.class);

                        startActivity(i);
                        Toast.makeText(getApplicationContext(),
                                R.string.lginsucc, Toast.LENGTH_LONG)
                                .show();
                        // closing this screen

                    } else if (code == 01) {

                        Toast.makeText(SplashScreen.this,
                                R.string.invalid,
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SplashScreen.this, Login_Screen.class);
                        startActivity(i);
                        // failed to create student
                    } else if (code == 02) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                SplashScreen.this);
                        // set dialog message
                        alertDialogBuilder
                                .setMessage(SplashScreen.this.getString(R.string.sure1))
                                .setCancelable(false)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(getApplicationContext(),
                                                Login_Screen.class);
                                        startActivity(i);
                                        dialog.cancel();
                                        //new LogoutTask(SplashScreen.this).execute();
                                    }
                                })
                                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
                        // failed to create student
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    private boolean isValidLasttName(String password) {
        // TODO Auto-generated method stub
        return password != null && password.length() > 5;
    }

    private boolean isValidFirstName(String userName) {
        // TODO Auto-generated method stub
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();

    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
                    msg.append(newMessage + "\n");
                }
            };

}


