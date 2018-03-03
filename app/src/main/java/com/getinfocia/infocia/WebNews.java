package com.getinfocia.infocia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.getinfocia.infocia.util.JsonUtils;


@SuppressLint("SetJavaScriptEnabled")
public class WebNews extends Activity {
	
	WebView webview;
	ProgressBar bar;
	String Link;
	ImageView imgNoInternet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webnews);

		webview=(WebView)findViewById(R.id.webView1);
		imgNoInternet = (ImageView)findViewById(R.id.imgNoInternet);
		bar=(ProgressBar)findViewById(R.id.load);
		Intent i=getIntent();
		Link=i.getStringExtra("link");
		if (JsonUtils.isNetworkAvailable(WebNews.this))
		{
			imgNoInternet.setVisibility(View.GONE);
			loadUrl(Link);

		}else {
			imgNoInternet.setVisibility(View.VISIBLE);
			webview.setVisibility(View.GONE);
			bar.setVisibility(View.GONE);
		}

	}
	
	private void loadUrl(String URL) {
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setAppCacheMaxSize(1024 * 1024 * 8);

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					
					bar.setVisibility(View.GONE);
					webview.setVisibility(View.VISIBLE);
				}
			}
		});
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(WebNews.this, description, Toast.LENGTH_SHORT)
				.show();
			}
		});
		webview.loadUrl(URL);
	}

}
