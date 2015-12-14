package com.credit.eshop;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.credit.imagepart.ImageLoader;

public class FinalItem extends Activity {
	Button bBuy;
	ImageView picArt;
	TextView txtTest;
	TextView txtName;
	TextView txtPrice;
	TextView txtKolicina;
	String id;
	int loader = R.drawable.ex;
	String image_url;
	TextView passedIntent;
	JSONArray products = null;
	JSONParser jsonParser = new JSONParser();
	private static String url_product_detail = "http://www.tabiat.info/php_data/get_last_detail.php";
	private ProgressDialog pDialog;

	// JSON Node names
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_NAZIV_ARTIKLA = "naziv_artikla";
	private static final String TAG_CIJENA = "cijena";
	private static final String TAG_KOLICINA = "kolicina";
	private static final String TAG_SLIKA_ARTIKLA = "slika_artikla";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_item);
		
		bBuy=(Button)findViewById(R.id.bBuy);
		
		bBuy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent detailIntent = new Intent(getApplicationContext(),
						Login.class);
				// sending pid to next activity as passed intent

				// starting new activity and expecting some response back
				startActivityForResult(detailIntent, 100);
			}
		});

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// passing intent with data
		Intent detailIntent = getIntent();
		id = (detailIntent.getStringExtra("idpassed"));
		txtName = (TextView) findViewById(R.id.inputName);
		txtPrice = (TextView) findViewById(R.id.textPrice);
		txtKolicina = (TextView) findViewById(R.id.textPieces);
		picArt = (ImageView) findViewById(R.id.logo);

		txtTest = (TextView) findViewById(R.id.productQuantity);
		new GetProductDetails().execute();
		// Loading products in Background Thread
		Log.d("OnCreate", "error here");
		// Get listview

	}

	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FinalItem.this);
			pDialog.setMessage("Loading item details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			runOnUiThread(new Runnable() {
				public void run() {
					try {
						// getting JSON string from URL
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", id));
						JSONObject json = jsonParser.makeHttpRequest(
								url_product_detail, "GET", params);
						Log.d("Single Product Details", json.toString());

						JSONArray productObj = json.getJSONArray(TAG_PRODUCT); // JSON
																				// Array
						JSONObject product = productObj.getJSONObject(0);

						// product with this pid found
						// Edit Text
						txtName.setText(product.getString(TAG_NAZIV_ARTIKLA));
						txtPrice.setText(product.getString(TAG_CIJENA));
						txtKolicina.setText(product.getString(TAG_KOLICINA));
						txtTest.setText(product.getString(TAG_SLIKA_ARTIKLA));
						image_url = product.getString(TAG_SLIKA_ARTIKLA);
						// display product data in EditText
						ImageLoader imgLoader = new ImageLoader(
								getApplicationContext());

						// whenever you want to load an image from url
						// call DisplayImage function
						// url - image url to load
						// loader - loader image, will be displayed before
						// getting image
						// image - ImageView
						imgLoader.DisplayImage(image_url, picArt);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
		
}}
