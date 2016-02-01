package com.dsandberg.abxtester2016;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import java.util.ArrayList;

public class XbuttonLayout extends RelativeLayout {
    private ArrayList<View> xbuttonList = new ArrayList<View>();
    private ArrayList<ToggleButton> toggleBtnList = new ArrayList<ToggleButton>();
    private final String answerBtnText = "TAP TO ANSWER";

    public XbuttonLayout(Context context) {
        super(context);
    }

    public XbuttonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XbuttonLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ArrayList<View> getXbuttonList() {
        return xbuttonList;
    }

    public ArrayList<ToggleButton> getToggleBtnList() {
        return toggleBtnList;
    }

    public View addXbtnAndAbToggleBtn(int xBtnId, int abToggleBtnId, int index) {
        View xbutton = createXbutton(xBtnId);
        if(index > 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) xbutton.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, xbuttonList.get(index-1).getId());
        }

        ToggleButton abToggleBtn = createToggleBtn(abToggleBtnId);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) abToggleBtn.getLayoutParams();
        params2.addRule(RelativeLayout.RIGHT_OF, xbutton.getId());
        if(index > 0)
            params2.addRule(RelativeLayout.BELOW, toggleBtnList.get(index-1).getId());
        return xbutton;
    }

    private View createXbutton(int id) {
        View view;
        Button xButton = new Button(getContext());
        xButton.setText("X" + id);
        xButton.setId(id);
        view = xButton;
        addView(view);
        xbuttonList.add(view);
        return view;
    }

    private ToggleButton createToggleBtn(int id){
        ToggleButton abToggleBtn = new ToggleButton(getContext());
        abToggleBtn.setText(answerBtnText);
        abToggleBtn.setTextOn("A");
        abToggleBtn.setTextOff("B");
        abToggleBtn.setId(id);
        addView(abToggleBtn);
        toggleBtnList.add(abToggleBtn);
        return abToggleBtn;
    }

    public String getAnswerBtnText() {
        return answerBtnText;
    }
}
