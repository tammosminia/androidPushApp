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
    static final public String TOMPOES_MESSAGE_NAME = "TOMPOES_MESSAGE_NAME";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String action = data.getString("action");
        String name = data.getString("name");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "Action: " + action);
        Log.d(TAG, "Name: " + name);

        sendNotification(message, action, name);
        if (action != null && action.toLowerCase().equals("tompoes")) {
            toMainActivity("tom poes actie! " + name);
        } else {
            toMainActivity(message);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg, String action, String name) {
        Intent intent;
        if (action != null && action.toLowerCase().equals("tompoes")) {
            intent = new Intent(this, TomPoesActivity.class);
            intent.putExtra(TOMPOES_MESSAGE_NAME, name);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(nl.ing.messaging.push.testpush.R.drawable.ic_stat_gcm)
        .setContentTitle("TestPush notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void toMainActivity(String msg) {
        Intent intent = new Intent(INCOMING_MESSAGE);
        intent.putExtra("message", msg);
        broadcaster.sendBroadcast(intent);
    }
}
