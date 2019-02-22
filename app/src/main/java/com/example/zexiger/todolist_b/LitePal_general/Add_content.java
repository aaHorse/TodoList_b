package com.example.zexiger.todolist_b.LitePal_general;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.classichu.lineseditview.LinesEditView;
import com.example.zexiger.todolist_b.MainActivity;
import com.example.zexiger.todolist_b.R;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Add_content extends AppCompatActivity {
    private String id;
    /*
    * 使用第三方控件
    * */
    private LinesEditView editText;

    private Button button;//界面的添加按钮
    private Button button_2;//界面的返回按钮
    private ImageButton button_3;//界面的垃圾桶按钮
    private ImageButton button_4;//界面的勾按钮
    private RatingBar mRatingBar;//显示星星
    private int level=1;//Item的优先级，默认为1级

    private List<Contents> list;//用于数据库获取，date的context

    private Boolean flag=false;//用于区别，这个页面是谁启动的，如果是点击Item启动的，为true；
    //如果是点击加号启动的，为false；
    private String date_id="00000";//如果是点击Item启动的，我们储存item对应的唯一id,在编辑完之后，在数据库中将其删除，再增加

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);

        editText=(LinesEditView)findViewById(R.id.et_add);
        button=(Button)findViewById(R.id.bt_add);
        button_2=(Button)findViewById(R.id.back) ;
        button_3=(ImageButton)findViewById(R.id.delete_input);
        button_4=(ImageButton)findViewById(R.id.input_done);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);

        editText.setContentTextSize(80);
        editText.setContentTextColor(Color.parseColor("#000000"));

        Intent intent=getIntent();
        if(intent.getIntExtra("flag",-1)==0){
            date_id=intent.getStringExtra("context_id");
            //开数据库，获取对应位置的content
            Contents item=getItem(date_id);
            editText.setContentText(item.getContent_text());
            mRatingBar.setRating(item.getLevel());
            flag=true;
        }else{
            editText.setHintText("input here ...");
            flag=false;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成编辑之后，跳回主界面
                Intent intent_2=new Intent();
                setResult(RESULT_OK,intent_2);
                finish();//按了返回键，不保存数据，直接销毁当前的界面
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setContentText("");
            }
        });
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                level=(int)rating;
               switch (level){
                   case 0:
                       try {
                           Thread.currentThread().sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       Toast.makeText(Add_content.this,"默认优先级为1级",Toast.LENGTH_SHORT).show();
                       ratingBar.setRating(1.0f);
                       break;
                   case 1:
                       Toast.makeText(Add_content.this,1+"",Toast.LENGTH_SHORT).show();
                       ratingBar.setRating(1.0f);
                       break;
                   case 2:
                       Toast.makeText(Add_content.this,2+"",Toast.LENGTH_SHORT).show();
                       ratingBar.setRating(2.0f);
                       break;
                   case 3:
                       Toast.makeText(Add_content.this,3+"",Toast.LENGTH_SHORT).show();
                       ratingBar.setRating(3.0f);
                       break;
                   default:
                       Log.d("ttttt","星星代码出问题了");
               }
            }
        });


    }

    private void save(){
        Intent intent=getIntent();
        id=intent.getStringExtra("id");

        String content_text=editText.getContentText().toString();

        Contents user=new Contents();

        user.setId_string(id);
        user.setContent_text(content_text);
        user.setDate(getStringDate());
        user.setLevel(level);
        user.setDone(false);
        user.setChecked(false);

        user.save();

        //如果是点击item增加的，相当于更新，把它删掉
        if(flag){
            DataSupport.deleteAll(Contents.class,"date = ?",date_id);
            MainActivity.mainActivity_refresh();
            finish();
        }

        //完成编辑之后，跳回主界面
        Intent intent_2=new Intent();
        intent_2.putExtra("user",user);
        setResult(RESULT_OK,intent_2);
        finish();
    }

    private Contents getItem(String date){
        Contents item;
        list=DataSupport.where("date = ?",date).find(Contents.class);
        if(list.size()!=0){
            item=list.get(0);
            return item;
        }else{
            Log.d("ttttt","获取对象失败");
            return null;
        }
    }

    public String getStringDate(){
        Date now=new Date();
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String str=sdfd.format(now);
        Log.d("ttttt",str);
        return str;
    }
}
