package mdg.com.notifs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by mansi on 4/6/18.
 */

@Dao
public interface DaoAccess {
    @Insert
    void insertSingleNotification (Notifications notif); //add a single row to the database

    @Query("SELECT * FROM notifications_table")
    List<Notifications> getAll(); //getting all rows of database
}
