package com.sihmaxhealth.drawerfragmeny;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static FragmentManager fragmentManager;
    public static FragmentTransaction ft;

    public static  JSONObject response;
    public RequestQueue mQueue;
    EditText username, password, reg_username, reg_password,
            reg_firstName, reg_lastName, reg_email, reg_confirmemail;
    Button login, signUp, reg_register;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    CheckBox rememberMe;
    public databasefile dbt;
    String Uname,Pass;
    databasefile mDB;
    private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent homeIntent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }

        },SPLASH_TIME_OUT);*/
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDB=new databasefile(this,"SignUpDB","Signup");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);
        txtInLayoutUsername = (TextInputLayout) findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = (TextInputLayout) findViewById(R.id.txtInLayoutPassword);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        //dbt=new databasefile(view.getContext(),"SignUp",null,1);
        //ClickLogin();
        mQueue= Volley.newRequestQueue(this);
        jsonParse();



        //SignUp's Button for showing registration page
        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });*/


    }
    public void jsonParse()
    {
        String url="https://api.jsonbin.io/b/5c7b6c600e75295893375f6d/latest";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response1) {
                try {
                    /*JSONArray jsonArray=response.getJSONArray("Name");
                    System.out.println("fromhere");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject employee=jsonArray.getJSONObject(i);
                        String firstName=employee.getString("Name");
                        System.out.println("FirstNAme");
                        System.out.println(firstName);

                    }*/
                    MainActivity.response=response1;

                    System.out.println(response);



                } catch (Exception e) {
                    System.out.println("JSON ERx");


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    private void ClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().trim().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(view, "Please fill out these fields",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutUsername.setError("Username should not be empty");
                } else {
                    Uname=username.getText().toString();
                    //Here you can write the codes for checking username
                }
                if (password.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Please fill out these fields",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                    txtInLayoutPassword.setError("Password should not be empty");
                } else {
                    Pass=password.getText().toString();
                    //Here you can write the codes for checking password
                }

                if (rememberMe.isChecked()) {
                    //Here you can write the codes if box is checked
                } else {
                    //Here you can write the codes if box is not checked
                }
                if(!Uname.isEmpty() && !Pass.isEmpty())
                {
                    // databasefile db=new databasefile(getContext(),"signupDB","signup");
                    Cursor res=mDB.verifyData(Uname,Pass);
                    if(res.getCount()!=0)
                    {
                        System.out.println("Entered");
                        /*Fragment fragment = new ChatBot();
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        //fragmentManager.replace(R.id.screen_area, fragment).commit();

                        fragmentManager.beginTransaction().replace(R.id.screen_area,fragment).commit();



                        //MainActivity.ft.commit();*/

                    }

                }

            }

        });

    }
    private void ClickSignUp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.register, null);
        dialog.setView(dialogView);

        reg_username = dialogView.findViewById(R.id.reg_username);
        reg_password = dialogView.findViewById(R.id.reg_password);
        reg_firstName = dialogView.findViewById(R.id.reg_firstName);
        reg_lastName = dialogView.findViewById(R.id.reg_lastName);
        reg_email = dialogView.findViewById(R.id.reg_email);
        reg_confirmemail = dialogView.findViewById(R.id.reg_confirmemail);
        reg_register = dialogView.findViewById(R.id.reg_register);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);
        final String[] UName = new String[1];
        final String[] Pass = new String[1];
        final String[] First = new String[1];
        final String[] Last = new String[1];
        final String[] Email=new String[1];
        final String[] confEmail=new String[1];
        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reg_username.getText().toString().trim().isEmpty()) {

                    reg_username.setError("Please fill out this field");
                } else {
                    UName[0] =reg_username.getText().toString().trim();
                    //Here you can write the codes for checking username
                }
                if (reg_password.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    reg_password.setError("Please fill out this field");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                    //Here you can write the codes for checking password
                    Pass[0] =reg_password.getText().toString().trim();
                }
                if (reg_firstName.getText().toString().trim().isEmpty()) {

                    reg_firstName.setError("Please fill out this field");
                } else {
                    First[0] =reg_firstName.getText().toString().trim();
                    //Here you can write the codes for checking firstname

                }
                if (reg_lastName.getText().toString().trim().isEmpty()) {

                    reg_lastName.setError("Please fill out this field");
                } else {
                    Last[0] =reg_lastName.getText().toString().trim();

                    //Here you can write the codes for checking lastname
                }
                if (reg_email.getText().toString().trim().isEmpty()) {

                    reg_email.setError("Please fill out this field");
                } else {
                    Email[0]=reg_email.getText().toString().trim();
                    //Here you can write the codes for checking email
                }
                if (reg_confirmemail.getText().toString().trim().isEmpty()) {

                    reg_confirmemail.setError("Please fill out this field");
                } else {
                    confEmail[0]=reg_confirmemail.getText().toString().trim();
                    //Here you can write the codes for checking confirmemail
                }
                if(!Email[0].isEmpty() && !Last[0].isEmpty() && !First[0].isEmpty() && !UName[0].isEmpty() && !Pass[0].isEmpty() && Email[0].equals(confEmail[0]))
                {
                    //databasefile db=new databasefile(getContext(),"signupDB","signup");
                    ContentValues cntl=new ContentValues();
                    cntl.put("Username",UName[0]);
                    cntl.put("Password",Pass[0]);
                    cntl.put("FirstName",First[0]);
                    cntl.put("LastName",Last[0]);
                    cntl.put("Email",Email[0]);


                    mDB.adddData(cntl);
                }
            }
        });



        dialog.show();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment=null;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment=new loginandsignup();
        } else if (id == R.id.nav_gallery) {
            fragment=new ChatBot();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            fragment=new webView();

        }
        if(fragment!=null)
        {
            fragmentManager=getSupportFragmentManager();
            ft=fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
