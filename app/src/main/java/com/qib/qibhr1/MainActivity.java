package com.qib.qibhr1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends FragmentActivity implements AsyncResponse {
    public static final Integer[] images = {
            R.drawable.home,
            R.drawable.instruction,
            R.drawable.notification,
            R.drawable.activities,
            R.drawable.scorecard,
            R.drawable.language,
            R.drawable.profile,
            R.drawable.logout

    };
    private Typeface myTypeface;
    private DrawerLayout mDrawerLayout;
    ImageView home, home1;
    Locale myLocale;
    TextView hometitle;
    Fragment fragment = null;
    SessionManager session;
    String count;
    TextView appname;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    private Toolbar toolbar;
    private static String os = "Android";
    private static String login = "1";
    private static String logout = "0";
    private static final String TAG_SESSION = "sessionid";
    static final String TAG = "MainActivity";
    private static final String tag = "Main Instruction";
    RelativeLayout bg_list;
    private JSONObject json;
    String responseServer, responseServer1;
    private String sessionid, CurentTime;
    String url, sesson2, lang, userid, devid;
    Serverhandler server = new Serverhandler(this);
    int mSelectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fontPath = "dax_regular.ttf";
        setContentView(R.layout.activity_navigation);
        home = (ImageView) findViewById(R.id.home);
        home1 = (ImageView) findViewById(R.id.home1);
        hometitle = (TextView) findViewById(R.id.hometext);
        bg_list = (RelativeLayout) findViewById(R.id.bg_list);
        session = new SessionManager(MainActivity.this);
        home.setOnClickListener(homeOnclickListener);

        SharedPreferences settings1 = getSharedPreferences("qibhr1 ", 0);

        lang = settings1.getString("lang", null);
        if (lang != null) {
            setLocale1(lang);

            Log.d("session lang: ", "> " + lang);
        } else {

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        CurentTime = df.format(now);
        System.out.println("Current Time main activity" + CurentTime);
        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
        lang = settings.getString("lang", null);
        sessionid = settings.getString("sesson2", null);
        server.delegate = this;
        server.execute();
        JSONObject servercheck = server.json;
        setUpDrawer();
        if (savedInstanceState == null) {
            fragment = new Fragment1Category();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            hometitle.setText(getResources().getString(R.string.home));
            home1.setImageResource(R.drawable.home);
            mDrawerLayout.closeDrawer(expListView);

        }


    }


    @Override
    public void processFinish(JSONObject output) {
        System.out.println("Json object value in main activity:  " + output);
        System.out.println("server handler json value: " + json);
        if (output == null) {
            System.out.println("errorrrrrrrrrrrrrrrrrrrr");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);
            // set dialog message
            alertDialogBuilder
                    .setMessage(MainActivity.this.getString(R.string.server))
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
                            MainActivity.this.startActivity(intent);
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

            if (sessionid != null) {
                Log.d("sessionid: ", "> " + sessionid);
                new LogoutTask(MainActivity.this).execute();

            } else {
                //if(servercheck==true){
                AsyncT asyncT = new AsyncT();
                asyncT.execute();
                Log.d(tag, "asyncT " + asyncT);
                //}
            }

        }


    }

    private void setUpDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerListener(mDrawerListener);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setGroupIndicator(null);
        expListView.setChildIndicator(null);

		/*LayoutInflater inflater = getLayoutInflater();
        View inflateview = inflater.inflate(R.layout.nav_header, null, false);
		expListView.addHeaderView(inflateview);*/

		/*LayoutInflater inflater1 = getLayoutInflater();
        View inflateview1 = inflater1.inflate(R.layout.nav_footer, null, false);
		expListView.addFooterView(inflateview1);*/


        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adaptermDrawerLayout
        expListView.setAdapter(listAdapter);


        ImageView logout = (ImageView) findViewById(R.id.log);


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                ImageView imageView = (ImageView) findViewById(R.id.expandableHeaderIcon);

                //	View img=null;
                imageView.setBackgroundResource(R.drawable.uparrow);
                System.out.println(previousGroup);
                if ((groupPosition != previousGroup) && (previousGroup != -1)) {
                    expListView.collapseGroup(previousGroup);

                }
                previousGroup = groupPosition;

            }
        });
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String title = "";
                mSelectedItem = groupPosition;
                switch (groupPosition) {
                    case 0:
                        fragment = new Fragment1Category();
                        hometitle.setText(getResources().getString(R.string.home));
                        home1.setImageResource(R.drawable.home);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        setLocale(lang);
                        break;
                    case 1:
                        fragment = new Fragment2Instructiondy();
                        hometitle.setText(MainActivity.this.getString(R.string.guide));
                        home1.setImageResource(R.drawable.instruction);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 2:
                        fragment = new Fragment3Notification();
                        hometitle.setText(MainActivity.this.getString(R.string.notifications));
                        home1.setImageResource(R.drawable.notification);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 3:
                        fragment = new Fragment4Activities();
                        hometitle.setText(MainActivity.this.getString(R.string.activities));
                        home1.setImageResource(R.drawable.activities);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 4:
                        fragment = new Fragment5Scoreboard();
                        home1.setImageResource(R.drawable.scorecard);
                        hometitle.setText(MainActivity.this.getString(R.string.scoreb));
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        break;

                    case 5:
                        hometitle.setText(MainActivity.this.getString(R.string.language));
                        home1.setImageResource(R.drawable.language);

                        break;
                    case 6:
                        fragment = new Fragment7profile();
                        home1.setImageResource(R.drawable.profile);
                        hometitle.setText(MainActivity.this.getString(R.string.profile));
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 7:
                        hometitle.setText(MainActivity.this.getString(R.string.logout));
                        home1.setImageResource(R.drawable.logout);
                        SharedPreferences settings = getSharedPreferences("qibhr1", 0);
                        userid = settings.getString("userid", null);
                        sessionid = settings.getString("sesson2", null);
                        devid = settings.getString("Android_device", null);
                        Log.d("sessionid: ", "> " + sessionid);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setMessage(MainActivity.this.getString(R.string.sure));

                        alertDialogBuilder.setPositiveButton(MainActivity.this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                if (isNetworkAvailable()) {
                                    new LogoutTasklogout(MainActivity.this).execute();

                                } else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            MainActivity.this);
                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage(MainActivity.this.getString(R.string.network))
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
                                                    MainActivity.this.finish();
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

                        alertDialogBuilder.setNegativeButton(MainActivity.this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;
                }
                /*if (groupPosition == mSelectedItem) {
                    // set your color
                    v.setBackgroundResource(R.color.list_background_pressed);
                }else if(groupPosition != mSelectedItem){
                    v.setBackgroundResource(R.color.ColorPrimary);
                }*/
              /* int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, groupPosition));
                parent.setItemChecked(index, true);*/
                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForGroup(groupPosition));
                parent.setItemChecked(index, true);
                return false;
            }
        });


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (groupPosition) {

                    case 5:
                        switch (childPosition) {
                            case 0:
                                fragment = new Fragment1Category();
                                hometitle.setText(MainActivity.this.getString(R.string.language));
                                lang = "EN";
                                SharedPreferences settings = getSharedPreferences("qibhr1", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("lang", lang);
                                editor.commit();
                                setLocale("en");
                                break;
                            case 1:
                                fragment = new Fragment1Category();
                                hometitle.setText(MainActivity.this.getString(R.string.language));
                                lang = "AR";
                                SharedPreferences settings1 = getSharedPreferences("qibhr1", 0);
                                SharedPreferences.Editor editor1 = settings1.edit();
                                editor1.putString("lang", lang);
                                editor1.commit();
                                setLocale("ar");
                                break;

                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                mDrawerLayout.closeDrawer(expListView);
                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                return false;
            }
        });


    }

    class AsyncT1 extends AsyncTask<Void, Void, String> {
        int status;
        HttpResponse response = null;

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(AppUrl.URL_COMMAN+"gaapi/Logouttime?sessionid=" + sessionid + "&logouttime=" + CurentTime + "&devid=" + devid + "&uid=" + userid + "&os=1");
            System.out.println("Logout url" + String.valueOf(httppost));

            try {

                JSONObject jsonobj = new JSONObject();

                jsonobj.put("CurentTime", CurentTime);
                jsonobj.put("sessionid", sessionid);
                jsonobj.put("sessionid", devid);
                jsonobj.put("sessionid", userid);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                status = response.getStatusLine().getStatusCode();
                InputStream inputStream = null;
                if (status == 200) {
                    inputStream = response.getEntity().getContent();
                    InputStreamToStringExample str = new InputStreamToStringExample();
                    responseServer = InputStreamToStringExample.getStringFromInputStream(inputStream);
                    Log.e("response", "response -----" + responseServer);

                } else if (status != 200) {
                    responseServer = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseServer;
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);

            // txt.setText(responseServer);
            if (status != null) {
                SharedPreferences settings = MainActivity.this.getSharedPreferences("qibhr1", 0);
                settings.edit().remove("sesson2").commit();
                Log.e("response", "response -----" + status);
                System.out.println("Session id 2" + sessionid);

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                // set dialog message
                alertDialogBuilder
                        .setMessage(MainActivity.this.getString(R.string.server))
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
                                MainActivity.this.finish();
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
                (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
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
       /* fragment = new Fragment1Category();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        hometitle.setText(getResources().getString(R.string.home));
        home1.setImageResource(R.drawable.home);
        mDrawerLayout.closeDrawer(expListView);*/
    }

    public void setLocale1(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    OnClickListener homeOnclickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDrawerLayout.isDrawerOpen(expListView)) {
                mDrawerLayout.closeDrawer(expListView);
            } else {
                mDrawerLayout.openDrawer(expListView);
            }
        }
    };


    private DrawerListener mDrawerListener = new DrawerListener() {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
        }

        @Override
        public void onDrawerClosed(View view) {
        }
    };

    private void prepareListData() {

        String string1 = getResources().getString(R.string.home);
        String string2 = getResources().getString(R.string.guide);
        String string3 = getResources().getString(R.string.notifications);
        String string4 = getResources().getString(R.string.activities);
        String string5 = getResources().getString(R.string.scoreb);
        String string6 = getResources().getString(R.string.language);
        String string7 = getResources().getString(R.string.profile);
        String string8 = getResources().getString(R.string.logout1);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(string1);
        listDataHeader.add(string2);
        listDataHeader.add(string3);
        listDataHeader.add(string4);
        listDataHeader.add(string5);
        listDataHeader.add(string6);
        listDataHeader.add(string7);
        listDataHeader.add(string8);

        String string11 = getResources().getString(R.string.english);
        String string12 = getResources().getString(R.string.arabic);
        // Adding child data
        List<String> home = new ArrayList<String>();
        List<String> guide = new ArrayList<String>();
        List<String> notifications = new ArrayList<String>();
        List<String> activities = new ArrayList<String>();
        List<String> scoreboard = new ArrayList<String>();
        List<String> language = new ArrayList<String>();
        language.add(string11);
        language.add(string12);
        List<String> profile = new ArrayList<String>();
        List<String> logout = new ArrayList<String>();

        listDataChild.put(listDataHeader.get(0), home); // Header, Child data
        listDataChild.put(listDataHeader.get(1), guide);
        listDataChild.put(listDataHeader.get(2), notifications);
        listDataChild.put(listDataHeader.get(3), activities);
        listDataChild.put(listDataHeader.get(4), scoreboard);
        listDataChild.put(listDataHeader.get(5), language);
        listDataChild.put(listDataHeader.get(6), profile);
        listDataChild.put(listDataHeader.get(7), logout);


    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item1, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            boolean status = this._listDataChild.get(this._listDataHeader.get(groupPosition)).isEmpty();
            if (status == true) {
                return 0;
            } else {
                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                        .size();
            }

        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);

            }
//			System.out.println(getChildrenCount(groupPosition));
            /*if (groupPosition==0){
                convertView.setBackgroundResource(R.drawable.bg_key);
            }else {
                convertView.setBackgroundResource(R.drawable.drawer_list_selection);
            }*/
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            //lblListHeader.setTypeface(null, Typeface.BOLD);
            myTypeface = Typeface.createFromAsset(getAssets(), "dax_regular.ttf");
            lblListHeader.setTypeface(myTypeface);
            lblListHeader.setText(headerTitle);
            ImageView imgListGroup = (ImageView) convertView
                    .findViewById(R.id.imghead);

            imgListGroup.setImageResource(MainActivity.images[groupPosition]);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.expandableHeaderIcon);
            imageView.setVisibility(View.VISIBLE);

            if (isExpanded) {
                imageView.setImageResource(R.drawable.uparrow);
            } else {
                imageView.setImageResource(R.drawable.downarrow);
            }
            if (getChildrenCount(groupPosition) == 0) {
                imageView.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    private class OnNavigationItemSelectedListener {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity_instruction in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(AppUrl.URL_COMMAN+"gaapi/AndroidGcmIdSave?userid=" + Login_Screen.userid + "&gcmid=" + Login_Screen.regId + "&deviceid=" + Login_Screen.Android_device
                    + "&ip=" + Login_Screen.ipaddress + "&os=" + os + "&lang=" + lang + "&logintime=" + CurentTime);
            //http://www.ems.belmonttek.com/gaapi/AndroidGcmIdSave?userid=22&gcmid=ascd123&deviceid=123abc&ip=10.20.30.105&os=IOS&lang=en&logintime=10:20:00
            String url1 = AppUrl.URL_COMMAN+"gaapi/AndroidGcmIdSave?userid=" + Login_Screen.userid + "&gcmid=" + ServerUtilities.register_id + "&deviceid=" + Login_Screen.Android_device
                    + "&ip=" + Login_Screen.ipaddress + "&os=" + os + "&lang=" + lang + "&logintime=" + CurentTime;
            System.out.println("Main activity gcm url" + url1);
            try {

                JSONObject jsonobj = new JSONObject();

                jsonobj.put("userid", Login_Screen.userid);
                jsonobj.put("gcmid", Login_Screen.regId);
                jsonobj.put("deviceid", Login_Screen.Android_device);
                jsonobj.put("ip", Login_Screen.ipaddress);
                jsonobj.put("username", Login_Screen.FinalData);
                jsonobj.put("lang", lang);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                Log.e("loginoPost", "loginoPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.e("login httppost response", "login time response -----" + httppost);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = InputStreamToStringExample.getStringFromInputStream(inputStream);
                Log.e("login response", "login time response -----" + responseServer);
                //String details = "Hello \"world\"!";
                responseServer1 = responseServer.replace("\"", "");
                System.out.println(responseServer1);
                String[] session = responseServer1.split(":");
                String sesson1 = session[0]; // 004
                sesson2 = session[1];
                Log.e("session response", "session " + sesson2);
                SharedPreferences settings = getSharedPreferences("qibhr1", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("sesson2", sesson2);
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // txt.setText(responseServer);
        }
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }

}
