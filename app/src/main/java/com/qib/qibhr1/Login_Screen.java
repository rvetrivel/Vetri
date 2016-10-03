package com.qib.qibhr1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.apache.http.NameValuePair;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.qib.qibhr1.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.qib.qibhr1.CommonUtilities.EXTRA_MESSAGE;
import static com.qib.qibhr1.CommonUtilities.SENDER_ID;


public class Login_Screen extends Activity {
    EditText EmailId, Password;
    CheckBox cb1;
    private SessionManager session;
    private static String email,input_email,input_password;
    private static String password, suname, spassword;
    private static final String TAG_SESSION = "sessionid";
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_NAME = "username";
    private static final String TAG_ID = "userid";
    private static final String TAG_LANG = "lang";
    private static final String TAG_ROLE = "roleid";
    private static final String tag = "SignUp Instruction";
    JSONParser jsonParser = new JSONParser();
    private JSONObject json;
    private ProgressDialog pDialog;
    private String CurentTime, date, sessionid;
    private int role;
    String responseServer;


    public static String Android_device;
    public static String username;
    public static String ipaddress;
    public static String FinalData;
    public static String regId;
    public static String lang;
    public static String userid;
    private TextView msg;
    private int fl = 0;
    AsyncTask<Void, Void, Boolean> mRegisterTask;
    private String TAG = " PushActivity";
    private Typeface myTypeface;
    private TextView remember, user, toolbartitle;

    /*public static final String MyPREFERENCES = "Qib" ;
    SharedPreferences settings;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);

        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);

        //userid = settings.getString("userid", null);
        Log.d(tag, "Lang " + lang);
        //Log.d(tag, "userid " + userid);
        if (lang == null) {
            lang = "en";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        //new JSONParse().execute();


        //  ip address find
        myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");
        user = (TextView) findViewById(R.id.user);
        user.setTypeface(myTypeface);
        toolbartitle = (TextView) findViewById(R.id.toolbartitle);

        toolbartitle.setTypeface(myTypeface);
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress() ) {
                    if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
                        ipaddress = inetAddress.getHostAddress();
                        Log.d(TAG, ipaddress);
                    }
                }
            }
        } catch (SocketException ex) {
            Log.d(TAG, ex.toString());
        }

        // Android unique id :

        Android_device = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("Android", "Android ID : " + Android_device);

        session = new SessionManager(getApplicationContext());

        remember = (TextView) findViewById(R.id.remember);
        remember.setTypeface(myTypeface);
        cb1 = (CheckBox) findViewById(R.id.checkBox6);
        cb1.setTypeface(myTypeface);
        EmailId = (EditText) findViewById(R.id.email);
        EmailId.setTypeface(myTypeface);
        Password = (EditText) findViewById(R.id.password);
        Password.setTypeface(myTypeface);
        Button btnLogIn = (Button) findViewById(R.id.btnLogin1);
        btnLogIn.setTypeface(myTypeface);
// get user data from session

		/*HashMap<String, String> user = session.getUserDetails();

		suname = user.get(SessionManager.KEY_NAME);

		spassword = user.get(SessionManager.KEY_EMAIL);

		Log.d(tag, "suname " + suname);

		Log.d(tag, "spassword " + spassword);*/
        SharedPreferences settings1 = getSharedPreferences("Qib", 0);
        suname = settings1.getString("userName", null);
        spassword = settings1.getString("password1", null);

        Log.d(tag, "suname " + suname);

        Log.d(tag, "spassword " + spassword);
        EmailId.setText(suname);
        Password.setText(spassword);
        btnLogIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Date now = new Date();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                CurentTime = df.format(now);
                System.out.println("Current Time" + CurentTime);
                //Log.d("gmtTime: ", "> " + CurentTime);

                String userName = EmailId.getText().toString();
                String password1 = Password.getText().toString();
                Log.d(tag, "userName " + userName);
                Log.d(tag, "password1 " + password1);


                if (!userName.equals("") && !password1.equals("")) {
                    if (cb1.isChecked()) {
                        SharedPreferences settings1 = getSharedPreferences("Qib", 0);
                        SharedPreferences.Editor editor1 = settings1.edit();
                        editor1.putString("userName", userName);
                        editor1.putString("password1", password1);
                        Log.d(tag, " userName " + userName);
                        editor1.commit();
                        session.createLoginSession(userName, password1);

                    } else {
                    }
                    if (isNetworkAvailable()) {
                        email = EmailId.getText().toString().trim();
                        try {
                            //input_email = Encryption.Encrypt(email, "9%9Vt3");
                            input_email =URLEncoder.encode(Encryption.Encrypt(email, "9%9Vt3"),"UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(tag, "input_email " + input_email);
                        password = Password.getText().toString().trim();
                        try {
                            input_password = URLEncoder.encode(Encryption.Encrypt(password, "9%9Vt3"),"UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(tag, "input_password " + input_password);
                        new LoginNewuser().execute();

                    } else {
                        Toast.makeText(Login_Screen.this,
                                R.string.network,
                                Toast.LENGTH_SHORT).show();
                        //Log.d(LOG_TAG, "No network present");
                    }
                } else {
                    Toast.makeText(Login_Screen.this,
                            R.string.plzent,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

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
        });

    }

    private void gcmregister() {

        checkNotNull(SENDER_ID, "SENDER_ID");
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(Login_Screen.this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(Login_Screen.this);
        msg = (TextView) findViewById(R.id.display);
        registerReceiver(mHandleMessageReceiver,
                new IntentFilter(DISPLAY_MESSAGE_ACTION));
        regId = GCMRegistrar.getRegistrationId(Login_Screen.this);


        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(Login_Screen.this, SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(Login_Screen.this)) {
                // Skips registration.
                msg.append(getString(R.string.already_registered) + "\n");

            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = Login_Screen.this;
                mRegisterTask = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        boolean registered =
                                ServerUtilities.register(context, regId);

                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return registered;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        //pDialog.dismiss();
                        System.out.println("Home Instruction reg id ----->" + result);
                        mRegisterTask = null;

                    }

                };
                //mRegisterTask.execute(null, null, null);
            }
        }
    }

    private boolean isNetworkAvailable() {
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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Language_screen.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onDestroy() {
        /*if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
		}
		unregisterReceiver(mHandleMessageReceiver);
		GCMRegistrar.onDestroy(this);*/
        super.onDestroy();
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


    private class LoginNewuser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login_Screen.this);
            pDialog.setMessage(Login_Screen.this.getString(R.string.loggin));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating student
         */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            String url = AppUrl.URL_COMMAN+"gaapi/LOGINEN?lang=" + lang + "&username=" + input_email + "&password=" + input_password + "&FL=" + Android_device + "&XP=" + fl;
            json = jsonParser.makeHttpRequest(
                    url, "POST", params);

            Log.d(tag, "url " + url);

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


						/*SharedPreferences settings1 = getSharedPreferences("userid", 0);
                        SharedPreferences.Editor editor1 = settings1.edit();
						editor1.putString("userid", userid);
						Log.d(tag, " userid " + userid);
						editor1.commit();*/

                SharedPreferences settings = getSharedPreferences("qibhr1", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("userid", userid);
                editor.putInt("roleid", roleid);
                editor.putString("useremail", email);
                //editor.putString("regId", regId);
                editor.putString("Android_device", Android_device);
                editor.putString("ipaddress", ipaddress);
                editor.putString("ipaddress", FinalData);
                Log.d(tag, " lang " + lang);
                Log.d(tag, " email " + email);
                editor.commit();
                        /*//SharedPreferences settings = getSharedPreferences("AndroidHivePref", 0);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
						editor.putString("userid", userid);
						editor.putString("lang", lang);
						editor.putString("roleid", roleid );
						Log.d(tag, " lang " + lang);
						Log.d(tag, " roleid " + roleid);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            gcmregister();
            int code;
            try {
                code = json.getInt(TAG_SUCCESS);
                if (code == 00) {

                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),
                            R.string.lginsucc, Toast.LENGTH_LONG)
                            .show();
                    // closing this screen

                } else if (code == 01) {

                    Toast.makeText(Login_Screen.this,
                            R.string.invalid,
                            Toast.LENGTH_SHORT).show();
                    // failed to create student
                } else if (code == 02) {
                    role = json.getInt(TAG_ROLE);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Login_Screen.this);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage(Login_Screen.this.getString(R.string.sure1))
                            .setCancelable(false)
                            .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //new LogoutTask(Login_Screen.this).execute();
                                    fl = 1;
                                    new LoginNewuser().execute();

                                    dialog.cancel();
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
