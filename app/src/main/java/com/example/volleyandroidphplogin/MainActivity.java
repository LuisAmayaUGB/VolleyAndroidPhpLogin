package com.example.volleyandroidphplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    String sharedprofFile="com.example.volleyandroidphplogin";
    SharedPreferences.Editor preferencesEditor;
    String id, name, username;
    Button logout;
    TextView Signedinusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();
        logout = (Button)findViewById(R.id.logout);
        Signedinusername = (TextView)findViewById(R.id.signinusername);

        id=mPreferences.getString("SignedInUserID","null");
        name=mPreferences.getString("SignedInName","null");
        username = mPreferences.getString("SignedInusername","null");

        Signedinusername.setText(name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesEditor.putString("issignedin","false");
                preferencesEditor.apply();
                Intent loginscreen = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginscreen);
            }
        });

    }
}
