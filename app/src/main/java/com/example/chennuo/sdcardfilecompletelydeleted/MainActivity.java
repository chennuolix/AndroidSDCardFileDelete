package com.example.chennuo.sdcardfilecompletelydeleted;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnDeleteSDFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        btnDeleteSDFile = (Button) findViewById(R.id.btnDeleteSDFile);
        btnDeleteSDFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeleteSDFile:
                //彻底删除SD文件
                Intent intent = new Intent(MainActivity.this,DeleteSDFileActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
