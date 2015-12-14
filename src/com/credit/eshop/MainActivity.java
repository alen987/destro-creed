package com.credit.eshop;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Button bBrowse;
		bBrowse=(Button)findViewById(R.id.bBrowseCat);
		bBrowse.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isNetworkAvailable(this)) {
			startActivity(new Intent("com.credit.eshop.CATEGORY"));

	    } else {
	    	Toast.makeText(getApplicationContext(), "Network is not avalible!", Toast.LENGTH_SHORT).show();
	    }

	}
	public boolean isNetworkAvailable(Context context) {
	     ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    if (connectivity == null) {
	        return false;
	    } else {
	        NetworkInfo[] info = connectivity.getAllNetworkInfo();
	        if (info != null) {
	            for (int i = 0; i < info.length; i++) {
	                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
}
