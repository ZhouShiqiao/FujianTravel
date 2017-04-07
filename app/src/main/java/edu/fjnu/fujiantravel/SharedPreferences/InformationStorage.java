package edu.fjnu.fujiantravel.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.user.Tourist;
import edu.fjnu.fujiantravel.user.User;
import edu.fjnu.fujiantravel.user.UserThread;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class InformationStorage {
    private final static InformationStorage instance = new InformationStorage();

    private InformationStorage() {

    }

    public static InformationStorage getInstance() {
        return instance;
    }

    public void loginfo(String id, String password, int state, Context context) {
        SharedPreferences sp = context.getSharedPreferences("sp_loginfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id);
        editor.putString("password", password);
        editor.putInt("state", state);
        editor.commit();
    }
    public void databaseinfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("sp_databaseinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putInt("state",0);
        editor.commit();
    }

    public Boolean initdatabase(Context context) {
        SharedPreferences sp = context.getSharedPreferences("sp_databaseinfo", Context.MODE_PRIVATE);
        if (sp.getInt("state", 0) == 1)
            return true;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("state", 1);
        editor.commit();
        return false;
    }

    public Boolean autolog(Context context) {
        SharedPreferences sp = context.getSharedPreferences("sp_loginfo", Context.MODE_PRIVATE);
        if (sp.getInt("state", 0) != User.LOG_SUCCESS)
            return false;
        User user = new User();
        user.setid(sp.getString("id", null));
        user.setpasswd(sp.getString("password", null));
        Thread thread = new Thread(new UserThread(User.USERLOG, user, context.getString(R.string.server_address),
                Integer.parseInt(context.getString(R.string.server_port))));
        thread.start();
        return true;
    }
}
