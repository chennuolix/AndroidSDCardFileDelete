package com.example.chennuo.sdcardfilecompletelydeleted.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chennuo.sdcardfilecompletelydeleted.File.FileOperation;
import com.example.chennuo.sdcardfilecompletelydeleted.R;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.CallbackBundle;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.OpenFileDialog;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chennuo on 2016/5/25.
 */
public class DeleteSDFileNormalActivity extends Activity implements View.OnClickListener {
    private ImageButton btnBack;
    private Button btnChoseFile;
    private Button btnStartDelete;
    private EditText etSdFilePath;

    private static int openfileDialogId = 0;

    private String filePath;
    private String fileName;
    private int fileLength = 0;

    private Dialog dialog;
    private ToastUtil toastUtil;
    private ProgressDialog.Builder progressDialog;

    private FileOperation fileOperation;

    private ReceiverBroadcast receiverBroadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_deletesdfilenormal);
        init();
        initView();
    }


    /**
     * 初始化
     */
    private void init() {
        toastUtil = new ToastUtil(DeleteSDFileNormalActivity.this);
        progressDialog = new ProgressDialog.Builder(DeleteSDFileNormalActivity.this);
        receiverBroadcast = new ReceiverBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.SDCardFileCompletelyDelete.DeleteSdFileNormalActivity");
        registerReceiver(receiverBroadcast, filter);
    }


    /**
     * 初始化界面
     */
    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnChoseFile = (Button) findViewById(R.id.btnChoseFile);
        btnStartDelete = (Button) findViewById(R.id.btnStartDelete);
        etSdFilePath = (EditText) findViewById(R.id.etSdFilePath);

        btnBack.setOnClickListener(this);
        btnChoseFile.setOnClickListener(this);
        btnStartDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                DeleteSDFileNormalActivity.this.finish();
                break;
            case R.id.btnChoseFile:
                dialog = onCreateDialog(openfileDialogId);
                showDialog(dialog);
                break;
            case R.id.btnStartDelete:
                if (filePath != null && !filePath.equals("")) {
                    if (checkInSDCard(filePath)) {
                        toastUtil.toast_short("正在删除中");
                        fileOperation = new FileOperation(DeleteSDFileNormalActivity.this, filePath, fileName);
                        if (fileOperation.deleteFile()){
                            Log.d("TAG","删除成功");
                            toastUtil.toast_short("删除成功");
                            etSdFilePath.setText("");
                        } else {
                            Log.d("TAG","删除失败");
                            toastUtil.toast_short("删除失败");
                        }
                    }
                } else {
                    toastUtil.toast_short("请选择文件");
                }
                break;
            default:
                break;

        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == openfileDialogId) {
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标
            images.put("wav", R.drawable.filedialog_wavfile);   //wav文件图标
            images.put("file", R.drawable.filedialog_file);      //其他文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            String filename = bundle.getString("name");
                            setTitle(filepath); // 把文件路径显示在标题上
                            etSdFilePath.setText(filepath);
                            filePath = filepath;
                            fileName = filename;
                            Intent intent_broadcast = new Intent();
                            intent_broadcast.setAction("com.example.SDCardFileCompletelyDelete.DeleteSdFileNormalActivity");
                            sendBroadcast(intent_broadcast);
                        }
                    },
                    ".wav;",
                    images);
            return dialog;
        }
        return null;
    }

    /**
     * 显示Dialog
     *
     * @param dialog
     */
    private void showDialog(final Dialog dialog) {
        dialog.show();

    }

    /**
     * 判断文件是否是SDcard上的
     */
    private boolean checkInSDCard(String filepath) {
        boolean isIn;
        String str1 = filepath.substring(0, 7);
        String str2 = filepath.substring(0, 8);
        if (str1.equals("/sdcard") || str2.equals("/storage")) {
            Log.d("TAG", "isIn is true");
            isIn = true;
        } else {
            Log.d("TAG", "isIn is false");
            isIn = false;
            toastUtil.toast_short("请选择SD上的文件！");
        }
        return isIn;
    }

    class ReceiverBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            dialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiverBroadcast);
        super.onDestroy();
    }
}
