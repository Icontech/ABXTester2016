package com.dsandberg.abxtester2016;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class TestRoundActivity extends Activity implements OnClickListener{
	private MediaPlayer mp;
	private int testRoundNumber;
	private String mediaPlayerDataSourcePath;
	private ABXTesterApplication app;
	private XbuttonLayout xbuttonLayout;
	private TestRound testRound;
	private HashMap<Integer, Sound> viewIdToSoundHashMap;
	private final String setAllAnswersMsg = "Please set all answers before proceeding";
	private final String finishedString = "FINISHED!";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ABXTesterApplication)getApplicationContext();
		mediaPlayerDataSourcePath = "android.resource://"+getApplicationContext().getPackageName()+"/";
		setContentView(R.layout.test_round_layout);
		TextView testRoundTextView = (TextView) findViewById(R.id.test_round_text);
		testRoundNumber = app.getIntFromPrefs(app.getTestRoundNumberString());;
		testRoundTextView.setText((testRoundNumber+1) + "/" + app.getTest().size());
		testRound = app.getTestRoundAtIndex(testRoundNumber);
		findViewById(R.id.a_btn).setOnClickListener(this);
		findViewById(R.id.b_btn).setOnClickListener(this);
		findViewById(R.id.submit_btn).setOnClickListener(this);
		xbuttonLayout = (XbuttonLayout) findViewById(R.id.xbuttons);
		viewIdToSoundHashMap = new HashMap<Integer,Sound>();
		viewIdToSoundHashMap.put(R.id.a_btn, testRound.getA());
		viewIdToSoundHashMap.put(R.id.b_btn, testRound.getB());
		mp = new MediaPlayer();
		setupXbuttonsAndAnswerBtns();
	}

	@Override
	protected void onStop(){
		super.onStop();
		releaseMediaPlayer();
	}

	@Override
	protected void onPause(){
		super.onPause();
		releaseMediaPlayer();
	}

	@Override
	protected void onStart(){
		super.onStart();
		if(mp == null){
			mp = new MediaPlayer();
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(mp == null){
			mp = new MediaPlayer();
		}
	}

	private void releaseMediaPlayer(){
		if(mp != null){
			if(mp.isPlaying())
				mp.stop();
			mp.release();
			mp = null;
		}
	}

	private void setupXbuttonsAndAnswerBtns(){
		for(int i = 1; i <= app.getXsoundsPerTestRound(); i++){
			View xBtn = xbuttonLayout.addXbtnAndAbToggleBtn(i, app.getXsoundsPerTestRound()+(i+1), i-1);
			viewIdToSoundHashMap.put(xBtn.getId(), testRound.getxSounds().get(i-1));
			xBtn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.submit_btn) {
			if(saveAnswers())
				goToNextTest();
		} else {
			Sound sound = viewIdToSoundHashMap.get(v.getId());
			playSound(sound.getId());
		}
	}

	private void playSound(int soundId){
		try {
			if(mp.isPlaying())
				mp.stop();
            mp.reset();
            mp.setDataSource(getApplicationContext(),
					Uri.parse(mediaPlayerDataSourcePath + soundId));
            mp.prepare();
            mp.start();
        }
        catch (Exception e) {
			e.printStackTrace();
        }
	}
	
	public boolean saveAnswers(){
		for(ToggleButton btn : xbuttonLayout.getToggleBtnList()){
			if(btn.getText() == xbuttonLayout.getAnswerBtnText()){
				Toast.makeText(this,setAllAnswersMsg,Toast.LENGTH_LONG).show();
				return false;
			}
		}
		for(int i = 0; i < app.getXsoundsPerTestRound(); i++){
			ToggleButton answerBtn = xbuttonLayout.getToggleBtnList().get(i);
			XSound xsound = testRound.getxSounds().get(i);
			xsound.setAnswerIsAsound(answerBtn.isChecked());
		}
		app.setTestRoundToTestInPrefs(testRound);
		return true;
	}
	
	private void goToNextTest(){
		Intent nextIntent;
		nextIntent = new Intent(TestRoundActivity.this, LoadingScreenActivity.class);
		if (testRoundNumber < app.getTest().size()-1){
			testRoundNumber++;
			app.addIntToPrefs(app.getTestRoundNumberString(), testRoundNumber);
			nextIntent.putExtra(app.getIntentToStart(), app.getTestRoundActivityString());
			nextIntent.putExtra(app.getLoadScreenText(),app.getRoundString()+(testRoundNumber+1));
		}else{
			nextIntent.putExtra(app.getIntentToStart(),app.getResultActivityString());
			nextIntent.putExtra(app.getLoadScreenText(),finishedString);
		}
		startActivity(nextIntent);
		finish();
	}
}
