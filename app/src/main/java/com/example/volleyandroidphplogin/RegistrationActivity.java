package com.example.volleyandroidphplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    TextInputEditText name ,username ,password;
    Button rregister;
    String sname,susername,spassword;
    ProgressDialog pdDialog;
    String URL_REGISTER = "http://asistencia.dx.am/registration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(TextInputEditText)findViewById(R.id.rname);
        username=(TextInputEditText)findViewById(R.id.rusername);
        password=(TextInputEditText)findViewById(R.id.rpassword);
        rregister=(Button) findViewById(R.id.rregister);

        pdDialog= new ProgressDialog(RegistrationActivity.this);
        pdDialog.setTitle("Registering please wait...");
        pdDialog.setCancelable(false);


        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString().trim();
                susername=username.getText().toString().trim();
                spassword=password.getText().toString().trim();

                if(sname.isEmpty()||susername.isEmpty()||spassword.isEmpty())
                {
                    Toast.makeText(RegistrationActivity.this,"please enter valid data",Toast.LENGTH_SHORT).show();
                }
                else{
                    Register();
                }
            }
        });
    }

    private void Register()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();

                                Intent login = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(login);
                                finish();

                            }
                            if(success.equals("0")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }
                            if(success.equals("3")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                            pdDialog.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Registration Error !2"+error, Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("name",sname);
                params.put("username",susername);
                params.put("password",spassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
