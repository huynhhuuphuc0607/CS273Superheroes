package edu.orangecoastcollege.cs273.phuynh101.cs273superheroes;

/**
 * Created by HuynhHuu on 10-Oct-17.
 */

/**
 * Superhero class
 */
public class Superhero {
    private String mUsername;
    private String mName;
    private String mSuperpower;
    private String mOneThing;

    /**
     * constructor
     * @param username username
     * @param name name
     * @param superpower superpower
     * @param oneThing one unique thing
     */
    public Superhero(String username, String name, String superpower, String oneThing) {
        mUsername = username;
        mName = name;
        mSuperpower = superpower;
        mOneThing = oneThing;
    }

    /**
     * get the username of the superhero
     * @return username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * set the new username
     * @param username new username
     */
    public void setUsername(String username) {
        mUsername = username;
    }

    /**
     * get the name of the superhero
     * @return name of the superhero
     */
    public String getName() {
        return mName;
    }

    /**
     * set the new name of the superhero
     * @param name new name
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * get the superhero's superpower
     * @return superpower
     */
    public String getSuperpower() {
        return mSuperpower;
    }

    /**
     * set the new superpower
     * @param superpower new superpower
     */
    public void setSuperpower(String superpower) {
        mSuperpower = superpower;
    }

    /**
     * get one unique thing of the superhero
     * @return one unique thing
     */
    public String getOneThing() {
        return mOneThing;
    }

    /**
     * set the new one unique thing of the superhero
     * @param oneThing the new one unique thing
     */
    public void setOneThing(String oneThing) {
        mOneThing = oneThing;
    }
}
