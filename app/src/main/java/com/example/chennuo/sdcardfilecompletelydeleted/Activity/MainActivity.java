package com.example.chennuo.sdcardfilecompletelydeleted.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.chennuo.sdcardfilecompletelydeleted.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnDeleteSDFile;
    private Button btnDeleteSDFileNormal;

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

        btnDeleteSDFileNormal = (Button) findViewById(R.id.btnDeleteSDFileNormal);
        btnDeleteSDFileNormal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeleteSDFile:
                //彻底删除SD文件
                Intent intent = new Intent(MainActivity.this,DeleteSDFileActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDeleteSDFileNormal:
                //一般删除SD文件
                Intent intent_normal = new Intent(MainActivity.this,DeleteSDFileNormalActivity.class);
                startActivity(intent_normal);
                break;
            default:
                break;
        }
    }
}
