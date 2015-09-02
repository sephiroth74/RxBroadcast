package it.sephiroth.rxbroadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import rx.Observable;

public final class RxBroadcast {
    private RxBroadcast() {
        throw new AssertionError("No instances");
    }

    /**
     * Create Observable that wraps BroadcastReceiver and emits received intents.
     *
     * @param filter Selects the Intent broadcasts to be received.
     */
    public static Observable<Intent> fromBroadcast(Context context, IntentFilter filter) {
        return Observable.create(new OnSubscribeBroadcastRegister(context, filter, null, null));
    }

    /**
     * Create Observable that wraps BroadcastReceiver and emits received intents.
     *
     * @param filter              Selects the Intent broadcasts to be received.
     * @param broadcastPermission String naming a permissions that a
     *                            broadcaster must hold in order to send an Intent to you.  If null,
     *                            no permission is required.
     * @param schedulerHandler    Handler identifying the thread that will receive
     *                            the Intent.  If null, the main thread of the process will be used.
     */
    public static Observable<Intent> fromBroadcast(
        Context context, IntentFilter filter, String broadcastPermission, Handler schedulerHandler) {
        return Observable.create(new OnSubscribeBroadcastRegister(context, filter, broadcastPermission, schedulerHandler));
    }

    /**
     * Create Observable that wraps BroadcastReceiver and connects to LocalBroadcastManager
     * to emit received intents.
     *
     * @param filter Selects the Intent broadcasts to be received.
     */
    public static Observable<Intent> fromLocalBroadcast(Context context, IntentFilter filter) {
        return Observable.create(new OnSubscribeLocalBroadcastRegister(context, filter));
    }
}