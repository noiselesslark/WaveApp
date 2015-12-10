package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * This class is an extension of the view that handles the zoness within which users can touch.
 * as well as detecting multi-touch events.
 */
public abstract class TouchZonesView extends View {
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    protected final int MAX_NUMBER_OF_TOUCHES_DETECTED = 6;
    private ArrayList<TouchZone> touchZones;

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     * @param defStyle - the style definition.
     */
    public TouchZonesView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public TouchZonesView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    /**
     * Constructor.
     * @param context - the context.
     */
    public TouchZonesView(Context context) {
        super(context);
        initView();
    }

    /**
     * Initialise the view. Draw basics.
     */
    protected void initView(){
        touchZones = new ArrayList<>();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        defineTouchZones();
    }

    /**
     * Called when vie is measured.
     * @param widthMeasureSpec - the width measure spec.
     * @param heightMeasureSpec - the height measure spec.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * Called when view is re-drawn. Draws touch zones.
     * @param canvas - the canvas.
     */
    @Override
    protected void onDraw(Canvas canvas){
        for(int i = 0; i < MAX_NUMBER_OF_TOUCHES_DETECTED; i++){
            TouchZone tp = touchZones.get(i);

            canvas.drawCircle(tp.getX(), tp.getY(), TouchZone.OUTER_CIRCLE_RADIUS, tp.getOuterCirclePaint());
            if(tp.isTouched()){
                canvas.drawCircle(tp.getX(), tp.getY(), TouchZone.INNER_CIRCLE_RADIUS, tp.getInnerCirclePaint());
            }
        }
    }

    /**
     * Overrides the View class's event handler for touch events. Detects whether the toouch is
     * within a touch zone.
     *
     * @param event - the touch event.
     * @return boolean representing whether touch event was consumed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = (event.getAction() & MotionEvent.ACTION_MASK);
        int pointCount = event.getPointerCount();

        for (int i = 0; i < pointCount; i++){
            int id = event.getPointerId(i);

            if(id < MAX_NUMBER_OF_TOUCHES_DETECTED) {
                int xPoint = (int)event.getX(i) - this.getLeft();
                int yPoint = (int)event.getY(i) - this.getTop();

                handleTouchEvent(xPoint, yPoint, action);
            }
        }

        invalidate(); //Refresh canvas.
        return true;
    }

    /**
     * Handles touch events logic.
     *
     * @param x - the x position of the touch event.
     * @param y - the y position of the touch event.
     * @param action - the action of the event.
     */
    protected void handleTouchEvent(int x, int y, int action) {
        for(TouchZone zone : touchZones){
            if(zone.isPointWithin(x, y)){
                if(action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_DOWN){
                    zone.setTouched(true);
                } else if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP){
                    zone.setTouched(false);
                }
            }
        }
    }

    /** Private Methods */

    /**
     * Defines where on the canvas the touch zones should be drawn.
     */
    private void defineTouchZones() {
        int circleRadius = Math.round(TouchZone.OUTER_CIRCLE_RADIUS);
        int refWidth = 480; // Width from reference phone for which the offset was initially calculated.
        int offset = Math.round(screenWidth * 10 / refWidth);
        
        int xLeft = circleRadius + offset;
        int xCentre = Math.round(screenWidth/2);
        int xRight = screenWidth - circleRadius - offset;

        int yTop = circleRadius + offset;
        int yHigh =  Math.round(screenHeight/3);
        int yLow = Math.round(2*screenHeight/3);
        int yDown = screenHeight - circleRadius - offset;
        
        touchZones.add(new TouchZone(xCentre, yTop));
        touchZones.add(new TouchZone(xLeft, yHigh));
        touchZones.add(new TouchZone(xLeft, yLow));
        touchZones.add(new TouchZone(xRight, yHigh));
        touchZones.add(new TouchZone(xRight, yLow));
        touchZones.add(new TouchZone(xCentre, yDown));
    }
}
