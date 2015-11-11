package nl.ing.messaging.push.testpush;

import com.google.android.gms.gcm.GcmListenerService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class GcmIntentService extends GcmListenerService {
    public static final int NOTIFICATION_ID = 1;

    public static final String TAG = "testPush";
    LocalBroadcastManager broadcaster;
    static final public String INCOMING_MESSAGE = "INCOMING_MESSAGE";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        sendNotification(message);
        toActivity(message);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DemoActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(nl.ing.messaging.push.testpush.R.drawable.ic_stat_gcm)
        .setContentTitle("TestPush notification")
        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void toActivity(String msg) {
        Intent intent = new Intent(INCOMING_MESSAGE);
        intent.putExtra("message", msg);
        broadcaster.sendBroadcast(intent);
    }
}
