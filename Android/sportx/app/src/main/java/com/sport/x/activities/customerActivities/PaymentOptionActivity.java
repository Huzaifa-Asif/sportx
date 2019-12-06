package com.sport.x.activities.customerActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.sport.x.R;
import com.sport.x.activities.menu.Menu;

public class PaymentOptionActivity extends Menu {
    private WebView mywebview;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_c_payment_option);
        setTitle("Payment Options");
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        Log.wtf("id",id);

        mywebview = (WebView)findViewById(R.id.webView);

        mywebview.setVisibility(View.INVISIBLE);
        WebSettings webSettings = mywebview.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setAppCacheEnabled(true);
        mywebview.getSettings().setDomStorageEnabled(true);

        mywebview.setWebChromeClient(new WebChromeClient());
        mywebview.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);

                mywebview.setVisibility(View.VISIBLE);
            }



        });

        mywebview.loadUrl("https://sportx.com.pk/sportx_admin/#/payment/paypal/"+id);


    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, BookingManagementActivity.class);
        startActivity(intent);
        finish();

    }
}