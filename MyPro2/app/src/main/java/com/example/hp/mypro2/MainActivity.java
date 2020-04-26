package com.example.hp.mypro2;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText name1,pass1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button b;
        b=(Button)findViewById(R.id.Login);
        name1=(EditText)findViewById(R.id.NameT);
        pass1=(EditText)findViewById(R.id.Password);
    }
    public void  LoginW(View view)
    {
        String name,pass;
        name=name1.getText().toString();
        pass=pass1.getText().toString();
        if(name.equals("")|| pass.equals(""))
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginB loginB = new LoginB(getApplicationContext());
            AsyncTask<String,String,String> asyncTask =  loginB.execute(name.toString(), pass.toString());
            try {
                if(asyncTask.get().contains("Success"))
                {
                    startActivity(new Intent(getApplicationContext(), MainPage2.class));
                }
                else
                {
                    Toast.makeText(this, "Retry \n"+loginB.get().toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }
}
