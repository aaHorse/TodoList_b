package com.example.zexiger.todolist_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Create_user;
import com.example.zexiger.todolist_b.LitePal_general.Sign_in_general;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

/*
* 登录界面
* */
public class FirstActivity extends AppCompatActivity {
    private Button button_2;


    private static final String APP_ID = "1108179346";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ////////////////////////////////////////////
        button=(ImageButton) findViewById(R.id.ib_QQ);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,FirstActivity.this.getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(FirstActivity.this,"all", mIUiListener);
                Log.d("ttttt","hhhhhhh1");
            }
        });
        ////////////////////////////////////////////////////

        /*
        * 登录页面的注册按钮，点击后，跳转到注册页面
        * */
        button_2=(Button)findViewById(R.id.sign_in);
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,Create_user.class);
                startActivity(intent);
            }
        });

       final View view = getWindow().getDecorView();
        final Context context=getApplicationContext();

        //创建LitePal_general数据库
        LitePal.getDatabase();
        Sign_in_general.sign_in(view,context);

    }



    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(FirstActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.d("ttttt", "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                final String openID = obj.getString("openid");
                final String accessToken = obj.getString("access_token");
                final String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(FirstActivity.this.getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.d("ttttt","登录成功"+response.toString());

                        JSONObject jsonObject=(JSONObject)response;
                        initOpenidAndToken(jsonObject);
                        getUserInfo();

                        Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                        startActivity(intent);
                    }


                    ////////////////////////////////////////////////////////////////////////////////
                    public void initOpenidAndToken(JSONObject jsonObject) {
                        try {
                            String openid = jsonObject.getString("openid");
                            String token = jsonObject.getString("access_token");
                            String expires = jsonObject.getString("expires_in");

                            mTencent.setAccessToken(token, expires);
                            mTencent.setOpenId(openid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    private Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            ////获取昵称
                            if (msg.what == 0) {
                                Log.d("ttttt", "获取昵称--->" + (CharSequence) msg.obj);

                            }
                        }
                    };


                    public void getUserInfo() {

                        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
                        QQToken mQQToken = mTencent.getQQToken();
                        UserInfo userInfo = new UserInfo(FirstActivity.this, mQQToken);
                        userInfo.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(final Object o) {
                                JSONObject userInfoJson = (JSONObject) o;

                                Log.d("ttttt", "userInfoJson-->" + userInfoJson.toString());
                                try {
                                    String nickname = userInfoJson.getString("nickname");//直接传递一个昵称的内容过去
                                    mHandler.obtainMessage(0, nickname).sendToTarget();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            @Override
                            public void onError(UiError uiError) {
                                Log.d("ttttt", "获取qq用户信息错误");
                                Toast.makeText(FirstActivity.this, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.d("ttttt", "获取qq用户信息取消");
                                Toast.makeText(FirstActivity.this, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                    @Override
                    public void onError(UiError uiError) {
                        Log.d("ttttt","登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("ttttt","登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(FirstActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(FirstActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ttttt","hhhhhhh2");
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
