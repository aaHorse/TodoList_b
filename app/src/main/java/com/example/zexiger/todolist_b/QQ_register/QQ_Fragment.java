





/*
* 用碎片实现QQ第三方登录出现不能解决的问题
* 改用在FirstActivity中实现第三方登录
*
* */







/*
package com.example.zexiger.todolist_b.QQ_register;

import android.content.Intent;
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

import com.example.zexiger.todolist_b.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class QQ_Fragment extends Fragment {
    private static final String APP_ID = "1108179346";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageButton button;

    //private ImageView iv_head;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.register,container,false);
        button=(ImageButton) view.findViewById(R.id.ib_QQ);
 //       iv_head=(ImageView)view.findViewById(R.id.iv_head);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(getActivity(),"all", mIUiListener);
                Log.d("ttttt","hhhhhhh1");
            }
        });
        return view;
    }

    */
/**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     *//*

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.d("ttttt", "response:" + response);
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
                    @Override
                    public void onComplete(Object response) {
                        Log.d("ttttt","登录成功"+response.toString());

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
                                Log.d("ttttt", "获取昵称--->" + (CharSequence) msg.obj);

                            } else if (msg.what == 1) {
                                //获取头像
                                Log.d("ttttt", "获取头像--->" + (Bitmap) msg.obj);

                                //iv_head.setImageBitmap((Bitmap) msg.obj);
                            } else if (msg.what == 2) {
                                //获取省份
                                Log.d("ttttt", "获取省份--->" + msg.obj);
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

                                Log.d("ttttt", "userInfoJson-->" + userInfoJson.toString());
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

                                try {
                                    Object province = userInfoJson.get("province");
                                    mHandler.obtainMessage(2, province).sendToTarget();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //                handler.obtainMessage(what, getLinkstr(result))
                                //                        .sendToTarget();
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
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    */
/**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     *//*

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ttttt","hhhhhhh2");
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
*/
