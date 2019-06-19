package com.dieam.reactnativepushnotification.modules;

import android.app.IntentService;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import static com.dieam.reactnativepushnotification.modules.RNPushNotification.LOG_TAG;

public class RNPushNotificationRegistrationService extends JobIntentService
{
    static final int JOB_ID = 100521;
    private static final String TAG = "RNPushNotification";

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, RNPushNotificationRegistrationService.class, JOB_ID, work);
    }

    public RNPushNotificationRegistrationService() {
        super(TAG);
    }
    @Override
    protected void onHandleWork(Intent intent) {
        try {
            String SenderID = intent.getStringExtra("senderID");
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(SenderID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            sendRegistrationToken(token);
        } catch (Exception e) {
            Log.e(LOG_TAG, TAG + " failed to process intent " + intent, e);
        }
    }

    private void sendRegistrationToken(String token) {
        Intent intent = new Intent(this.getPackageName() + ".RNPushNotificationRegisteredToken");
        intent.putExtra("token", token);
        sendBroadcast(intent);
    }
}