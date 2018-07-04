package com.example.a28062.okhttp3postdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.pass_world)
    EditText passWorld;
    @BindView(R.id.sommint)
    Button sommint;
    OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sommint)
    public void onViewClicked() {

        String username = userName.getText().toString().trim();
        String passworld = passWorld.getText().toString().trim();

//        loginWithFrom(username,passworld);

        loginWithJson(username,passworld);

    }

    private void loginWithJson(String username, String passworld) {
        okHttpClient = new OkHttpClient();

        String URL = Constant.API.BASE_URL;

        JSONObject obj = new JSONObject();

        try {

            obj.put("username",username);
            obj.put("passworld",passworld);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonParams = obj.toString();

        RequestBody body = RequestBody.create(MediaType.parse("Application/json"),jsonParams);

        Request request =new Request.Builder()
                .post(body)
                .url(URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Log.d("MainActivity","服务器开小差了");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    String json = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        final String message  = jsonObject.optString("message");
                        final int success  = jsonObject.optInt("success");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (success == 1){
                                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }


    private void loginWithFrom(String username,String passworld){
        okHttpClient = new OkHttpClient();

        String URL = Constant.API.BASE_URL;
        RequestBody body = new FormBody.Builder().add("username",username).add("passworld",passworld).build();

         Request request =new Request.Builder()
                .post(body)
                .url(URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Log.d("MainActivity","服务器开小差了");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    String json = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        final String message  = jsonObject.optString("message");
                        final int success  = jsonObject.optInt("success");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (success == 1){
                                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

}
