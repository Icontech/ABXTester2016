package com.dsandberg.abxtester2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingScreenActivity extends Activity {
    private final int TIME_TO_WAIT = 1000;
    private TextView loadScreenTextView;
    private ABXTesterApplication app;
    private final String errorMsg = "Don't know what activity to start";
    private final String classNotFoundMsg = "Class not found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ABXTesterApplication)getApplicationContext();
        setContentView(R.layout.loading_screen_layout);
        loadScreenTextView = (TextView) findViewById(R.id.loadScreenTextView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loadScreenTextView.setText(extras.getString(app.getLoadScreenText()));
            initNextActivity(getApplicationContext().getPackageName()+extras.getString(app.getIntentToStart()));
        } else{
            loadScreenTextView.setText(errorMsg);
        }
    }

    private void initNextActivity(String intentToStart){
        Class<?> nextActivity = null;
        try {
            nextActivity = Class.forName(intentToStart);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),classNotFoundMsg,Toast.LENGTH_SHORT).show();
            finish();
        }
        if(nextActivity != null) {
            SimpleRunnable runnable = new SimpleRunnable(nextActivity);
            Handler handler = new Handler();
            handler.postDelayed(runnable, TIME_TO_WAIT);
        }
    }

    private class SimpleRunnable implements Runnable{
        private Class<?> nextActivity;

        private SimpleRunnable(Class<?> nextActivity){
            this.nextActivity = nextActivity;
        }
        public void run(){
            Intent intent = new Intent(getApplicationContext(), nextActivity);
            startActivity(intent);
            finish();
        }
    }
}
