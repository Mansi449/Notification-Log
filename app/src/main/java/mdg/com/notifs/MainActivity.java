package mdg.com.notifs;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> notifsList;
    private static final String DATABASE_NAME = "notifications_database";
    NotifDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startService(new Intent(this, NotifService.class));

        String currentDBPath = getDatabasePath(DATABASE_NAME).getAbsolutePath();

        listView = (ListView) findViewById(R.id.list);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
        notifsList = new ArrayList<>();

        database = Room.databaseBuilder(getApplicationContext(),NotifDatabase.class,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String title = intent.getStringExtra("title");
            final String text = intent.getStringExtra("text");
            final String app_name = intent.getStringExtra("app name");

            Log.e("title", title);
            Log.e("text", text);
            Log.e("app_name", app_name);
            notifsList.add(title);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    notifsList);
            listView.setAdapter(adapter);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Notifications notification =new Notifications();
                    notification.setAppName(app_name);
                    notification.setNotifTitle(title);
                    notification.setNotifText(text);
                    database.daoAccess ().insertSingleNotification(notification);
                }
            }) .start();
        }
    };

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