package com.qib.qibhr1.Instruction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qib.qibhr1.R;

import java.io.InputStream;
import java.util.ArrayList;

public class Instruction_Adapter extends ArrayAdapter<Instruction> {
	ArrayList<Instruction> actorList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public Instruction_Adapter(Context context, int resource, ArrayList<Instruction> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		actorList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.key1 = (TextView) v.findViewById(R.id.key1);
			holder.key2 = (TextView) v.findViewById(R.id.key2);
			holder.key3 = (TextView) v.findViewById(R.id.key3);
			holder.key4 = (TextView) v.findViewById(R.id.key4);
			holder.key5 = (TextView) v.findViewById(R.id.key5);
			holder.key6 = (TextView) v.findViewById(R.id.key6);
			holder.key7 = (TextView) v.findViewById(R.id.key7);
			holder.key8 = (TextView) v.findViewById(R.id.key8);
			holder.key9 = (TextView) v.findViewById(R.id.key9);
			holder.no1 = (TextView) v.findViewById(R.id.no1);
			holder.no2 = (TextView) v.findViewById(R.id.no2);
			holder.no3 = (TextView) v.findViewById(R.id.no3);
			holder.no4 = (TextView) v.findViewById(R.id.no4);
			holder.no5 = (TextView) v.findViewById(R.id.no5);
			holder.no6 = (TextView) v.findViewById(R.id.no6);
			holder.no7 = (TextView) v.findViewById(R.id.no7);
			holder.no8 = (TextView) v.findViewById(R.id.no8);
			holder.no9 = (TextView) v.findViewById(R.id.no9);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		int positionno = position + 1;
		Log.d("position", "getView: positionno "+positionno);

		holder.key1.setText((CharSequence) actorList.get(position).getkey());
		/*holder.key2.setText(actorList.get(position).getkey2());
		holder.key3.setText(actorList.get(position).getkey3());
		holder.key4.setText(actorList.get(position).getkey4());
		holder.key5.setText(actorList.get(position).getkey5());
		holder.key6.setText(actorList.get(position).getkey6());
		holder.key7.setText(actorList.get(position).getKey7());
		holder.key8.setText(actorList.get(position).getKey8());
		holder.key9.setText(actorList.get(position).getKey9());*/

//		holder.key10.setText(actorList.get(position).getKey10());
		//holder.key11.setText(actorList.get(position).getKey11());

		//holder.no10.setText(position);
		//holder.no11.setText(position);
		return v;

	}

	static class ViewHolder {
		public TextView key1;
		public TextView key2;
		public TextView key3;
		public TextView key4;
		public TextView key5;
		public TextView key6;
		public TextView key7;
		public TextView key8;
		public TextView key9;
		public TextView key10;
		public TextView key11;
		public TextView key12;
		public TextView no1;
		public TextView no2;
		public TextView no3;
		public TextView no4;
		public TextView no5;
		public TextView no6;
		public TextView no7;
		public TextView no8;
		public TextView no9;
		public TextView no10;
		public TextView no11;
		public TextView no12;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

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
			bmImage.setImageBitmap(result);
		}

	}
}