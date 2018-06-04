package mdg.com.notifs;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "notifications_database";
    NotifDatabase database;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, NotifService.class));

        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

        /*
            Initializing the database
         */
        database = Room.databaseBuilder(getApplicationContext(),NotifDatabase.class,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            /*
                Storing various components of a notification in String variables
             */
            final String title = intent.getStringExtra("title");
            final String text = intent.getStringExtra("text");
            final String app_name = intent.getStringExtra("app name");
            final String packageName = intent.getStringExtra("package");

            Log.e("title", title);
            Log.e("text", text);
            Log.e("app_name", app_name);
            Log.e("Packa", packageName);

           /*
                getting the icon of the application sending the notification throguh its package name
            */
            try {
                Drawable icon = getPackageManager().getApplicationIcon(packageName);

                /*
                    Converting the drawable object (icon) to byte array for storing in the database
                 */
                Bitmap bitmap = ((BitmapDrawable)icon).getBitmap(); //get bitmap from drawable object
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.WEBP,25,stream); // Compress the bitmap with WEBP format and quality 25%
                byteArray = stream.toByteArray();

                //imageView.setImageDrawable(icon);
            }
            catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            /*
                Storing the notification details in the database
             */

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Notifications notification =new Notifications();
                    notification.setAppName(app_name);
                    notification.setNotifTitle(title);
                    notification.setNotifText(text);
                    notification.setImage(byteArray);
                    database.daoAccess ().insertSingleNotification(notification);
                }
            }) .start();
        }
    };

    /*
        Menu option to give notification access to the app
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings_icon:
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
        return true;
    }
}