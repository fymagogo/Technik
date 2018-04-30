package ra.olympus.zeus.events.Search.Recycler.Implement;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.annotations.Expose;

/**
 * Created by alfre on 26/04/2018.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    private GestureDetector gestureDetector;
    private ClickListener clickListener;


    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
    this.clickListener = clickListener;


    gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
            if (child != null && clickListener != null){
                clickListener.onLongClick(child,recyclerView.getChildPosition(child));
            }
        }
    });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onClick(child, rv.getChildPosition(child));
        }

        return false;
    }

    public interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
}

