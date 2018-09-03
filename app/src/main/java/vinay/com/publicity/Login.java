package vinay.com.publicity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import layout.SharedResources;
import models.User;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnLogin;
    Firebase userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userRef=new Firebase(SharedResources.USER);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        checkSession();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAttempt();
            }
        });
    }
    private void checkSession(){
        SharedPreferences sp=getSharedPreferences(
                SharedResources.SharedUSERDATA,
                Context.MODE_PRIVATE);
        if(
                (!(sp.getString(SharedResources.SharedInital,"null").equals("null")))&&
                        (!(sp.getString(SharedResources.SharedNAME,"null").equals("null")))&&
                        (sp.getInt(SharedResources.SharedENTRIES,-99)!=-99)
                ){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);

            startActivity(intent);
        }
    }
    private void loginAttempt(){
        email.setError(null);
        password.setError(null);
        final String emailStr = email.getText().toString();
        final String passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(emailStr)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean isFound=false;
                    final String android_id = Settings.Secure.getString(getBaseContext()
                            .getContentResolver(), Settings.Secure.ANDROID_ID);
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        User user=ds.getValue(User.class);
                        if(emailStr.equals(user.getEmail())&&passwordStr.equals(user.getPassword())){
                            if(!android_id.equals(user.getDeviceid())){
                                final TextView tv=(TextView)findViewById(R.id.tvFindId);
                                tv.setVisibility(View.VISIBLE);
                                tv.setTextIsSelectable(true);
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tv.setText(android_id);
                                    }
                                });
                                isFound=false;
                                break;
                            }
                            SharedPreferences sharedPreferences=getSharedPreferences(
                                    SharedResources.SharedUSERDATA,
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(SharedResources.SharedNAME,user.getMember_name());
                            editor.putString(SharedResources.SharedInital,user.getMember_initial());
                            editor.putInt(SharedResources.SharedENTRIES,user.getLast_entry());
                            editor.apply();
                            isFound=true;
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    if(!isFound) {
                        password.setError(getString(R.string.error_incorrect_password));
                        password.requestFocus();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

}
