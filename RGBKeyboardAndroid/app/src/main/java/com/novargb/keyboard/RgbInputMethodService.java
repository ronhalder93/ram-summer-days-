package com.novargb.keyboard;

import android.inputmethodservice.InputMethodService;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

public class RgbInputMethodService extends InputMethodService {
 private WebView web;
 @Override public View onCreateInputView() {
  web=new WebView(this); web.setBackgroundColor(Color.rgb(10,10,18)); WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(false); s.setAllowFileAccess(true); s.setAllowContentAccess(false); web.addJavascriptInterface(new Bridge(),"AndroidKeyboard"); web.setWebViewClient(new WebViewClient()); web.loadUrl("file:///android_asset/keyboard.html"); return web;
 }
 @Override public void onDestroy(){if(web!=null){web.destroy();web=null;}super.onDestroy();}
 private class Bridge {
  @JavascriptInterface public void commitText(String text){String safe=text==null?"":text;new Handler(Looper.getMainLooper()).post(()->{InputConnection ic=getCurrentInputConnection();if(ic!=null)ic.commitText(safe,1);});}
  @JavascriptInterface public void backspace(){new Handler(Looper.getMainLooper()).post(()->{InputConnection ic=getCurrentInputConnection();if(ic!=null)ic.deleteSurroundingText(1,0);});}
  @JavascriptInterface public void enter(){new Handler(Looper.getMainLooper()).post(()->{InputConnection ic=getCurrentInputConnection();if(ic!=null)ic.commitText("\n",1);});}
 }
}
