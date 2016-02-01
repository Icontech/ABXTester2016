package com.dsandberg.abxtester2016;

public class XSound extends Sound {
    private boolean isAsound;
    private boolean answerIsCorrect;
    private String answer;

    public XSound(String name, int id, boolean isAsound){
        setName(name);
        setId(id);
        this.isAsound = isAsound;
    }

    public void setAnswerIsAsound(boolean answerWasA) {
        if(answerWasA && isAsound || !answerWasA && !isAsound)
            answerIsCorrect = true;
        else
            answerIsCorrect = false;
        if(answerWasA)
            answer = "A";
        else
            answer = "B";
    }

    public boolean isAsound() {
        return isAsound;
    }

    public boolean getAnswerIsCorrect() {
        return answerIsCorrect;
    }

    public String getAnswer() {
        return answer;
    }

}
