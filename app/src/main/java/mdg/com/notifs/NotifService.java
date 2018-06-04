package mdg.com.notifs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by mansi on 3/6/18.
 */

public class NotifService extends NotificationListenerService {

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        String package_name = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        Intent msgrcv = new Intent("Msg");
        try
        {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo;
            applicationInfo = packageManager.getApplicationInfo(sbn.getPackageName(), 0);

            msgrcv.putExtra("app name", packageManager.getApplicationLabel(applicationInfo));
        }
        catch(PackageManager.NameNotFoundException e)
        {
            msgrcv.putExtra("app name", "unknown application");
        }
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);
        msgrcv.putExtra("package",package_name);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        }

}
