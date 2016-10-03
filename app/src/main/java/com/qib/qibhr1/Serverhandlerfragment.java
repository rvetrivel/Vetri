package com.qib.qibhr1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezhilarasan on 19-Mar-16.
 */
public class Serverhandlerfragment extends AsyncTask<String, String, JSONObject> {
    public static String userid;
    public AsyncResponse delegate = null;
    private String tag = "Server Handler";
    private String email, password, lang, username, regId;
    private JSONParser jsonParser = new JSONParser();

    private Context mContext;
    public JSONObject json;

    public Serverhandlerfragment(Context context) {
        mContext = context;
    }

    boolean isAvailable;
    private ProgressDialog pDialog;

    /*@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext.getApplicationContext());
        pDialog.setMessage("server");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }*/

    /**
     * Creating student
     */


    protected JSONObject doInBackground(String... args) {
        SharedPreferences settings1 = mContext.getSharedPreferences("Qib", 0);
        username = settings1.getString("userName", null);
        password = settings1.getString("password1", null);
        //lang=settings1.getString("lang",null);

        Log.d(tag, "suname " + username);
        Log.d(tag, "spassword " + password);


        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", username));
        params.add(new BasicNameValuePair("password", password));
        String url = AppUrl.URL_COMMAN+"gaapi/ApiLogin?lang=en&username=" + username + "&password=" + password;
                    /*try {
                        InetAddress.getByName(url).isReachable(timeout);
					} catch (IOException e) {
						e.printStackTrace();
					}*/

        json = jsonParser.makeHttpRequest(
                url, "POST", params);

        Log.d(tag, "url " + url);

        return json;
    }

    protected void onPostExecute(JSONObject json) {
        //pDialog.dismiss();
        delegate.processFinish(json);

    }

    /*public Context getActivity() {
        return mContext;
    }*/
}
