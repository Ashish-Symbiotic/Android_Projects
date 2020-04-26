package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.RegisterUser;
import com.example.naveen.assatemanagement.databaseConnection.userCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Register extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username=(EditText)findViewById(R.id.editText4);
        final EditText password=(EditText)findViewById(R.id.editText5);
        final EditText rePassword=(EditText)findViewById(R.id.editText6);
        final EditText email=(EditText)findViewById(R.id.editText7);
        final EditText phone=(EditText)findViewById(R.id.editText8);
        final EditText enc=(EditText)findViewById(R.id.editText9);
        final Spinner type=(Spinner)findViewById(R.id.spinner5);
        final EditText name=(EditText)findViewById(R.id.editText12);
        Button register=(Button)findViewById(R.id.button8);
        ImageButton back=(ImageButton)findViewById(R.id.imageButton2);

        final String[] typeSpnner = new String[1];
        final List<String> typeofSpinner=new ArrayList<>();

        typeofSpinner.add("admin");
        typeofSpinner.add("employee");
        typeofSpinner.add("keeper");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeofSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSpnner[0] =typeofSpinner.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!email.getText().toString().contains("@"))
                {
                    email.setText(email.getText().toString()+"@gmail.com");
                }
            }
        });


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(!new userCheck().execute("username",username.getText().toString()).get().contains("true"))
                    {
                        username.setError("Username already register!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        enc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(!new userCheck().execute("ecn",enc.getText().toString()).get().contains("true"))
                    {
                        enc.setError("ECN already register!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(username.getText().toString().isEmpty())
                    {
                        username.setError("Field must required!");
                    }
                    else if(name.getText().toString().isEmpty())
                    {
                        name.setError("Field must required!");
                    }
                    else if(password.getText().toString().isEmpty())
                    {
                        password.setError("Field must required!");
                    }
                    else if(rePassword.getText().toString().isEmpty())
                    {
                        rePassword.setError("Field must required!");
                    }
                    else if(email.getText().toString().isEmpty())
                    {
                        email.setError("Field must required!");
                    }
                    else if(phone.getText().toString().isEmpty())
                    {
                        phone.setError("Field must required!");
                    }
                    else if(enc.getText().toString().isEmpty())
                    {
                        enc.setError("Field must required!");
                    }
                    else if(!password.getText().toString().equals(rePassword.getText().toString()))
                    {
                        rePassword.setText("");
                        rePassword.setError("Password must equal!");
                    }
                    else if(password.getText().toString().length()<8)
                    {
                        password.setError("Pasword lenth must be 8 digit or longer!");
                    }
                    else
                    {
                        String result=new RegisterUser().execute(username.getText().toString(),password.getText().toString(),email.getText().toString(),phone.getText().toString(),enc.getText().toString(), typeSpnner[0],name.getText().toString()).get();
                        if(result.contains("sucessfull"))
                        {
                            LoginDataTempStorage loginDataTempStorage=new LoginDataTempStorage();
                            loginDataTempStorage.setUsername(username.getText().toString());
                            Toast.makeText(Register.this, "User register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,AlertLogin.class));
                            finish();
                        }
                        else
                        {
                            password.setText("");
                            rePassword.setText("");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,AlertLogin.class));
                finish();
            }
        });

    }
}
