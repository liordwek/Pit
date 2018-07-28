package com.example.liord.pit.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.liord.pit.classes.PitPointClass;

import java.util.ArrayList;

/**
 * Created by liord on 7/26/2018.
 */

public class PitCv extends ViewGroup {

    private Paint paint;
    private ArrayList<PitPointClass> PointCollection;
    private int initialSpace = 200; // the space between points on the first layout

    public PitCv(Context context, AttributeSet attrs) {
        // only this constructor is in use, initialise the context and the paint

        this(context, attrs, 0);
        this.paint = new Paint();
        this.paint.setColor(Color.CYAN);
    }

    public PitCv(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PitCv(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    public void setPoints(ArrayList<PitPointClass> points){
        // sets the point collection from PitMainActivity

        this.PointCollection = points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if (this.PointCollection != null) {

            for (int i = 0; i < this.PointCollection.size(); i++) { // go over the point collection and draw them
                if (this.PointCollection.get(i).isSelected()){ // if a point is selected, it will be red, otherwise cyan
                    this.paint.setColor(Color.RED);
                }else{
                    this.paint.setColor(Color.CYAN);
                }

                canvas.drawCircle(this.PointCollection.get(i).getX(), this.PointCollection.get(i).getY(), this.PointCollection.get(i).getRadius(), this.paint);
            }
        }
        this.drawEdges(canvas); // draw the lines between the points
        this.drawBackground(canvas); // draw background grid
    }


    private void drawBackground (Canvas canvas){
        // set background grid

        this.paint.setColor(Color.BLACK);
        this.paint.setStrokeWidth(1);
        canvas.drawLine(canvas.getWidth()/2,0,canvas.getWidth()/2,canvas.getHeight(),paint);//vertical line
        canvas.drawLine(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2,paint);//horizontal line
        this.paint.setColor(Color.CYAN);// return to default color
    }

    private void drawEdges(Canvas canvas){
        // draw the lines between the points

        this.paint.setColor(Color.RED);
        this.paint.setStrokeWidth(2);
        if(this.PointCollection != null){
            for (int i = 0; i < this.PointCollection.size(); i++) {
                if (i < this.PointCollection.size() - 1) {
                    canvas.drawLine(this.PointCollection.get(i).getX(), this.PointCollection.get(i).getY(), this.PointCollection.get(i + 1).getX(), this.PointCollection.get(i+1).getY(), this.paint);
                }
            }
        }
        this.paint.setColor(Color.CYAN); // return to default color
    }

    public int getInitialSpace(){
        return this.initialSpace;
    }

}
