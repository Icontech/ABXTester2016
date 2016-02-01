package com.dsandberg.abxtester2016;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity implements OnClickListener {
	private String id;
	private StringBuilder sb;
	private String congratsMsg;
	private ABXTesterApplication app;
	private ArrayList<TestRound> test;
	private final String noEmailClientsInstalledMsg = "There are no email clients installed.";
	private String emailSubject;
	private final String intentType = "message/rfc822";
	private final String sendMailString = "Send results via email";
	private final String correct = "correct";
	private final String wrong = "wrong";
	private final String aString = "A";
	private final String bString = "B";
	private TextView resultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		resultText = (TextView) findViewById(R.id.result_text);
		findViewById(R.id.email_btn).setOnClickListener(this);
		app = (ABXTesterApplication) getApplicationContext();
		test = app.getTest();
		id = app.getStringFromPrefs(app.getUserNameString())+(randInt(1000000));
		emailSubject = "ABX TEST. Id: "+id;
		sb = new StringBuilder();
		setupResults();
	}


	private void setupResults(){
		int correctAnswers = 0;
		sb.append("id: "+id+"\n");
		for (TestRound round : test){
			sb.append(app.getRoundString()+(round.getRoundId()+1)+"\n");
			sb.append("A: "+round.getA().getName()+"\n");
			sb.append("B: "+round.getB().getName()+"\n");
			for(int i = 0; i < round.getxSounds().size(); i++){
				XSound x = round.getxSounds().get(i);
				sb.append("X"+(i+1)+": "+(x.isAsound() ? aString : bString)+" # Answer: "+(x.getAnswer())+" # "+(x.getAnswerIsCorrect() ? correct : wrong)+"\n");
				if(x.getAnswerIsCorrect()){
					correctAnswers++;
				}
			}
		}
		float percentage = (correctAnswers*100)/(test.size()*app.getXsoundsPerTestRound());
		congratsMsg = "You got "+correctAnswers+"/"+test.size()*app.getXsoundsPerTestRound()+" correct answers! \n"+percentage+"%\nThank you for participating!";
		resultText.setText(congratsMsg);
		sb.append(congratsMsg);
	}

	public void sendEmail(StringBuilder sb){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType(intentType);
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{app.getEmailAddressToSendAnswersTo()});
		i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
		i.putExtra(Intent.EXTRA_TEXT, sb.toString());

		try {
		    startActivity(Intent.createChooser(i, sendMailString));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(getApplicationContext(), noEmailClientsInstalledMsg, Toast.LENGTH_SHORT).show();
		}
	}

	private int randInt(int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt(max+1);
	    return randomNum;
	}

	@Override
	public void onClick(View v) {
		sendEmail(sb);
	}
}