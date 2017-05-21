package edu.ucsb.boning.jsontest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by boning on 5/13/17.
 */

public class DashBoard {
    private Context context;
    private ImageView mImgView;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private final float mScale = ((float) 0.85);
    private final int[] RED_COLOR = {255, 180, 180};
    private final int[] GREEN_COLOR = {175,255, 184};
    private final int R = 0, G = 1, B = 2;

    private int mCurrentIndex;
    private int mMaxIndex;
    private final int DASH_BOARD_SCALE = 40;
    private final int SCLAE_DGREE = 240 / DASH_BOARD_SCALE;


    private Handler mTimerHandler;
    private Runnable mTimerRunnable;

    public DashBoard(Context context, ImageView imgView){
        this.context = context;
        mImgView = imgView;
        Log.d("DashBoard", "Width: " + mImgView.getWidth());
        Log.d("DashBoard", "Height: " + mImgView.getHeight());


        mBitmap = Bitmap.createBitmap((int) mImgView.getWidth(), (int) mImgView.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();


        mCurrentIndex = 0;
        mMaxIndex = 0;

        mTimerHandler = new Handler();

    }

    public void clearDashBoard(){
        mBitmap = Bitmap.createBitmap((int) mImgView.getWidth(), (int) mImgView.getHeight(), Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
    }

    public void drawDashBoard(int index){
        //DashBoard Init:
        final RectF rect = new RectF(mBitmap.getWidth() * (1 - mScale),
                mBitmap.getHeight() * (1 - mScale), mBitmap.getWidth() * mScale, mBitmap.getHeight() * mScale);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(RED_COLOR[R], RED_COLOR[G], RED_COLOR[B]));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBitmap.getWidth() * (1 - mScale));
        mImgView.setImageBitmap(mBitmap);
        //Begin to Draw
        //Data prepare:
        mCurrentIndex = 0;
        mMaxIndex = index * DASH_BOARD_SCALE / 200;

        mTimerRunnable = new Runnable() {
            @Override
            public void run() {
                int dR = (GREEN_COLOR[R] - RED_COLOR[R]) * 2 / DASH_BOARD_SCALE;
                int dG = (GREEN_COLOR[G] - RED_COLOR[G]) * 2 / DASH_BOARD_SCALE;
                int dB = (GREEN_COLOR[B] - RED_COLOR[B]) * 2 / DASH_BOARD_SCALE;
                mPaint.setColor(Color.rgb(RED_COLOR[R] + dR * mCurrentIndex, RED_COLOR[G] + dG * mCurrentIndex, RED_COLOR[B] + dB * mCurrentIndex));
                mCanvas.drawArc(rect, 150 + 2 * mCurrentIndex * SCLAE_DGREE, SCLAE_DGREE, false, mPaint );
                mImgView.setImageBitmap(mBitmap);
                mCurrentIndex ++;
                if (mCurrentIndex > mMaxIndex){
                    displaySafetyConditon();
                    mCurrentIndex = 0;
                    return;
                }
                else{
                    mTimerHandler.postDelayed(this, 50 + mCurrentIndex * 7);
                }
            }
        };

        mTimerHandler.postDelayed(mTimerRunnable, 300);
    }

    private void displaySafetyConditon(){
        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(6);
        textPaint.setTextSize(50);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/VanillaExtractRegular.ttf"));
        textPaint.setShadowLayer(1, 1, 1, Color.GRAY);
        if(mMaxIndex < (DASH_BOARD_SCALE / 2) * 0.33){
            //Display Dangers
            textPaint.setColor(Color.rgb(227, 137, 136));
            textPaint.setTextSize(36);
            mCanvas.drawText("Unsafe", mBitmap.getWidth()/2, ((int) (mBitmap.getHeight() * 0.54)), textPaint);
        }else if(mMaxIndex < (DASH_BOARD_SCALE / 2) * 0.67){
            //Display Good
            textPaint.setColor(Color.rgb(233, 207, 153));
            mCanvas.drawText("OK", mBitmap.getWidth()/2, ((int) (mBitmap.getHeight() * 0.53)), textPaint);
        }else{
            //Display Safe
            textPaint.setColor(Color.rgb(136, 194, 153));
            mCanvas.drawText("Safe", mBitmap.getWidth()/2, ((int) (mBitmap.getHeight() * 0.53)), textPaint);
        }
    }
}


