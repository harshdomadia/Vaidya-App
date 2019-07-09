package com.sihmaxhealth.drawerfragmeny;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class loginandsignup extends Fragment {
    EditText username, password, reg_username, reg_password,
            reg_firstName, reg_lastName, reg_email, reg_confirmemail;
    Button login, signUp, reg_register;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    CheckBox rememberMe;
    public databasefile dbt;
    String Uname,Pass;
    databasefile mDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDB=new databasefile(getContext(),"SignUpDB","Signup");
       // dbt=new databasefile();
        //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setRetainInstance(true);
        return inflater.inflate(R.layout.loginandsignup,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        signUp = view.findViewById(R.id.signUp);
        txtInLayoutUsername = view.findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = view.findViewById(R.id.txtInLayoutPassword);
        rememberMe = view.findViewById(R.id.rememberMe);
        //dbt=new databasefile(view.getContext(),"SignUp",null,1);
        ClickLogin();


        //SignUp's Button for showing registration page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });

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
                        Fragment fragment = new ChatBot();

                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.screen_area, fragment).commit();




                            //MainActivity.ft.commit();

                    }

                }

            }

        });

    }
    private void ClickSignUp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
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


}
