package com.example.zexiger.todolist_b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zexiger.todolist_b.floating_action_button.Fab_add;
import com.example.zexiger.todolist_b.recyclerview.ItemInit;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View cv = getWindow().getDecorView();
        final Context context=getApplicationContext();

        ItemInit.initItems(cv,context);
        Fab_add.add(cv,context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_1:
                Toast.makeText(this, "item_1", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
