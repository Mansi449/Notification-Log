package mdg.com.notifs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mansi on 5/6/18.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotifViewHolder> {
    private ArrayList<Notifications> notifsList;

    public NotificationsAdapter(ArrayList<Notifications> notifsList) {
        this.notifsList = notifsList;
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder{
        public ImageView app_icon;
        public TextView app_name, notif_title, notif_text;

        public NotifViewHolder(View itemView) {
            super(itemView);
            app_icon = itemView.findViewById(R.id.app_icon);
            app_name = itemView.findViewById(R.id.app_name);
            notif_text = itemView.findViewById(R.id.text);
            notif_title = itemView.findViewById(R.id.title);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        Notifications model =  notifsList.get(position);

        byte[] byte_array = model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length);
        holder.app_icon.setImageBitmap(bitmap);
        holder.notif_title.setText(model.getNotifTitle());
        holder.notif_text.setText(model.getNotifText());
        holder.app_name.setText(model.getAppName());
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new NotifViewHolder(itemView);    }

    @Override
    public int getItemCount() {
        return notifsList.size();
    }
}
