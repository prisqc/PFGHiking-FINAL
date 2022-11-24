package com.example.pfghiking;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerRutaTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerRutaTouchHelperListener listenerHelper;

    public RecyclerRutaTouchHelper(int dragDirs, int swipeDirs,
                                   RecyclerRutaTouchHelperListener listenerHelper) {
        super( dragDirs, swipeDirs );
        this.listenerHelper = listenerHelper;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
       // super.onSelectedChanged( viewHolder, actionState );
        if (viewHolder != null){
            View foregroundView = ((RecyclerRutaAdapter.RecyclerHolder) viewHolder).layoutDelete;
            getDefaultUIUtil().onSelected( foregroundView );
        }

    }


    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull
            RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

       // super.onChildDrawOver( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );
        View foregroundView = ((RecyclerRutaAdapter.RecyclerHolder) viewHolder).layoutDelete;
        getDefaultUIUtil().onDrawOver( c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive );

    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //super.clearView( recyclerView, viewHolder );
        View foregroundView = ((RecyclerRutaAdapter.RecyclerHolder) viewHolder).layoutDelete;
        getDefaultUIUtil().clearView( foregroundView );
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        //super.onChildDraw( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );
        View foregroundView = ((RecyclerRutaAdapter.RecyclerHolder) viewHolder).layoutDelete;
        getDefaultUIUtil().onDraw( c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive );
    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection( flags, layoutDirection );
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listenerHelper.onSwipe( viewHolder, direction, viewHolder.getAdapterPosition()  );
    }

    public interface RecyclerRutaTouchHelperListener{
        void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

}
