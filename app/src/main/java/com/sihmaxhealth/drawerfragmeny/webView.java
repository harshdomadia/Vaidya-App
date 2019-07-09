package com.sihmaxhealth.drawerfragmeny;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class webView extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setRetainInstance(true);
        return inflater.inflate(R.layout.activity_web_view,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url="https://sangramdesai123.github.io/VideoChat/index.html#981d6";
        WebView view1=(WebView) view.findViewById(R.id.webview);
        view1.getSettings().setJavaScriptEnabled(true);
        view1.loadUrl(url);

    }
}
