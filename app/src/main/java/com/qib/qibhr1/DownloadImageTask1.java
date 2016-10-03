package com.qib.qibhr1;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by vetrivel on 12/30/2015.
 */
public class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {
    ImageView personality;
    private Context mContext;
    private ProgressDialog pDialog;

    // psychometric image loading...
    public DownloadImageTask1(ImageView personality, Context activity) {
        this.personality = personality;
        this.mContext = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.plz));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {

            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        pDialog.dismiss();
        personality.setImageBitmap(result);
    }
}
