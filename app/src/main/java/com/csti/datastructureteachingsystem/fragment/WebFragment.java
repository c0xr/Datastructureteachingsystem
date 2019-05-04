package com.csti.datastructureteachingsystem.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.csti.datastructureteachingsystem.R;

public class WebFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.web,container,false);
        WebView webView=view.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);//设置浏览器属性，让WebView支持JavaScript脚本
        webView.setWebViewClient(new WebViewClient());//当需要从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前WebView中显示，而不是打开系统浏览器
        webView.loadUrl("https://tool.lu/coderunner/");//传入相应的网址就可以打开了
        return view;
    }
}
