package com.sdz.hilloworld.ui.aboutMe;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.sdz.hilloworld.R;
import com.sdz.hilloworld.global.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutMeFragment extends Fragment {

    private static final String TAG = "AboutMeFragment";

    private AboutMeViewModel aboutMeViewModel;

    private TextView tv_blog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutMeViewModel = ViewModelProviders.of(this).get(AboutMeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about_me,container,false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }



    private void init(View view){
        tv_blog = getActivity().findViewById(R.id.blog);
        tv_blog.setClickable(true);

//        webView = view.findViewById(R.id.web_blog);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//            //网上说这样开启https, 然而...
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //handler.cancel(); // Android默认的处理方式
//                handler.proceed();  // 接受所有网站的证书
//                //handleMessage(Message msg); // 进行其他处
//            }
//        });
        tv_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlogActivity.class);
                startActivity(intent);
            }
        });


    }
}
