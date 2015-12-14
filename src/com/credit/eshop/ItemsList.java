package com.credit.eshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ItemsList extends Activity {
	// All static variables
	private static String URL = "http://www.tabiat.info/php_data/get_articles_detail.php";
	// JSON Node names
	static final String TAG_PRODUCTS = "products";
	static final String TAG_ID = "id";
	static final String TAG_NAZIV_ARTIKLA = "naziv_artikla";
	static final String TAG_CIJENA = "cijena";
	static final String TAG_SLIKA_ARTIKLA = "slika_artikla";
	String pid;
	JSONParser jsonParser = new JSONParser();
	JSONArray products = null;

	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		Intent in = getIntent();
		pid = (in.getStringExtra("idpassed"));
		ArrayList<HashMap<String, String>> productsList = new ArrayList<HashMap<String, String>>();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pid", pid));
		// getting JSON string from URL
		JSONObject json = jsonParser.makeHttpRequest(URL, "GET", params);
		Log.d("Products by category", json.toString());

		try {
			// products found
			// Getting Array of Products
			products = json.getJSONArray(TAG_PRODUCTS);

			// looping through All Products
			for (int i = 0; i < products.length(); i++) {
				JSONObject c = products.getJSONObject(i);

				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAZIV_ARTIKLA);
				String cijena = c.getString(TAG_CIJENA);
				String slike = c.getString(TAG_SLIKA_ARTIKLA);
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NAZIV_ARTIKLA, name);
				map.put(TAG_CIJENA, cijena);
				map.put(TAG_SLIKA_ARTIKLA, slike);
				// adding HashList to ArrayList
				productsList.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		list = (ListView) findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(this, productsList);
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String passedId = ((TextView) view.findViewById(R.id.id))
						.getText().toString();

				// Starting new intent
				Intent detailIntent = new Intent(getApplicationContext(),
						FinalItem.class);
				// sending pid to next activity as passed intent
				detailIntent.putExtra("idpassed", passedId);

				// starting new activity and expecting some response back
				startActivityForResult(detailIntent, 100);
			}

		});
	}
}