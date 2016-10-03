package com.qib.qibhr1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ezhilarasan on 29-Mar-16.
 */
public class LogoutTask extends AsyncTask<Void, Void, String> {
    int status;
    HttpResponse response = null;
    private String CurentTime, userid, sessionid, devid, responseServer;
    public static int logout;

    private Context mContext;

    public LogoutTask(Context context) {
        mContext = context;
    }
    private ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(mContext);
        pDialog.setIndeterminate(false);
        pDialog.setMessage(mContext.getString(R.string.plz));
        pDialog.setCancelable(false);
        pDialog.show();


    }

    @Override
    protected String doInBackground(Void... voids) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        CurentTime = df.format(now);
        System.out.println("Current Time" + CurentTime);
        Log.d("gmtTime: logouttask", "> " + CurentTime);
        SharedPreferences settings = mContext.getSharedPreferences("qibhr1", 0);
        userid = settings.getString("userid", null);
        sessionid = settings.getString("sesson2", null);
        devid = settings.getString("Android_device", null);
        Log.d("sessionid: logout task", "> " + sessionid);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(AppUrl.URL_COMMAN+"gaapi/Logouttime?sessionid=" + sessionid + "&logouttime=" + CurentTime + "&devid=" + devid + "&uid=" + userid + "&os=3");
        System.out.println("Logout url logout task url ====>" + String.valueOf(httppost));

        try {

            JSONObject jsonobj = new JSONObject();

            jsonobj.put("CurentTime", CurentTime);
            jsonobj.put("sessionid", sessionid);
            jsonobj.put("devid", devid);
            jsonobj.put("userid", userid);
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
                Log.e("response", "response lagout task----->>>" + responseServer);

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
        pDialog.dismiss();
        super.onPostExecute(status);
        if (status != null) {
            Log.e("response", "response logout task ----->" + status);
            status = status.replace('"', ' ');
            String sts = status.toString().trim();
            Log.e("response", "response logout task ----->" + sts);
            if (sts.equals("2")) {
                logout = 2;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                // set dialog message
                alertDialogBuilder
                        .setMessage(mContext.getString(R.string.sure1))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences settings = mContext.getSharedPreferences("qibhr1", 0);
                                settings.edit().remove("sesson2").commit();
                                Intent i = new Intent(mContext,
                                        Login_Screen.class);
                                mContext.startActivity(i);
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
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
        } else {

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
