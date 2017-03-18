package com.example.jamesljk.project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ruby on 2016/12/5.
 */

public class NoteActivity extends AppCompatActivity {

    private userDB db = new userDB(NoteActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        final ImageButton save = (ImageButton) findViewById(R.id.save);
        final ImageButton clear = (ImageButton) findViewById(R.id.clear);
        final TextView content = (TextView) findViewById(R.id.memo_text);
        final TextView title = (TextView) findViewById(R.id.memo_id);
        final ImageButton save_picture = (ImageButton) findViewById(R.id.save_picture);

        save_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, Picture_Setting.class);
                intent.putExtra("TextWriter", content.getText().toString());
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setText("");
                title.setText("");
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = content.getText().toString();
                String t = title.getText().toString();
                if(str.isEmpty()){
                    Toast.makeText(NoteActivity.this,"您还没有输入内容",Toast.LENGTH_SHORT).show();
                }
                else{
                            try{
                                db.insertDB(t,str);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        startActivity(new Intent(NoteActivity.this,MainActivity.class));
                        finish();
                }
            }
        });

    }
}
