package edu.ucsb.boning.jsontest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //For debuging:
    final private String TAG = "MainActivity: ";
    static public HashMap<String, CityInfo> mCitiesInfo;
    //Member Vars:
    private AutoCompleteTextView mEditView;
    private JsonHandler mJsHandler;

    private Button mSearchButton;
    private Button mClearButton;

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize:
        mEditView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
        mJsHandler = new JsonHandler(this);
        mCitiesInfo = mJsHandler.readAllCities();
        mSearchButton = (Button) findViewById(R.id.search_btn);
        mClearButton = (Button) findViewById(R.id.clear_btn);
        initAutoCompleteView();

        final Intent intent = new Intent(this, DisplayCityInfo.class);

        //SetOnClicker
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = mEditView.getText().toString();
                Log.d(TAG, "cityName: " + cityName);
                Log.d(TAG, "containKey: " + mCitiesInfo.containsKey(cityName));
                if(mCitiesInfo.containsKey(cityName)){
                    intent.putExtra("cityName", cityName);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, R.string.no_city_info, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditView.setText("");
            }
        });
    }

    private void initAutoCompleteView(){
        ArrayList<String> cityNames = new ArrayList<>(mCitiesInfo.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNames);
        Log.d(TAG, "Adapter Items: " + adapter.getCount());
        mEditView.setAdapter(adapter);
        mEditView.setThreshold(1);
    }

}
