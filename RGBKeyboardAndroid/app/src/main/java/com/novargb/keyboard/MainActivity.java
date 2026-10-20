package com.novargb.keyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends Activity {
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  LinearLayout root=new LinearLayout(this); root.setOrientation(1); root.setPadding(28,42,28,28); root.setGravity(Gravity.CENTER_HORIZONTAL); root.setBackgroundColor(Color.rgb(12,12,20));
  TextView title=new TextView(this); title.setText("RGB KEYBOARD PREMIUM"); title.setTextColor(Color.WHITE); title.setTextSize(25); title.setGravity(Gravity.CENTER); root.addView(title,new LinearLayout.LayoutParams(-1,-2));
  TextView desc=new TextView(this); desc.setText("Gaming RGB • Number row • Colour effects\n\n1. Enable the keyboard\n2. Choose RGB Keyboard\n3. Open any app and start typing"); desc.setTextColor(Color.LTGRAY); desc.setTextSize(16); desc.setGravity(Gravity.CENTER); LinearLayout.LayoutParams dp=new LinearLayout.LayoutParams(-1,-2); dp.topMargin=24; root.addView(desc,dp);
  Button enable=new Button(this); enable.setText("1. Enable RGB Keyboard"); enable.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))); root.addView(enable,new LinearLayout.LayoutParams(-1,-2));
  Button choose=new Button(this); choose.setText("2. Choose Keyboard"); choose.setOnClickListener(v->{InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); if(imm!=null) imm.showInputMethodPicker();}); LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.topMargin=12; root.addView(choose,cp);
  setContentView(root);
 }
}
