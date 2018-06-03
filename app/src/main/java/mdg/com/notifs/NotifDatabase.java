package mdg.com.notifs;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by mansi on 4/6/18.
 */
@Database(entities = {Notifications.class}, version = 1, exportSchema = false)
public abstract class NotifDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
}
