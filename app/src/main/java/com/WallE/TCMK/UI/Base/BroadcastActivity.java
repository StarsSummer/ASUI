package com.WallE.TCMK.UI.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.WallE.TCMK.UI.UIActivities.RegisterActivity;

/**
 * Created by Jinffee on 2017/9/13.
 */

public abstract class BroadcastActivity extends BasicActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * set broadcastReceiver
         * registe broadcast
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver() ;
        IntentFilter intentFilter = new IntentFilter(getFilter()) ;
        localBroadcastManager.registerReceiver(broadcastReceiver ,intentFilter);
    }

    /**
     * implement onReceiver of new BroadcastReceiver
     * @param context
     * @param intent
     */
    protected abstract void onReceive(Context context, Intent intent);

    /**
     * get name of action which need filter
     * @return filter action
     */
    protected abstract String getFilter();

    /**
     * implement new BroadcastReceiver
     */
    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            BroadcastActivity.this.onReceive(context, intent);
        }
    }
}
