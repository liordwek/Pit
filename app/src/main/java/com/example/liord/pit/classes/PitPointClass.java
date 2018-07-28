package com.example.liord.pit.classes;

import android.graphics.Point;

/**
 * Created by liord on 7/28/2018.
 */

public class PitPointClass  {

    private float x = 0;
    private float y = 0;
    private float radius = 60;
    private boolean isSelected = false;

    public PitPointClass(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public float getCenterX(){
        return getX() + getRadius();
    }

    public float getCenterY(){
        return getY() + getRadius();
    }

    public void setPosition(float x, float y){
        this.setX(x);
        this.setY(y);
    }

}
