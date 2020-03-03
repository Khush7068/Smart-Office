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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import voms.tech.smartoffice.Service.Serverurl;
import voms.tech.smartoffice.Utility.Commonhelper;

public class ForgetPassword extends AppCompatActivity {

    private EditText email;
    private Button reset;
    private Commonhelper helper;

    private void initview() {
        setContentView(R.layout.forget_password);
        helper = new Commonhelper(this);
        email = findViewById(R.id.etx_email);
        reset = findViewById(R.id.btn_reset);
    }

    private void OnClick() {
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                if (TextUtils.isEmpty(txt_email)) {
                    email.setError("Enter email address");
                    return;
                }
                Exe_Forget_password(txt_email);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initview();
        OnClick();
    }

    private void Exe_Forget_password(final String email) {

        if (helper.isNetworkConnected()) {

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(50000);
            String Para = "";
            Para += "userDetail=" + email;
            String url = Serverurl.Forget + Para;

            asyncHttpClient.get(url, null, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    helper.ShowLoader();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        helper.HideLoader();
                        {
                            helper.callintent(ForgetPassword.this, LoginScreen.class);
                            helper.ShowMesseage("Check your email");
                            finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    helper.HideLoader();
                    Log.e("tag", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    helper.HideLoader();
                    Log.e("tag", throwable.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    helper.HideLoader();
                    Log.e("tag", throwable.toString());
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
