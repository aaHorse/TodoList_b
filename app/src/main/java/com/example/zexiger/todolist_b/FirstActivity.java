package com.example.zexiger.todolist_b;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zexiger.todolist_b.LitePal_general.Contents;
import com.example.zexiger.todolist_b.LitePal_general.Create_user;
import com.example.zexiger.todolist_b.LitePal_general.Sign_in_general;
import com.example.zexiger.todolist_b.SQLite_User.Query;
import com.example.zexiger.todolist_b.SQLite_User.Users;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/*
* 登录界面
* */
public class FirstActivity extends AppCompatActivity {
    private Button button_2;//注册按钮
    //登录按钮写到了另外一个类中     Sign_in_general.sign_in(view,context);

    private static final String APP_ID = "1108179346";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageButton button;//QQ按钮
    private CheckBox remember;//记住密码按钮

    /*
    * 用于给QQ登录的用户分配id，
    * 采用SharedPreferences文件进行存储，
    * 和general登录共用同一个SharedPreferences文件
    * id在这里为负数，在后面会被初始化为正数
    * */
    private int id=-100;
    private String user_name="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final View view = getWindow().getDecorView();
        final Context context=getApplicationContext();

        //QQ登录按钮
        button=(ImageButton) findViewById(R.id.ib_QQ);
        remember=(CheckBox)findViewById(R.id.cb_checkbox);

        /*
        * 第一次初始化存用于给用户分配id的SharedPreferences文件，
        * 将里面的id初始化为1
        * */
        SharedPreferences sharedPreferences=getSharedPreferences("id_file",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("flag",true)){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("id",1);
            editor.putBoolean("flag",false);
            editor.apply();
        }

        //如果之前按了记住密码，在这里将登录界面显示出来
        SharedPreferences  preferences=PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember= preferences.getBoolean("remember_password",false);
        if(isRemember){
            Sign_in_general.checkout_show(view,context);
        }

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
        //创建LitePal_general数据库,登录按钮
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
            //Log.d("ttttt", "response:" + response);
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
                        //Log.d("ttttt","登录成功"+response.toString());
                        JSONObject jsonObject=(JSONObject)response;
                        initOpenidAndToken(jsonObject);
                        getUserInfo();
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
                                user_name=(String) msg.obj;
                                Log.d("ttttt", "获取昵称--->" + (CharSequence)msg.obj);
                                Users user=new Users(FirstActivity.this,"users.db",null,1);
                                /* 如果该QQ以前在这里用过QQ登录，则不写数据库，
                                 * 如果是新用户，则进行SQLite数据库的写入
                                 * */
                                //使用name做检查，如果QQ出现重名，这个方法将引发错误，在这里忽略这个bug，
                                //仍然使用name做唯一性检查
                                init_id();
                                if(!Query.query_have(user_name,getWindow().getContext())){
                                    id_add();
                                    //新用户
                                    Log.d("ttttt","新用户");
                                    SQLiteDatabase db=user.getWritableDatabase();
                                    ContentValues values=new ContentValues();
                                    String user_id="QQ"+id;
                                    values.put("user_id",user_id);
                                    values.put("name",user_name);
                                    values.put("password","00000000");//与general注册用户对齐
                                    db.insert("User",null,values);

                                    //
                                    Query.print_all(getWindow().getContext());

                                    Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                                    intent.putExtra("id",user_id);
                                    startActivity(intent);
                                }else{
                                    Log.d("ttttt","二次登录");
                                    //二次使用QQ登录的用户，需要通过名字去获取对应的数据库user_id

                                    //
                                    Query.print_all(getWindow().getContext());

                                    Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                                    String user_id=Query.get_id(user_name,getWindow().getContext());
                                    intent.putExtra("id",user_id);
                                    startActivity(intent);
                                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ttttt","跑到下面来了！！！");
    }

    private void init_id(){
        SharedPreferences sharedPreferences=getSharedPreferences("id_file",MODE_PRIVATE);
        id=sharedPreferences.getInt("id",-100);
    }
    private void id_add(){
        SharedPreferences sharedPreferences=getSharedPreferences("id_file",MODE_PRIVATE);
        int id_2=sharedPreferences.getInt("id",-100);
        id_2++;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.putInt("id",id_2);
        editor.putBoolean("flag",false);
        editor.apply();
    }
}
