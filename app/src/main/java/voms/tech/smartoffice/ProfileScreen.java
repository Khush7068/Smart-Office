package voms.tech.smartoffice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import voms.tech.smartoffice.Service.Serverurl;
import voms.tech.smartoffice.Utility.Commonhelper;

public class ProfileScreen extends AppCompatActivity {

    private TextView custid, emailId, city, active, edu, role, address;
    private Commonhelper helper;

    private void INITVIEW() {
        setContentView(R.layout.activity_profile_screen);
        custid = findViewById(R.id.custid);
        emailId = findViewById(R.id.emailId);
        city = findViewById(R.id.city);
        edu = findViewById(R.id.edu);
        active = findViewById(R.id.active);
        role = findViewById(R.id.role);
        address = findViewById(R.id.address);
    }

    private void EVENTCLICK() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INITVIEW();
        EVENTCLICK();
    }

    private void Exe_Forget_password(final String email) {

        if (helper.isNetworkConnected()) {

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(50000);
            String Para = "";
            Para += "emailOrMobile=" + email;
            String url = Serverurl.user_profile + Para;

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
//                            helper.callintent(ProfileScreen.this, LoginScreen.class);
//                            helper.ShowMesseage("Check your email");
//                            finish();
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
