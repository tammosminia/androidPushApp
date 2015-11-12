package nl.ing.messaging.push.testpush;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setComponent(new ComponentName(context.getPackageName(), GcmIntentService.class.getName()));
        startWakefulService(context, intent);
    }
}
