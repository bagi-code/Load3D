package com.bagicode.load3d;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity{

    private ImageView imageView360;
    private Renderer renderer;
    private int angle = 10;
    private int mStartX, mStartY, mEndX, mEndY, mImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView360 = (ImageView) findViewById(R.id.iv_imageView);


        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        // Add mSurface to your root view
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        renderer = new Renderer(this);
        surface.setSurfaceRenderer(renderer);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):

                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                return true;

            case (MotionEvent.ACTION_MOVE):

                mEndX = (int) event.getX();
                mEndY = (int) event.getY();

                if ((mEndX - mStartX) > 3) {
                    angle = angle + 10;
                    if (mImageIndex > 100)
                        mImageIndex = 0;

                    imageView360.setImageLevel(mImageIndex);
                    renderer.getObject().rotate(Vector3.Axis.Y, -1.0);

                }
                if ((mEndX - mStartX) < -3) {
                    angle = angle - 10;
                    if (mImageIndex < 0)
                        mImageIndex = 10;

                    renderer.getObject().rotate(Vector3.Axis.Y, 1.0);

                }
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();

                return true;

            case (MotionEvent.ACTION_UP):
                mEndX = (int) event.getX();
                mEndY = (int) event.getY();

                return true;

            case (MotionEvent.ACTION_CANCEL):
                return true;

            case (MotionEvent.ACTION_OUTSIDE):
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }
}
