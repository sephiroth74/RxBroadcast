
package it.sephiroth.rxbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

class OnSubscribeLocalBroadcastRegister implements Observable.OnSubscribe<Intent> {
    private final Context context;
    private final IntentFilter intentFilter;

    public OnSubscribeLocalBroadcastRegister(Context context, IntentFilter intentFilter) {
        this.context = context;
        this.intentFilter = intentFilter;
    }

    @Override
    public void call(final Subscriber<? super Intent> subscriber) {
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                subscriber.onNext(intent);
            }
        };

        final Subscription subscription = Subscriptions.create(
            new Action0() {
                @Override
                public void call() {
                    localBroadcastManager.unregisterReceiver(broadcastReceiver);
                }
            });

        subscriber.add(subscription);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
}