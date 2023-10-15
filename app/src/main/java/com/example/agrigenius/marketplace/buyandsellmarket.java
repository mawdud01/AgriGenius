package com.example.agrigenius.marketplace;
// ei page r er activity 2 tai delete kore dite hobe
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.agrigenius.MainActivity;
import com.example.agrigenius.R;

public class buyandsellmarket extends AppCompatActivity {
    WebView webView;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyandsellmarket);


        webView = findViewById(R.id.webwiew);
        progressBar=findViewById(R.id.progressbar);






        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://greenvillmarket.blogspot.com/p/post-ad.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Log JavaScript errors here for debugging
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Log the URL requests here for debugging
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

//progressbar er kaj
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.INVISIBLE);
                setTitle("Loading......");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                //setTitle(view.getTitle());
            }
        });





    }
//function for webviewclient
    private class MywebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }



//back button er kaj
    @Override
    public void onBackPressed() {
        if(webView.isFocused() && webView.canGoBack())
        {
            webView.goBack();
        }else{
            //(back button a click korle kaj hobena)  super.onBackPressed();

            Intent intent = new Intent(buyandsellmarket.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }





}
