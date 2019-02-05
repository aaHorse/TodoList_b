package com.example.zexiger.todolist_b.floating_action_button;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.example.zexiger.todolist_b.R;

public class Fab_add {
    public static void add(View view, final Context context) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hhh", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
