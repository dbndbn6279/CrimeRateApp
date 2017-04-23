package edu.ucsb.boning.jsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayCityInfo extends AppCompatActivity {
    private String TAG = "DISP_ACT_DEBUG";
    private TextView mCityInfoView;

    private String mCityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_city_info);
        /*
        //Init:
        mCityInfoView = (TextView) findViewById(R.id.city_info_view);
        mCityName = getIntent().getStringExtra("cityName");
        Log.d(TAG, "The city: " + mCityName);
        CityInfo city = MainActivity.mCitiesInfo.get(mCityName);
        mCityInfoView.setText("City Name: " + city.getName());
        mCityInfoView.append("\nNumber: " + city.getNumber());
        mCityInfoView.append("\nRate: " + city.getRate());
        mCityInfoView.append("\nIndex: " + city.getIndex());
        mCityInfoView.append("\nCity Ranking: " + city.getCityRank());
        mCityInfoView.append("\nResident Ranking: " + city.getResRank());
        */
    }
}
