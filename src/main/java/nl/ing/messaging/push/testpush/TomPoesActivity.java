package nl.ing.messaging.push.testpush;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TomPoesActivity extends Activity {
    TextView mStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tompoes);
        mStory = (TextView) findViewById(R.id.story);

        Log.i("Push", "onCreate");
        handleIntent(getIntent());
    }

    void changeCharacter(String name) {
        Log.i("Push", "changeCharacter " + name);
        switch (name.toLowerCase()) {
            case "tompoes": setStory("Daar komt ellende van"); break;
            case "bommel": setStory("Als je begrijpt wat ik bedoel"); break;
            case "wammes": setStory("Slapen is veel leukerder, want dan verveel je je niet."); break;
            case "bullebas": setStory("Ik ga je opschrijven Bommel, wat is je naam?"); break;
            default: setStory("Onbekend persoon: " + name);
        }
    }

    void setStory(String story) {
        mStory.setText(story);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("Push", "onNewIntent");
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        String s = intent.getStringExtra(GcmIntentService.TOMPOES_MESSAGE_NAME);
        if(s != null) {
            changeCharacter(s);
        }
    }

}
