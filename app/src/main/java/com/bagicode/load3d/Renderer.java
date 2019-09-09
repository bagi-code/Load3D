package com.bagicode.load3d;
import android.content.Context;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.RajawaliRenderer;

import javax.microedition.khronos.opengles.GL10;

public class Renderer extends RajawaliRenderer {

    private DirectionalLight directionalLight;
    private Object3D earthSphere;
    LoaderOBJ objParser ;

    public Renderer(Context context) {
        super(context);
        setFrameRate(60);
    }

    public void onTouchEvent(MotionEvent event){
    }

    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }

    @Override
    protected void initScene() {
        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        //your object file name instead of R.raw.***;

        objParser = new LoaderOBJ(mContext.getResources(),mTextureManager, R.raw.monitorcurved);

        try {

            objParser.parse();
            earthSphere = objParser.getParsedObject();
            earthSphere.setX(0);
            earthSphere.setY(-1);
            earthSphere.setZ(0);
            earthSphere.setScale(0.1f);    //change as per you object height to fit in screen
            earthSphere.setDoubleSided(true);
            getCurrentScene().addChild(earthSphere);


        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        earthSphere.rotate(Vector3.Axis.Y, 1.0);
    }

    @Override
    public void onRenderFrame(GL10 gl) {
        super.onRenderFrame(gl);

    }

    public Object3D getObject(){
        return earthSphere;
    }
}