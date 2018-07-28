package com.example.liord.pit;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import com.example.liord.pit.classes.PitPointClass;
import com.example.liord.pit.customViews.PitCv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PitMainActivity extends AppCompatActivity implements View.OnTouchListener {

    private PitCv cv; // an instance of PitCv
    private int pointsInitialNumber = 5; // initial point number on the grid
    private ArrayList<PitPointClass> pointCollection = new ArrayList<>(); // an array that will hold all the points on the grid
    private boolean isSelected = false; // if true, one of the points on the grid is already selected
    private boolean isFirstDraw = true;// a flag to prevent double initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pit_main);
        this.init();
    }

    private void init(){
        // initiate activity's ui components

        this.cv = findViewById(R.id.pitCustomView);
        this.cv.setOnTouchListener(this);
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // + actionButton
                addPoint();
            }
        });
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        // this method is triggered when the PitCv view is loaded

        if(this.isFirstDraw) { // runs only one time when the PitCv view is loaded
            this.setInitialPointCollection(this.pointsInitialNumber);
        }
    }

    private void setInitialPointCollection(int PointNumber){
        // sets the initial points on the view
        // it starts with a point in the center, then sets one point to the right of it, one to the left of it etc.
        // y is steady, x is changing

        int pointX = this.cv.getWidth()/2;
        boolean switchSide = true;
        int space = this.cv.getInitialSpace(); // the space between the initial points

        for (int i = 0; i<PointNumber; i++){
            PitPointClass point = new PitPointClass(pointX,this.cv.getHeight()/2);
            pointX = this.cv.getWidth()/2;// reset x
            this.pointCollection.add(point);
            if(switchSide) {
                pointX += space;
            }
            else{
                pointX -= space;
                space += cv.getInitialSpace();
            }
            switchSide = !switchSide;
        }
        this.cv.setPoints(this.pointCollection); // deliver the point collection to PitCv
        this.cv.invalidate(); // refresh PitCv view
        this.isFirstDraw = false; // means that this action should not be called again
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // handle screen touching events

        final int action = event.getActionMasked(); // get action type
        float positionTouchedX =   event.getX(); // get touch location x
        float positionTouchedY =   event.getY(); // get touch location y

        switch (action) {
            case MotionEvent.ACTION_DOWN: // When touching the screen

                break;
            case MotionEvent.ACTION_UP: // When lifting finger from the screen
                // deselect all points

                this.isSelected = false;
                for(int i = 0; i<this.pointCollection.size(); i++){
                    this.pointCollection.get(i).setSelected(false);
                }
                break;
            case MotionEvent.ACTION_MOVE: // When moving finger on the screen

                for(int i = 0; i<this.pointCollection.size(); i++){

                // check if any of the points got touched
                    if(isTouchInside(this.pointCollection.get(i).getCenterX(),this.pointCollection.get(i).getCenterY(),positionTouchedX,positionTouchedY, this.pointCollection.get(i).getRadius()*2)){
                        if(this.isSelected){ // if a point is already selected
                            if(this.pointCollection.get(i).isSelected()){ // check if the point touched is the one selected,
                                // preventing other points to get selected as well

                                // update point position to the position touched
                                this.pointCollection.get(i).setPosition(positionTouchedX,positionTouchedY);
                                this.pointCollection.get(i).setSelected(true);
                                break;
                            }
                        }else { // no point is yet selected, select the point and update it to the touched location
                            this.isSelected = true;
                            this.pointCollection.get(i).setPosition(positionTouchedX,positionTouchedY);
                            this.pointCollection.get(i).setSelected(true);
                            break;
                        }
                    }else{
                        this.pointCollection.get(i).setSelected(false); // deselect other points
                    }
                }
                break;
        }
        this.updatePointCollection(); // reorder points by x size
        this.cv.invalidate();// refresh PitCv
        return  true;
    }

    private boolean isTouchInside(float pCenterX,float pCenterY, float positionTouchedX,float positionTouchedY, float radius){
        // checking if the touched point is inside a drawn point

        float distanceX = positionTouchedX - pCenterX;
        float distanceY = positionTouchedY - pCenterY;
        boolean isInside =  (distanceX * distanceX) + (distanceY * distanceY) <= radius * radius;
        return isInside;
    }

    private void addPoint(){
        // add a point to the grid

        for(int i = 0; i<this.pointCollection.size(); i++){ // deselect all points
            this.pointCollection.get(i).setSelected(false);
        }
        PitPointClass ppc = new PitPointClass(cv.getWidth()/2,cv.getHeight()/2);
        ppc.setSelected(true); // select new point
        this.isSelected = true;
        this.pointCollection.add(ppc);// add to collection
        this.updatePointCollection(); // reorder points by x
        this.cv.invalidate(); // refresh PitCv
    }

    private void updatePointCollection() {
        // reorders the points in the collection by x

        Collections.sort(pointCollection, new Comparator<PitPointClass>() {
            @Override
            public int compare(PitPointClass o1, PitPointClass o2) {
                return Integer.compare((int)o1.getX(), (int)o2.getX());
            }
        });
    }
}
