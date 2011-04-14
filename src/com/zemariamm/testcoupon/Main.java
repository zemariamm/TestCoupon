package com.zemariamm.testcoupon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zemariamm.coupons.Coupon;
import com.zemariamm.coupons.CouponPreferences;
import com.zemariamm.coupons.ProcessCoupon;

public class Main extends Activity implements ProcessCoupon{
    
	TextView text;
    Button btn;
    Button btnFull;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);
        btnFull = (Button) findViewById(R.id.btnFull);
        if (CouponPreferences.isUnblocked(this))
        	text.setText("Full Version");
        else
        	text.setText("Limited Version");
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!CouponPreferences.isUnblocked(Main.this))
					launchCoupon();
			}
		});
        
        btnFull.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (CouponPreferences.isUnblocked(Main.this))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
					builder.setMessage("You have access to the full version")
				       .setCancelable(false)
				       .setPositiveButton("Nice", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   text.setText("Full Version");
				           }
				       	});
					AlertDialog alert = builder.create();
					alert.show();
				}
				else
				{
					Coupon.createForbiddenDialog(Main.this, "To access this feature you must redeem your coupon","com.zemariamm.wickedfull");
				}
				
			}
		});
    }
	public void launchCoupon() {
		// working example - dkbm1ha5
		Coupon.createCouponDialog(Main.this, Main.this, "testcoupons", "http://droidcoupons.appspot.com/check/");
	}
	public void processInvalidCoupon() {
		Log.d("TestCoupon", "INvalid coupon!");
	}
	public void processValidCoupon() {
		Log.d("TestCoupon", "Valid Coupon!");
		CouponPreferences.markUnblocked(this);
	}
	
	
}