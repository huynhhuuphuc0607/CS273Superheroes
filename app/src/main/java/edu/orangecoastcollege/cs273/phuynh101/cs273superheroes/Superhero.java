package edu.orangecoastcollege.cs273.phuynh101.cs273superheroes;

/**
 * Created by HuynhHuu on 10-Oct-17.
 */

public class Superhero {
    private String mUsername;
    private String mName;
    private String mSuperpower;
    private String mOneThing;

    public Superhero(String username, String name, String superpower, String oneThing) {
        mUsername = username;
        mName = name;
        mSuperpower = superpower;
        mOneThing = oneThing;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSuperpower() {
        return mSuperpower;
    }

    public void setSuperpower(String superpower) {
        mSuperpower = superpower;
    }

    public String getOneThing() {
        return mOneThing;
    }

    public void setOneThing(String oneThing) {
        mOneThing = oneThing;
    }
}
