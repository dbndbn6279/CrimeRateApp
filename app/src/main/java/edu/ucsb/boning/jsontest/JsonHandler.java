package edu.ucsb.boning.jsontest;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


/**
 * Created by boning on 4/22/17.
 */

public class JsonHandler {
    final private String TAG = "DEBUG_JS_HANDLER";
    final private String READ_CITY_TAG = "DEBUG_READ_CITY";
    final private String LOAD_CITIES_TAG = "DEBUG_LOAD_CITIES";
    //Reading String from Json related:
    private Context context;
    private String path;
    private String jsonInfo;

    //Reader Method related:
    JsonReader reader;

    public JsonHandler(Context context){
        path = new String("crimeNew.json");
        this.context = context;

    }

    //Use a JsonReader to parse the info:
    public HashMap<String, CityInfo> readAllCities(){
        HashMap <String, CityInfo> cityLists = new HashMap<>();
        try {
            InputStream inputStream = context.getAssets().open(path);
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            //Try to read all cities:
            reader.beginObject();
            while (reader.hasNext()){
                String cityName = reader.nextName();
                if(cityName.length() != 0) {
                    cityLists.put(cityName, readCityInfo(cityName, reader));
                }
                else{
                    reader.skipValue();
                }
            }
            reader.endObject();
            //Done trying
            return  cityLists;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public CityInfo readCityInfo(String name, JsonReader reader) throws IOException {
        //Temporary var
        CityInfo city = new CityInfo();
        //Init
        city.setName(name);
        //Read Process:
        reader.beginObject();
        while (reader.hasNext()){
            String termName = reader.nextName();
            if(termName.equals("Num")){
                city.setNumber(reader.nextInt());
            }else if(termName.equals("Rate")){
                city.setRate(reader.nextInt());
            }else if(termName.equals("Index")){
                city.setIndex(reader.nextInt());
            }else if(termName.equals("Ranking")){
                city.setRanking(reader.nextDouble());
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return city;
    }
}
