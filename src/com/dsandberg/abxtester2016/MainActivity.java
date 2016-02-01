package com.dsandberg.abxtester2016;

import java.lang.reflect.Field;
import java.util.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	ABXTesterApplication app;
	private ArrayList<TestRound> test;
	private final String unevenSoundNumberMsg = "Not possible to perform this test type with odd number of sounds.";
	private final String testTypeNotChosenMsg = "Please set a valid test type";
	private String userNameTooShortMsg;
	private EditText userNameEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ABXTesterApplication)getApplicationContext();
		app.initPrefs();
		setContentView(R.layout.main_layout);
		findViewById(R.id.start_btn).setOnClickListener(this);
		userNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
		userNameTooShortMsg = "User name too short. Minimum "+app.getMinimumUserNameLength() +" characters.";
		app.addIntToPrefs(app.getTestRoundNumberString(), 0);
		test = new ArrayList<TestRound>();
		setupTest();
	}

	private void setupTest(){
		Field[] fields=R.raw.class.getFields();

		switch(app.getTestType()){

			case allSoundCombinations:
				try {
					int roundId = 0;
					for (int i = 0; i < fields.length - 1; i++) {
						int resIdA = fields[i].getInt(fields[i]);
						Sound aSound = new Sound(getResources().getResourceEntryName(resIdA), resIdA);
						for(int j = i+1; j < fields.length; j++){
							int resIdB = fields[j].getInt(fields[j]);
							addTestRoundToTest(aSound,new Sound(getResources().getResourceEntryName(resIdB),resIdB),roundId);
							roundId++;
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;

			case firstSoundAgainstAllOther:
				try {
					int resIdA = -1;
					Sound aSound = new Sound();
					for (int i = 0; i < fields.length; i++) {
						if(i == 0){
							resIdA = fields[i].getInt(fields[i]);
							aSound = new Sound(getResources().getResourceEntryName(resIdA), resIdA);
						} else{
							int resIdB = fields[i].getInt(fields[i]);
							addTestRoundToTest(aSound,new Sound(getResources().getResourceEntryName(resIdB),resIdB),i-1);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;

			case orderedSingleOccurrence:
				if(fields.length % 2 != 0) {
					Toast.makeText(getApplicationContext(), unevenSoundNumberMsg,Toast.LENGTH_LONG).show();
					break;
				}
				addSingleOccurrenceSoundsToTest(fields);
				break;

			case randomizedSingleOccurrence:
				fisherYates(fields);
				addSingleOccurrenceSoundsToTest(fields);
				break;

			default:
				Toast.makeText(getApplicationContext(), testTypeNotChosenMsg,Toast.LENGTH_LONG).show();
				break;
		}

		app.addTestToPrefs(test);
	}

	private void addSingleOccurrenceSoundsToTest(Field[] fields){
		try{
			int roundId = 0;
			for (int i = 0; i < fields.length-1; i+=2) {
				int resIdA = fields[i].getInt(fields[i]);
				int resIdB = fields[i+1].getInt(fields[i+1]);
				addTestRoundToTest(new Sound(getResources().getResourceEntryName(resIdA), resIdA),
						new Sound(getResources().getResourceEntryName(resIdB),resIdB),roundId);
				roundId++;
			}
		} catch (IllegalAccessException e){
			e.printStackTrace();
		}
	}

	private void addTestRoundToTest(Sound aSound, Sound bSound, int roundId){
		TestRound testRound = new TestRound(aSound,bSound,app.getXsoundsPerTestRound(),roundId);
		test.add(testRound);
	}

	private void fisherYates(Field[] array) {
		Random r = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int index = r.nextInt(i);
			Field tmpField = array[index];
			array[index] = array[i];
			array[i] = tmpField;
		}
	}

	@Override
	public void onClick(View v) {
		if(userNameEditText.getText().length() < app.getMinimumUserNameLength()) {
			Toast.makeText(getApplicationContext(),userNameTooShortMsg,Toast.LENGTH_LONG).show();
		}else {
			String shuffledUserName = shuffleUserName(userNameEditText.getText().toString());
			app.addStringToPrefs(app.getUserNameString(), shuffledUserName);
			Intent intent = new Intent(getApplicationContext(), LoadingScreenActivity.class);
			intent.putExtra(app.getIntentToStart(), app.getTestRoundActivityString());
			intent.putExtra(app.getLoadScreenText(), app.getRoundString() + "1");
			startActivity(intent);
		}
	}

	private String shuffleUserName(String stringToShuffle){
		List<Character> user = new ArrayList<Character>();
		for(char letter : stringToShuffle.toCharArray()){
			user.add(letter);
		}
		Collections.shuffle(user);
		char[] tmpArray = new char[user.size()];
		for(int i = 0; i < user.size(); i++){
			tmpArray[i] = user.get(i);
		}
		return (new String(tmpArray));
	}
}
