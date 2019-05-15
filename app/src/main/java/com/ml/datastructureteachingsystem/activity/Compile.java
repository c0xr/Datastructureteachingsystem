package com.ml.datastructureteachingsystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ml.datastructureteachingsystem.R;

public class Compile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);
        WebView webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);//设置浏览器属性，让WebView支持JavaScript脚本
        webView.setWebViewClient(new WebViewClient());//当需要从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器
        webView.loadUrl("https://tool.lu/coderunner/");//传入相应的网址就可以打开了
    }
}
