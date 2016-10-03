package com.qib.qibhr1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class Fragment7profile extends Fragment {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private JSONObject json;
    private static final String TAG_PROFILE = "Profiles";
    private static final String TAG_NAME = "FirstName";
    private static final String TAG_LANAME = "LastName";
    private static final String TAG_GENDER = "Gender";
    private static final String TAG_CODE = "User_Code";
    private static final String TAG_DOB = "DOB";
    private static final String TAG_CEMAIL = "CommunicationEmail";
    private static final String TAG_PEMAIL = "PersonalEmail";
    private static final String TAG_MOBILENO = "MobilePhone";
    private static final String TAG_CITY = "City";
    private static final String TAG_DOORNO = "Door_No";
    private static final String TAG_STREET = "Street";
    private static final String TAG_STATE = "State";
    private static final String TAG_PINCODE = "Pincode";
    private static final String TAG_COUNTRY = "Country";

    private static final String TAG_ADDRESS = "Address";
    private static final String tag = "SignUp Instruction";
    public JSONObject c;
    // contacts JSONArray
    JSONArray contacts = null;

    public String Gender;

    ListView list;
    private String userid;
    private String responseServer, responseServer1, input_confirm1, input_current1, input_confirm, input_current;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    private TextInputLayout inputLayoutCurrent, inputLayouNew, inputLayoutConfirm;

    public Fragment7profile() {
    }

    private Button changepassword, summit, cancel;
    private EditText current_password, new_password, confirm_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile, container,
                false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tableHead));
        }
        list = (ListView) rootView.findViewById(R.id.list);
        changepassword = (Button) rootView.findViewById(R.id.change_password);
        changepassword.setVisibility(changepassword.GONE);

        changepassword.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.change_password);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int divierId = dialog.getContext().getResources()
                        .getIdentifier("android:id/titleDivider", null, null);
                View divider = dialog.findViewById(divierId);

                current_password = (EditText) dialog.findViewById(R.id.input_current);
                new_password = (EditText) dialog.findViewById(R.id.input_new);
                confirm_password = (EditText) dialog.findViewById(R.id.input_confirm);
                inputLayoutCurrent = (TextInputLayout) dialog.findViewById(R.id.input_layout_Current);
                inputLayouNew = (TextInputLayout) dialog.findViewById(R.id.input_layout_new);
                inputLayoutConfirm = (TextInputLayout) dialog.findViewById(R.id.input_layout_confirm);
                summit = (Button) dialog.findViewById(R.id.summit);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                summit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a=new_password.getText().toString().trim();
                        String current=current_password.getText().toString().trim();
                        String confirm=confirm_password.getText().toString().trim();
                        if(!current.isEmpty()) {
                            inputLayouNew.setErrorEnabled(false);
                            if (!a.isEmpty()) {
                                char c = a.charAt(0);
                                int firstchar = (int) c;
                                boolean firstvalid = false;
                                boolean passwordlength = false;
                                if (a != null && a.length() >= 4)
                                    passwordlength = true;

                                if (firstchar >= 65 && firstchar <= 91)
                                    firstvalid = true;
                                boolean atleastonenumber = false;
                                boolean atleastonelower = false;
                                boolean special = false;

                                for (int i = 1; i < a.length(); i++) {
                                    char charater = a.charAt(i);
                                    if (((int) charater) >= 97 && ((int) charater) <= 122)
                                        atleastonelower = true;

                                    else if (((int) charater) >= 48 && ((int) charater) <= 56)
                                        atleastonenumber = true;
                                    else
                                        special = true;
                                }
                                if (passwordlength == true && firstvalid == true && atleastonenumber == true && atleastonelower == true && special == false) {
                                    if(!a.equals(confirm)) {
                                            Toast.makeText(getActivity(),
                                                    R.string.passwordmatch,
                                                    Toast.LENGTH_SHORT).show();
                                    }else {
                                        inputLayouNew.setErrorEnabled(false);

                                        input_current = current_password.getText().toString().trim();
                                        try {
                                            input_current1 = URLEncoder.encode(Encryption.Encrypt(input_current, "9%9Vt3"), "UTF-8");
                                            System.out.println("encrypt value1 current password" + input_current1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(tag, "input_current " + input_current1);
                                        input_confirm = new_password.getText().toString().trim();
                                        try {
                                            input_confirm1 = URLEncoder.encode(Encryption.Encrypt(input_confirm, "9%9Vt3"), "UTF-8");
                                            Log.d(tag, "input_current new password " + input_current1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Log.d(tag, "input_confirm " + input_confirm1);

                                        AsyncT asyncT = new AsyncT();
                                        asyncT.execute();
                                        // do for submit
                                    }
                                } else {
                                    if (passwordlength == false)
                                        inputLayouNew.setError(getResources().getString(R.string.minmum));
                                    if (firstvalid == false)
                                        inputLayouNew.setError(getResources().getString(R.string.uppercase));
                                    else if (atleastonelower == false)
                                        inputLayouNew.setError(getResources().getString(R.string.lowercase));
                                    else if (atleastonenumber == false)
                                        inputLayouNew.setError(getResources().getString(R.string.neumericletter));
                                    else if (special == true)
                                        inputLayouNew.setError(getResources().getString(R.string.secialchared));
                                }
                            } else {
                                inputLayouNew.setError(getResources().getString(R.string.passwordfield));
                            }
                        }else {
                            inputLayoutCurrent.setError(getResources().getString(R.string.passwordfield));
                        }

                        // s.dismiss();
                    }
                });

                new_password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                       /* String current = new_password.getText().toString().trim();
                        confirm_password.setText(current);
                        requestFocus(new_password);*/
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    private void requestFocus(EditText current_password) {
                        if (current_password.requestFocus()) {
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }

                    }
                });


                dialog.show();
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        contactList = new ArrayList<HashMap<String, String>>();
        SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);

        userid = settings.getString("userid", null);
        // Calling async task to get json
        if (isNetworkAvailable()) {

            //handler.post(timedTask);
            new GetContacts().execute();
            new LogoutTask(getActivity()).execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            // set dialog message
            alertDialogBuilder
                    .setMessage(getActivity().getString(R.string.network))
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
                            getActivity().finish();
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

    private class GetContacts extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Fragment7profile.this.getString(R.string.plz));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String url = AppUrl.URL_COMMAN + "gaapi/ApiUserViewProfileIOS?userid=" + userid;
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST);

            Log.d("Profile url: ", "> " + url);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_PROFILE);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String FirstName = c.getString(TAG_NAME);
                        String LastName = c.getString(TAG_LANAME);
                        String salutation = c.getString("Salutation");
                        String middlename = c.getString("MiddleName");
                        Gender = c.getString(TAG_GENDER);
                        String User_Code = c.getString(TAG_CODE);
                        String DOB = c.getString(TAG_DOB);
                        String CommunicationEmail = c.getString(TAG_CEMAIL);
                        String PersonalEmail = c.getString(TAG_PEMAIL);
                        String MobilePhone = c.getString(TAG_MOBILENO);
                        String City = c.getString(TAG_CITY);
                        String Door_No = c.getString(TAG_DOORNO);
                        String Street = c.getString(TAG_STREET);
                        String State = c.getString(TAG_STATE);
                        String Pincode = c.getString(TAG_PINCODE);
                        String Country = c.getString(TAG_COUNTRY);

                        String Address = Door_No + ", " + Street + ", " + City + ", " + State + ", " + Country + "-" + Pincode + ".";
                        String Fullname = salutation + "." + FirstName + " " + LastName;
                        Log.d(tag, " Address " + Address);
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
                        // adding each child node to HashMap key => value
                        contact.put(TAG_NAME, Fullname);
                        contact.put(TAG_GENDER, Gender);
                        contact.put(TAG_CODE, User_Code);
                        contact.put(TAG_DOB, DOB);
                        contact.put(TAG_CEMAIL, CommunicationEmail);
                        contact.put(TAG_PEMAIL, PersonalEmail);
                        contact.put(TAG_MOBILENO, MobilePhone);
                        contact.put(TAG_ADDRESS, Address);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                String Gender1 = Gender;
                Log.d(tag, " Gender " + Gender);
                Log.d(tag, " Gender1 " + Gender1);

                if (Gender1.equals("Male")) {
                    // setContentView( R.layout.profile);
                    ImageView imgView = (ImageView) getView().findViewById(R.id.name2);
                    imgView.setImageResource(R.drawable.male);

                } else if (Gender1.equals("Female")) {
                    //setContentView( R.layout.profile);
                    ImageView imgView = (ImageView) getView().findViewById(R.id.name2);
                    imgView.setImageResource(R.drawable.female);

                }
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), contactList,
                        R.layout.profile_list, new String[]
                        {
                                TAG_NAME, TAG_NAME, TAG_GENDER,
                                TAG_CODE, TAG_DOB, TAG_CEMAIL, TAG_PEMAIL, TAG_MOBILENO, TAG_ADDRESS},
                        new int[]{R.id.name, R.id.name1,
                                R.id.email, R.id.mobile, R.id.dob, R.id.pmail, R.id.cmail, R.id.MobilePhone, R.id.add});


                list.setAdapter(adapter);
                changepassword.setVisibility(changepassword.VISIBLE);
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                // set dialog message
                alertDialogBuilder
                        .setMessage(getActivity().getString(R.string.server))
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
                                getActivity().finish();
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
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public void onBackPressed() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }


    class AsyncT extends AsyncTask<Void, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setIndeterminate(false);
            pDialog.setMessage(Fragment7profile.this.getString(R.string.plz));
            pDialog.setCancelable(true);
            pDialog.show();


        }
        @Override
        protected String doInBackground(Void... strings) {
            // replace with your url
            ServiceHandler sh = new ServiceHandler();
            String url = AppUrl.URL_COMMAN + "/gaapi/ResetPass?Uid=" + userid + "&ODP=" + input_current1 + "&NEWP=" + input_confirm1;
            System.out.println("survey_question-->"+url);
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);




            Log.d(tag, "url " + jsonStr);


            return jsonStr;
        }



        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            super.onPostExecute(result);
            int code;
            int errorcode = 0;
            String errormsg = null;
            try {

                JSONArray jr = new JSONArray(result);
                JSONObject jb = (JSONObject)jr.getJSONObject(0);
                String userid = jb.getString("UIDE");
                errorcode = jb.getInt("ErrorCode");
                errormsg = jb.getString("ErrorMSG");
                Log.d("change password   ",userid+errorcode+errormsg);

            }catch(Exception e)
            {
                e.printStackTrace();
            }
            if(errorcode == 00){
                Intent i = new Intent(getActivity(),
                        Login_Screen.class);
                startActivity(i);
                Toast.makeText(getActivity(),
                        errormsg, Toast.LENGTH_LONG)
                        .show();
            }
            else {
                Toast.makeText(getActivity(),
                        errormsg,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}