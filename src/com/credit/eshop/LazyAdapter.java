package com.credit.eshop;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.imagepart.ImageLoader;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_list_row, null);

		TextView artName = (TextView) vi.findViewById(R.id.textArtName); // title
        TextView cijena = (TextView)vi.findViewById(R.id.textPrice); // artist name
        TextView passedID = (TextView)vi.findViewById(R.id.id); // artist name

        ImageView slikaArtikla=(ImageView)vi.findViewById(R.id.imageSmall); // thumb image
		HashMap<String, String> artikal = new HashMap<String, String>(); //song mijenjana sa artikal
		artikal = data.get(position);

		// Setting all values in listview
		artName.setText(artikal.get(ItemsList.TAG_NAZIV_ARTIKLA));
		cijena.setText(artikal.get(ItemsList.TAG_CIJENA));
		passedID.setText(artikal.get(ItemsList.TAG_ID));

        imageLoader.DisplayImage(artikal.get(ItemsList.TAG_SLIKA_ARTIKLA),slikaArtikla);
		return vi;
	}
}