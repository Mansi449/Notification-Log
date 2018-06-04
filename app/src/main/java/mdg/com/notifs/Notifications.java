package mdg.com.notifs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mansi on 4/6/18.
 */
@Entity(tableName = "notifications_table")
public class Notifications {

    @PrimaryKey(autoGenerate = true)
    private int notif_id;

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "notif_title")
    private String notifTitle;

    @ColumnInfo(name = "notif_text")
    private String notifText;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getNotif_id() {
        return notif_id;
    }

    public void setNotif_id(int notif_id) {
        this.notif_id = notif_id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getNotifText() {
        return notifText;
    }

    public void setNotifText(String notifText) {
        this.notifText = notifText;
    }
}
