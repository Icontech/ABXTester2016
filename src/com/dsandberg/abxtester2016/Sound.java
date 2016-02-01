package com.dsandberg.abxtester2016;

public class Sound {
    private String name;
    private int id;

    public Sound(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Sound (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
