package com.dsandberg.abxtester2016;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ABXTesterApplication extends Application{
    private final int xSoundsPerTestRound = 5;
    private final int minimumUserNameLength = 3;
    private final String testRoundString = "testRound";
    private final String testString = "test";
    private final TestTypeEnum testType = TestTypeEnum.orderedSingleOccurrence;
    private SharedPreferences prefs;
    private final String intentToStart = "intentToStart";
    private final String loadScreenText = "loadScreenText";
    private final String testRoundActivityString = ".TestRoundActivity";
    private final String resultActivityString = ".ResultActivity";
    private final String roundString = "ROUND ";
    private final String userNameString = "userName";
    private final String emailAddressToSendAnswersTo = "INSERT EMAIL ADDRESS";

    public String getEmailAddressToSendAnswersTo() {
        return emailAddressToSendAnswersTo;
    }

    public String getUserNameString() {
        return userNameString;
    }

    public int getMinimumUserNameLength() {
        return minimumUserNameLength;
    }

    public String getLoadScreenText() {
        return loadScreenText;
    }

    public String getResultActivityString() {
        return resultActivityString;
    }

    public String getRoundString() {
        return roundString;
    }

    public String getTestRoundActivityString() {
        return testRoundActivityString;

    }

    public String getIntentToStart() {
        return intentToStart;
    }

    public TestTypeEnum getTestType() {

        return testType;
    }

    public String getTestRoundNumberString() {
        return testRoundString;
    }

    public int getXsoundsPerTestRound() {
        return xSoundsPerTestRound;
    }


    public void addTestToPrefs(ArrayList<TestRound> test){
        Gson gson = new Gson();
        String jsonString = gson.toJson(test);
        prefs.edit().putString(testString,jsonString).apply();
    }

    public void setTestRoundToTestInPrefs(TestRound testRound){
        ArrayList<TestRound> test = getTest();
        test.set(testRound.getRoundId(), testRound);
        addTestToPrefs(test);
    }

    public TestRound getTestRoundAtIndex(int index){
        String jsonString = prefs.getString(testString, "No such element");
        Type type = new TypeToken<List<TestRound>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<TestRound> test = gson.fromJson(jsonString, type);
        return test.get(index);
    }

    public ArrayList<TestRound> getTest(){
        String jsonString = prefs.getString(testString, "No such element");
        Type type = new TypeToken<List<TestRound>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, type);
    }

    public void addIntToPrefs(String key, int value){
        prefs.edit().putInt(key, value).apply();
    }

    public String getStringFromPrefs(String key){
        return prefs.getString(key, "No such element");
    }

    public void addStringToPrefs(String key, String value){
        prefs.edit().putString(key, value).apply();
    }

    public int getIntFromPrefs(String key){
        return prefs.getInt(key,-1);
    }

    public void initPrefs(){
        if(prefs == null)
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }
}