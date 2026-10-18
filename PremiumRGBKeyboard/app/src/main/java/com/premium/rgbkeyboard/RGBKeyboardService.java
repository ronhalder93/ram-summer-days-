package com.premium.rgbkeyboard;

import android.inputmethodservice.InputMethodService;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.os.Handler;
import android.os.Looper;

public class RGBKeyboardService extends InputMethodService {
    private WebView webView;
    private final Handler main = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateInputView() {
        webView = new WebView(this);
        webView.setBackgroundColor(0xFF090B12);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new KeyboardBridge(), "AndroidKeyboard");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        return webView;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        if (webView != null) {
            webView.post(() -> webView.evaluateJavascript(
                "if(window.applyNativeTheme){window.applyNativeTheme(AndroidKeyboard.getTheme());}", null));
        }
    }

    private void onMain(Runnable task) {
        main.post(task);
    }

    public class KeyboardBridge {
        @JavascriptInterface
        public void type(String value) {
            if (value == null) return;
            onMain(() -> {
                InputConnection ic = getCurrentInputConnection();
                if (ic != null) ic.commitText(value, 1);
            });
        }

        @JavascriptInterface
        public void backspace() {
            onMain(() -> {
                InputConnection ic = getCurrentInputConnection();
                if (ic != null) ic.deleteSurroundingText(1, 0);
            });
        }

        @JavascriptInterface
        public void enter() {
            onMain(() -> {
                InputConnection ic = getCurrentInputConnection();
                if (ic == null) return;
                EditorInfo info = getCurrentInputEditorInfo();
                if (info != null && info.imeOptions != 0) {
                    int action = info.imeOptions & EditorInfo.IME_MASK_ACTION;
                    if (action != EditorInfo.IME_ACTION_NONE && action != EditorInfo.IME_ACTION_UNSPECIFIED) {
                        ic.performEditorAction(action);
                        return;
                    }
                }
                ic.commitText("\n", 1);
            });
        }

        @JavascriptInterface
        public String getTheme() {
            return getSharedPreferences("rgb_keyboard", MODE_PRIVATE).getString("theme", "neon");
        }
    }
}
