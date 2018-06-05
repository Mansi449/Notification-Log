package mdg.com.notifs;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by mansi on 5/6/18.
 */

public class RecyclerViewModel {
    private Bitmap app_icon;
    private String app_name;
    private String title;
    private String text;

    public RecyclerViewModel(Bitmap app_icon, String app_name, String title, String text) {
        this.app_icon = app_icon;
        this.app_name = app_name;
        this.title = title;
        this.text = text;
    }

    public Bitmap getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Bitmap app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
