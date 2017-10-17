package edu.orangecoastcollege.cs273.phuynh101.cs273superheroes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 10-Oct-17.
 */

/**
 * JSON Loader
 */
public class JSONLoader {

    /**
     * load JSon from asset and return a list of superheroes
     * @param context the context to get asset manager
     * @return the list of superheroes
     * @throws IOException
     */
    public static List<Superhero> loadJSONFromAsset(Context context) throws IOException
    {
        List<Superhero> allSuperHeroesList = new ArrayList<>();
        String json = null;
        InputStream is = context.getAssets().open("cs273superheroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        try {
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray allSuperheroesJSON = jsonRootObject.getJSONArray("CS273Superheroes");

            int length = allSuperheroesJSON.length();
            for(int i = 0; i < length; i++)
            {
                JSONObject superheroJSON = allSuperheroesJSON.getJSONObject(i);
                allSuperHeroesList.add(new Superhero(superheroJSON.getString("Username")
                        , superheroJSON.getString("Name"), superheroJSON.getString("Superpower")
                        , superheroJSON.getString("OneThing")));
            }
        } catch (JSONException e) {
            Log.e("CSA273 Superheroes", e.getMessage());
        }
        return allSuperHeroesList;
    }
}
