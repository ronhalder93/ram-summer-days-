package com.premium.rgbkeyboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String PREFS = "rgb_keyboard";
    private static final String THEME = "theme";
    private final String[] themes = {"Neon Mix", "Ice Blue", "Red & Gold", "Purple Glow"};
    private final String[] themeIds = {"neon", "ice", "fire", "purple"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(10, 11, 20));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(22), dp(30), dp(22), dp(24));

        TextView title = new TextView(this);
        title.setText("✦ PREMIUM RGB KEYBOARD");
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(12), 0, dp(8));
        root.addView(title, matchWrap());

        TextView subtitle = new TextView(this);
        subtitle.setText("Static neon colours • Smooth glow • Android typing");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setTextSize(13);
        subtitle.setPadding(0, 0, 0, dp(26));
        root.addView(subtitle, matchWrap());

        addButton(root, "1. Set Up Keyboard", v -> {
            try {
                startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
            } catch (Exception e) {
                showMessage("Open Android Settings and search for On-screen keyboard.");
            }
        });

        addButton(root, "2. Switch Keyboard", v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showInputMethodPicker();
        });

        addButton(root, "3. Keyboard Settings — RGB Styles", v -> showThemeDialog());

        TextView note = new TextView(this);
        note.setText("Enable Premium RGB Keyboard in Android settings, then choose it from the keyboard picker.");
        note.setTextColor(Color.rgb(165, 170, 195));
        note.setTextSize(12);
        note.setGravity(Gravity.CENTER);
        note.setPadding(dp(8), dp(22), dp(8), 0);
        root.addView(note, matchWrap());

        setContentView(root);
    }

    private void addButton(LinearLayout root, String label, android.view.View.OnClickListener click) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(14);
        b.setAllCaps(false);
        b.setTextColor(Color.WHITE);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(44, 48, 82)));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(56));
        p.setMargins(0, dp(7), 0, dp(7));
        root.addView(b, p);
        b.setOnClickListener(click);
    }

    private void showThemeDialog() {
        int selected = getSelectedThemeIndex();
        new AlertDialog.Builder(this)
            .setTitle("RGB Light Glow Style")
            .setSingleChoiceItems(themes, selected, (dialog, which) -> {
                getSharedPreferences(PREFS, MODE_PRIVATE).edit().putString(THEME, themeIds[which]).apply();
                dialog.dismiss();
            })
            .setNegativeButton("Close", null)
            .show();
    }

    private int getSelectedThemeIndex() {
        String id = getSharedPreferences(PREFS, MODE_PRIVATE).getString(THEME, "neon");
        for (int i = 0; i < themeIds.length; i++) if (themeIds[i].equals(id)) return i;
        return 0;
    }

    private void showMessage(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).setPositiveButton("OK", null).show();
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }
}
