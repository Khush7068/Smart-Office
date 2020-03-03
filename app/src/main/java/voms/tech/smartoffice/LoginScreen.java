package voms.tech.smartoffice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import voms.tech.smartoffice.Service.Serverurl;
import voms.tech.smartoffice.Utility.Commonhelper;
import voms.tech.smartoffice.Utility.Shared;

public class LoginScreen extends AppCompatActivity {

    private EditText email, password;
    private TextView tv_signup, tv_forget_pass;
    private Button login, google_login;

    private Commonhelper helper;

    private void INITVIEW() {
        setContentView(R.layout.login_screen);
        helper = new Commonhelper(this);
        email = findViewById(R.id.etx_email);
        password = findViewById(R.id.etx_password);
        tv_forget_pass = findViewById(R.id.txt_forgot_password);
        tv_signup = findViewById(R.id.txt_dont_hv_account);
        login = findViewById(R.id.btn_login);
//        google_login = findViewById(R.id.btn_ggl_cust);
    }

    private void EVENTCLICK() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.callintent(LoginScreen.this, MainActivity.class);
                /*String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email)) {
                    email.setError("Enter your username");
                    return;
                }
                if (TextUtils.isEmpty(txt_password)) {
                    password.setError("Enter your password");
                    return;
                }
                Exe_Login(txt_email, txt_password);*/
            }
        });

        //auto login
        String log_status = helper.getSharedPreferences(Shared.sp_login_status, "False");
        if (log_status.equals("True")) {
            helper.callintent(LoginScreen.this, MainActivity.class);
            finish();
        }

        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, ForgetPassword.class));
            }
        });

        /*google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginScreen.this, "Still in development ", Toast.LENGTH_SHORT).show();
            }
        });*/

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INITVIEW();
        EVENTCLICK();

    }

    private void Exe_Login(final String username, final String password) {

        if (helper.isNetworkConnected()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(50000);

            String Para = "";
            Para += "username=" + username;
            Para += "&password=" + password;
            String url = Serverurl.Login + Para;

            String postdate = "ABC";
            asyncHttpClient.get(url, null, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    helper.ShowLoader();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        helper.HideLoader();

                        Log.d("KS", response.toString());
                        String status = response.getString("status");
                        String mesg = response.getString("mesg");
                        if (status.equals("1")) {
                            helper.setSharedPreferences(Shared.sp_name, response.getString("user_name"));
                            helper.setSharedPreferences(Shared.sp_passport_name, response.getString("passport_name"));
                            helper.setSharedPreferences(Shared.sp_email, response.getString("email"));
                            helper.setSharedPreferences(Shared.sp_phone, response.getString("contact"));
                            helper.setSharedPreferences(Shared.sp_login_status, "True");
                            helper.callintent(LoginScreen.this, MainActivity.class);
                            finish();
                        } else {
                            helper.ShowMesseage(mesg);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    helper.HideLoader();
                    Log.e("tagg", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    helper.HideLoader();

                    Log.e("tagg", throwable.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    helper.HideLoader();
                    Log.e("tagg", throwable.toString());
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    long percentage = (bytesWritten / totalSize) * 100;
                    //progressDialog.setProgress(((int) percentage));
                }
            });
        } else {
            new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                    .setTitle("Network Error")
                    .setCancelable(true)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })
                    .show();

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
//                saveResult();
//                MyActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                MyActivity.super.onBackPressed();
            }
        });
        builder.show();
    }
}
