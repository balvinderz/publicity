package vinay.com.publicity;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Patil on 25-08-2016.
 */
public class ConfigApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
