package edu.ucsb.boning.jsontest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private HashMap<String, CityInfo> mCitiesInfo;
    //Member Vars:
    private AutoCompleteTextView mEditView;
    private TextView mCityInfoView;
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
        mCityInfoView = (TextView) findViewById(R.id.info_text_view);
        mJsHandler = new JsonHandler(this);
        mCitiesInfo = mJsHandler.readAllCities();
        initAutoCompleteView();

        mCityInfoView.setText("");
        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cityName = mEditView.getText().toString();
                if(mCitiesInfo.containsKey(cityName)) {
                    CityInfo city = mCitiesInfo.get(cityName);
                    mCityInfoView.setText("City Name: " + city.getName());
                    mCityInfoView.append("\nNumber: " + city.getNumber());
                    mCityInfoView.append("\nRate: " + city.getRate());
                    mCityInfoView.append("\nIndex: " + city.getIndex());
                    mCityInfoView.append("\nCity Ranking: " + city.getCityRank());
                    mCityInfoView.append("\nResident Ranking: " + city.getResRank());
                }else{
                    mCityInfoView.setText("");
                }
            }
        });

                                                                                                                                                                                                ImageButton crossButton = (ImageButton) findViewById(R.id.cross_button);
                                                                                                                                                                                                ImageButton crossButton = (ImageButton) findViewById(R.id.cross_button);
        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditView.setText("");
            }
        });
        /*
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
        */
    }

    private void initAutoCompleteView(){
        ArrayList<String> cityNames = new ArrayList<>(mCitiesInfo.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNames);
        Log.d(TAG, "Adapter Items: " + adapter.getCount());
        mEditView.setAdapter(adapter);
        mEditView.setThreshold(1);
    }

}

