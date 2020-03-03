package voms.tech.smartoffice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class SignUpScreen extends AppCompatActivity {

    private EditText name , email , contact , password;
    private TextView tv_login ;
    private Button signup;

    private Commonhelper helper;

    private void initview(){
        setContentView(R.layout.sign_up_screen);
        helper = new Commonhelper(this);
        name = findViewById(R.id.etxname);
        contact = findViewById(R.id.etx_mobile);
        email = findViewById(R.id.etx_email);
        password = findViewById(R.id.etx_password);
        tv_login = findViewById(R.id.txt_login);
        signup = findViewById(R.id.btn_signup);
    }

    private void OnClick(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(SignUpScreen.this,MainActivity.class));
//                finish();

//                String

//                Exe_SIGNUP

            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpScreen.this,LoginScreen.class));
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        OnClick();
    }

    private void Exe_SIGNUP(final String username, final String password) {

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
//                            helper.callintent(LoginScreen.this, MainActivity.class);
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
}
