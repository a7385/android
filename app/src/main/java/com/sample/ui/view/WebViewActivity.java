package com.sample.ui.view;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.ui.R;

public class WebViewActivity extends AppCompatActivity {

    String url = "https://www.google.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String scheme = getIntent().getScheme();
        if (scheme != null && scheme.equals("test")) {
            url = getIntent().getData().getQueryParameter("url");
        }

        setContentView(R.layout.activity_webview);
        WebView wv = findViewById(R.id.web);

        //no catch
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //參數值可設定true或是false,默認值是false,此參數值設定為true是為了當內容大於viewport時，系統將會自動縮小內容以適屏幕寬度。
        wv.getSettings().setLoadWithOverviewMode(true);
        //參數可設定true或是false,設定true時會將viewport的meta tag啟動。
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setJavaScriptEnabled(true);
        //不显示webview缩放按钮
        wv.getSettings().setBuiltInZoomControls(true);

        StringBuilder userAgent = new StringBuilder(wv.getSettings().getUserAgentString());
        userAgent.append("_userAgent");
        wv.getSettings().setUserAgentString(userAgent.toString());
        wv.loadUrl(url);
    }
}