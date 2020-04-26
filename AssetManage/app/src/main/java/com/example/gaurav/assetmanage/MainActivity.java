package com.example.gaurav.assetmanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText Login, Pass;
    Button getLogin;
    String getName, getPass;
    TextView fP, Sig;
    final String type = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = (EditText) findViewById(R.id.editText);
        Pass = (EditText) findViewById(R.id.editText2);
        getLogin = (Button) findViewById(R.id.button);
        fP = (TextView) findViewById(R.id.textView2);
        Sig = (TextView) findViewById(R.id.textView4);
        Pass.setTransformationMethod(new PasswordTransformationMethod());

    }
        public void userCl(final View view) {
     /*       getName = Login.getText().toString();
            getPass = Pass.getText().toString();
            final Database1 database1 = new Database1(this);
            database1.execute(type, getName, getPass);
            try {

                if (database1.get().contains("Success")) {
                    Toast.makeText(MainActivity.this, "Login Success ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoggedPage.class));
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("Login Error");
                    alert.setCancelable(true);
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                Pass.setText("");
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.setTitle("Error");
                    alertDialog.show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

*/
            startActivity(new Intent(this, LoggedPage.class));

        }


        public void helloP(View view) {

        }

        public Context getActivity() {
            return this;
        }

        public void setSig2(View view) {
            startActivity(new Intent(getActivity(), RegisterIt.class));
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            try {
                getMenuInflater().inflate(R.menu.mainmenu, menu);

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Exception caught" + e.toString(), Toast.LENGTH_LONG).show();
            }
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.setting:
                    startActivity(new Intent(getActivity(),Setting.class));
                case  R.id.exit:
                    finish();
                default:
                    return super.onOptionsItemSelected(item);

            }

        }
}
