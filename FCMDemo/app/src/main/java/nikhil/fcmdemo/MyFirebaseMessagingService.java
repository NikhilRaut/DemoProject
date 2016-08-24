package nikhil.fcmdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("FFFFFFFFFFFFFFFFFFFFFFF", "remoteMessage -- " + remoteMessage.getSentTime());
        Log.e("FFFFFFFFF", "From: " + remoteMessage.getFrom());
        Log.e("FFF", "Notification Message Body: " + remoteMessage.getNotification().getBody());

    }
    //AIzaSyD7KIyLufGosfzPgJcszBRmH_hQb41_dhQ


}
