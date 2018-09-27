package vinay.com.publicity;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Patil on 25-08-2016.
 */
public class ConfigApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
       Firebase.setAndroidContext(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
