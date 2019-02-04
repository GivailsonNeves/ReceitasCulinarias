package com.example.givasneves.receitasculinarias.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplication(), intent);
    }
}
