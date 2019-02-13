package com.example.zexiger.todolist_b.QQ_register;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;
import com.example.zexiger.todolist_b.SQLite_User.Users;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class QQ_Fragment extends Fragment {
    private static final String TAG = "ttttt";
    private static final String APP_ID = "1108179346";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageButton button;

    private static int id=1;

    //private ImageView iv_head;//QQ头像

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register,container,false);
        Log.d("ttttt","hhhhhhhhh1");
        button=(ImageButton)view.findViewById(R.id.ib_QQ);
 //       iv_head=(ImageView)view.findViewById(R.id.iv_head);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ttttt","hhhhhhhhh2");
                mIUiListener = new BaseUiListener();
                Log.d("ttttt","hhhhhhhhh3");
                //all表示获取所有权限
                mTencent.login(getActivity(),"all", mIUiListener);
                Log.d("ttttt","hhhhhhhhh4");
            }
        });
        return view;
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        /**
         * 授权完成
         * */
        @Override
        public void onComplete(Object response) {
            Log.d("ttttt","hhhhhhhhh5");
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) response;
            try {
                final String openID = obj.getString("openid");
                final String accessToken = obj.getString("access_token");
                final String expires = obj.getString("expires_in");

                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getActivity().getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
//                    String name="null";
//                    String qq_id="000";
                    @Override
                    public void onComplete(Object response) {
                        JSONObject jsonObject=(JSONObject)response;
                        initOpenidAndToken(jsonObject);
                        getUserInfo();

/*                        ///////////////
                        Users user=new Users(getContext(),"users.db",null,1);
                        SQLiteDatabase db=user.getWritableDatabase();

                        ContentValues values=new ContentValues();
                        String user_id="QQ"+id;
                        values.put("user_id",user_id);
                        id++;
                        values.put("name",name);
                        values.put("password","00000000");
                        db.insert("User",null,values);

                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        intent.putExtra("id",user_id);
                        startActivity(intent);*/
                    }

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
                            /*
                            这里写得很混乱，
                            从QQ返回的JSON字符串中，获取了昵称，openid，很头像
                            将昵称（初始化为null），id（自己设置的QQ+static id），password（初始化为00000000）
                            其中昵称会展示在侧滑菜单栏，id用于content存储和查询，password不向用户展示，
                            主要是与general注册用户用同一个数据库，需要实现对齐

                            完成之后，跳转到mainActivity界面
                            * */

                            ////获取昵称
                            if (msg.what == 0) {
                                //name=(String)msg.obj;
                                //Log.e("hzq", "获取昵称--->" + (CharSequence) msg.obj);
                            } else if (msg.what == 1) {
                                //获取头像
                                //iv_head.setImageBitmap((Bitmap) msg.obj);
                            }else if(msg.what==2){
                               // qq_id=(String)msg.obj;
                            }
                        }
                    };


                    public void getUserInfo() {

                        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
                        QQToken mQQToken = mTencent.getQQToken();
                        UserInfo userInfo = new UserInfo(getActivity(), mQQToken);
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

                                //子线程 获取并传递头像图片，由Handler更新
                                new Thread(String.valueOf(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap bitmapHead = null;
                                        if (((JSONObject) o).has("figureurl")) {
                                            try {
                                                String headUrl = ((JSONObject) o).getString("figureurl_qq_2");
                                                bitmapHead = Utils.getbitmap(headUrl);
                                                mHandler.obtainMessage(1, bitmapHead).sendToTarget();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                })).start();

                                try{
                                    String id=userInfoJson.getString("openid");
                                    mHandler.obtainMessage(2,id).sendToTarget();
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }


                            @Override
                            public void onError(UiError uiError) {
                                Log.d("ttttt", "获取qq用户信息错误");
                                Toast.makeText(getActivity(), "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.d("ttttt", "获取qq用户信息取消");
                                Toast.makeText(getActivity(), "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                    @Override
                    public void onError(UiError uiError) {
                        Log.d(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 授权过程出错
         * */
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
            Log.d("ttttt","hhhhhhhhh5");
        }

        /**
         * 授权取消
         * */
        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();
            Log.d("ttttt","hhhhhhhhh5");
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
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
