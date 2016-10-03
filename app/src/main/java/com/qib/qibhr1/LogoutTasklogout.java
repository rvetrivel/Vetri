package com.qib.qibhr1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
public class LogoutTasklogout extends AsyncTask<Void, Void, String> {
    int status;
    HttpResponse response = null;
    private String CurentTime, userid, sessionid, devid, responseServer;
    public static int logout;

    private Context mContext;

    public LogoutTasklogout(Context context) {
        mContext = context;
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
        // HttpPost httppost = new HttpPost("http://www.ems.belmonttek.com/gaapi/Logouttime?sessionid="+sessionid+"&logouttime="+CurentTime+"&devid="+devid+"&uid="+userid+"&os=1");
        HttpPost httppost = new HttpPost(AppUrl.URL_COMMAN+"gaapi/Logouttime?sessionid=" + sessionid + "&logouttime=" + CurentTime + "&devid=" + devid + "&uid=" + userid + "&os=1");

        // HttpPost httppost = new HttpPost("http://www.ems.belmonttek.com/gaapi/Logouttime?sessionid="+sessionid+"&logouttime="+CurentTime+"&devid="+devid+"&uid="+userid+"&os=1");
        // HttpPost httppost = new HttpPost("http://10.20.30.105:2020/gaapi/Logouttime?sessionid="+sessionid+"&logouttime=11:30:00");
        System.out.println("Logout url logout task ====>" + AppUrl.URL_COMMAN+"gaapi/Logouttime?sessionid=" + sessionid + "&logouttime=" + CurentTime + "&devid=" + devid + "&uid=" + userid + "&os=1");

        String result1 = "";
        try {

            JSONObject jsonobj = new JSONObject();

            jsonobj.put("CurentTime", CurentTime);
            jsonobj.put("sessionid", sessionid);
            jsonobj.put("sessionid", devid);
            jsonobj.put("sessionid", userid);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

            Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

            String jsongb = "";
            jsongb = nameValuePairs.toString();
            StringEntity se = new StringEntity(jsongb);
            System.out.println("converting json hole responce send " + jsongb);
            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");


            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            status = response.getStatusLine().getStatusCode();
            InputStream inputStream = null;
            if (status == 200) {

                inputStream = response.getEntity().getContent();
                responseServer = convertInputStreamToString(inputStream);

                Log.e("response", "response lagout task----->>>" + responseServer);

            } else if (status != 200) {
                responseServer = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseServer;
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

    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        //int sts=Integer.valueOf(status);
        // txt.setText(responseServer);
        if (status == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mContext);
            // set dialog message
            alertDialogBuilder
                    .setMessage(mContext.getString(R.string.server))
                    .setCancelable(false)
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

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
        } else {
            Intent i = new Intent(mContext, Language_screen.class);
            mContext.startActivity(i);
            SharedPreferences pref = mContext.getSharedPreferences("Qib", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Instruction
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(mContext, mContext.getString(R.string.sucses), Toast.LENGTH_LONG).show();
        }

    }

    /* public void execute(Instruction activity) {
         mContext=activity;
     }*/
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
