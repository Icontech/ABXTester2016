package com.dsandberg.abxtester2016;

import java.util.ArrayList;
import java.util.Random;

public class TestRound {
    private Sound a;
    private Sound b;
    private ArrayList<XSound> xSounds;
    private int roundId;

    public TestRound(Sound a, Sound b, int numberOfXSounds, int roundId){
        this.a = a;
        this.b = b;
        this.roundId = roundId;
        xSounds = new ArrayList<XSound>();
        for(int i= 0; i < numberOfXSounds; i++){
            Sound s = getRandomSound();
            XSound xSound = new XSound(s.getName(),s.getId(), s.equals(this.a) ? true : false);
            xSounds.add(xSound);
        }
    }

    public Sound getA() {
        return a;
    }

    public int getRoundId() {
        return roundId;
    }

    public Sound getB() {
        return b;
    }

    public ArrayList<XSound> getxSounds() {
        return xSounds;
    }

    private Sound getRandomSound(){
        Random rand = new Random();
        if(rand.nextInt(2) == 0) {
            return a;
        }
        return b;
    }
}