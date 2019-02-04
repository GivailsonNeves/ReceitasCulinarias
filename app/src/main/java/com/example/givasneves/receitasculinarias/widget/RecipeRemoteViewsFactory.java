package com.example.givasneves.receitasculinarias.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.junit.rules.TestRule;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public RecipeRemoteViewsFactory(Context applicationContexto, Intent intent) {
        mContext = applicationContexto;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
//        final long identityToken = Binder.clearCallingIdentity();
//        Uri uri = Contract.PATH_TODOS_URI;
//        mCursor = mContext.getContentResolver().query(uri,
//                null,
//                null,
//                null,
//                Contract._ID + " DESC");
//
//        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

//        if (position == AdapterView.INVALID_POSITION ||
//                mCursor == null || !mCursor.moveToPosition(position)) {
//            return null;
//        }
//
//        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
//        rv.setTextViewText(R.id.widgetItemTaskNameLabel, mCursor.getString(1));
//
//        return rv;

        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
