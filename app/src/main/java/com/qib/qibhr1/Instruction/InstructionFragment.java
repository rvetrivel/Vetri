package com.qib.qibhr1.Instruction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qib.qibhr1.AppUrl;
import com.qib.qibhr1.LogoutTask;
import com.qib.qibhr1.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstructionFragment extends Fragment {
	ArrayList<Instruction> actorsList;
	ArrayList<String> key =new ArrayList<>();
	Instruction_Adapter adapter;
	private String  lang,userid;

	ExpandableListView expListView;
	private ImageView img, img1;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
TextView Noactivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.instructions, null);
		//TextView.class.cast(rootView.findViewById(R.id.labelText)).setText("Earth");
      Noactivity=(TextView)rootView.findViewById(R.id.noactivity);
		Noactivity.setVisibility(Noactivity.GONE);
		// preparing list data
		actorsList = new ArrayList<Instruction>();
		SharedPreferences settings = getActivity().getSharedPreferences("qibhr1", 0);
		lang = settings.getString("lang", null);
		userid=settings.getString("userid", null);
		if(isNetworkAvailable()){
		new LogoutTask(getActivity()).execute();
		new JSONAsyncTask().execute(AppUrl.URL_COMMAN+"gaapi/ApiAssementInstruction?Type=GN&lang=" + lang);
		System.out.println("Instruction_uri--->"+AppUrl.URL_COMMAN+"gaapi/ApiAssementInstruction?Type=GN&lang=" + lang);
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

		ListView listview = (ListView)rootView.findViewById(R.id.list);
		adapter = new Instruction_Adapter(getActivity(), R.layout.instructions_list, actorsList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long id) {

				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), actorsList.get(position).getName(), Toast.LENGTH_LONG).show();
			}
		});

		return rootView;
	}
	class JSONAsyncTask extends AsyncTask<String, Void, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage(InstructionFragment.this.getString(R.string.plz));
			//dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected String doInBackground(String... urls) {
			String data=null;
			try {

				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();
            String servercheck=String.valueOf(status);
				if (servercheck.equals("200")) {
					HttpEntity entity = response.getEntity();
					data = EntityUtils.toString(entity);

					return data;
				}else{
					servercheck=null;
					return servercheck;

				}

				//------------------>>

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return data;
		}

		private void connotconnevtserver() {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

			// set dialog message
			alertDialogBuilder
					.setMessage(getActivity().getString(R.string.server))
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

		protected void onPostExecute(String result) {
			dialog.cancel();
			if (result==null){
				connotconnevtserver();
			}else {
				adapter.notifyDataSetChanged();
				try {
					JSONObject jsono = new JSONObject(result);
					JSONArray jarray = jsono.getJSONArray("QuizInstructions");
					System.out.println("QuizInstructions--->" + jarray);
					if (jarray.length() == 0) {
						Noactivity.setVisibility(Noactivity.VISIBLE);
					} else {
						Noactivity.setVisibility(Noactivity.GONE);
						int l = 1;
						for (int i = 0; i < jarray.length(); i++) {
							JSONObject object = jarray.getJSONObject(i);
							System.out.println("Instruction_jarray--->" + object);
							Instruction actor = new Instruction();
							actor.setkey1(object.getString("key"+l));
							actor.setkey2(object.getString("key"+l));
							actor.setkey3(object.getString("key"+l));
							//actor.setCountry(object.getString("country"));
							actor.setkey4(object.getString("key"+l));
							actor.setkey5(object.getString("key"+l));
							actor.setkey6(object.getString("key"+l));
							actor.setKey7(object.getString("key"+l));


							/*for (int l = 1; l <= conlength++; l++) {

								key.add(object.getString("key" + l));
								key.removeAll(Arrays.asList(null, ""));
								System.out.println("key array-->" + key);
								actor.setkey(key);
								actorsList.add(actor);
								System.out.println("actorsList array-->" + actorsList);
							}*/

						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public boolean isNetworkAvailable() {
		ConnectivityManager manager =
				(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			// Network is present and connected
			isAvailable = true;
		}
		return isAvailable;
	}

}