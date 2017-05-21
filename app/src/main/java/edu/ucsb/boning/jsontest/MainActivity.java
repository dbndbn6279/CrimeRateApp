package edu.ucsb.boning.jsontest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView mSearchHint;
    private AutoCompleteTextView mEditView;
    private ImageButton mClearButton;

    private ImageView mDashBoardContainer;
    private DashBoard mDashBoard;

    private JsonHandler mJsHandler;

    private TextView mRateValueDisp;
    private TextView mRankValueDisp;
    private LinearLayout mRateValueLayout;
    private LinearLayout mRankValueLayout;

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize:
        mSearchHint = (TextView) findViewById(R.id.searchHint);
        mEditView = (AutoCompleteTextView) findViewById(R.id.autoTextView);
        mClearButton = (ImageButton) findViewById(R.id.clearButton);
        mJsHandler = new JsonHandler(this);
        mCitiesInfo = mJsHandler.readAllCities();
        mRankValueDisp = (TextView) findViewById(R.id.cityRankValue);
        mRateValueDisp = (TextView) findViewById(R.id.crimeRateValue);
        //Font Style:
        /*
        mRankValueDisp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/VanillaExtractRegular.ttf"));
        mRateValueDisp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/VanillaExtractRegular.ttf"));
        */
        //Info Display:
        mRankValueLayout = (LinearLayout) findViewById(R.id.infoDispFrame2);
        mRateValueLayout = (LinearLayout) findViewById(R.id.infoDispFrame1);
        mRankValueLayout.setVisibility(LinearLayout.GONE);
        mRateValueLayout.setVisibility(LinearLayout.GONE);
        //DashBoard Image:
        mDashBoardContainer = (ImageView) findViewById(R.id.dashboard);
        initAutoCompleteView();

        //SearchHint Init
        mSearchHint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/VanillaExtractRegular.ttf"));
        mSearchHint.setShadowLayer(5, 3, 3, R.color.shadow);

        //ClearButton Init
        mClearButton.setVisibility(ImageButton.GONE);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditView.setText("");
            }
        });

        //Search Bar Init
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
                if(cityName.length() != 0)
                    mClearButton.setVisibility(ImageButton.VISIBLE);
                else
                    mClearButton.setVisibility(ImageButton.GONE);
                displayCityInfo(cityName);
            }
        });
        //Search Bar Click
        mEditView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideSoftKeyboard();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        initInfoDispFont();
    }
    private void initInfoDispFont(){
        for(int i = 0; i < mRateValueLayout.getChildCount(); i++){
            View child = mRateValueLayout.getChildAt(i);
            if(child instanceof TextView){
                ((TextView) child).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/VanillaExtractRegular.ttf"));
            }
        }

        for(int i = 0; i < mRankValueLayout.getChildCount(); i++){
            View child = mRankValueLayout.getChildAt(i);
            if(child instanceof TextView){
                ((TextView) child).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/VanillaExtractRegular.ttf"));
            }
        }
    }

    private void initAutoCompleteView(){
        ArrayList<String> cityNames = new ArrayList<>(mCitiesInfo.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNames);
        Log.d(TAG, "Adapter Items: " + adapter.getCount());
        mEditView.setAdapter(adapter);
        mEditView.setThreshold(2);
    }

    private void drawDashBoard(int index){
        mDashBoard = new DashBoard(this, mDashBoardContainer);
        mDashBoard.drawDashBoard(index);

    }

    private void displayCityInfo(String cityName){
        if(mCitiesInfo.containsKey(cityName)) {
            CityInfo city = mCitiesInfo.get(cityName);
            //Final Design
            mRankValueLayout.setVisibility(LinearLayout.VISIBLE);
            mRateValueLayout.setVisibility(LinearLayout.VISIBLE);
            mRankValueDisp.setText("" + city.getRanking());
            mRateValueDisp.setText("" + city.getIndex());
            int color = Color.rgb(13, 70, 80);
            if(city.getRanking() < 33.33)
                color = Color.rgb(185, 106, 106);
            else if(city.getRanking() < 66.66)
                color = Color.rgb(175, 153, 101);
            else
                color = Color.rgb(101, 182, 120);

            mRankValueDisp.setTextColor(color);
            mRateValueDisp.setTextColor(color);
            drawDashBoard(((int) city.getRanking()));
        }else{
            mRankValueLayout.setVisibility(LinearLayout.GONE);
            mRateValueLayout.setVisibility(LinearLayout.GONE);
            if(mDashBoard == null)
                mDashBoard = new DashBoard(this, mDashBoardContainer);
            mDashBoard.clearDashBoard();
        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

}
