package com.example.chennuo.sdcardfilecompletelydeleted;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chennuo.sdcardfilecompletelydeleted.File.FileOperation;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.CallbackBundle;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.OpenFileDialog;
import com.example.chennuo.sdcardfilecompletelydeleted.Utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chennuo on 2016/5/24.
 */
public class DeleteSDFileActivity extends Activity implements View.OnClickListener {

    private ImageButton btnBack;
    private Button btnChoseFile;
    private Button btnStartDelete;
    private EditText etSdFilePath;

    private static int openfileDialogId = 0;

    private String filePath;
    private String fileName;

    private Dialog dialog;
    private ToastUtil toastUtil;
    private ProgressDialog.Builder progressDialog;

    private FileOperation fileOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_deletesdfile);
        init();
        initView();
    }


    /**
     * 初始化
     */
    private void init() {
        toastUtil = new ToastUtil(DeleteSDFileActivity.this);
        progressDialog = new ProgressDialog.Builder(DeleteSDFileActivity.this);
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
                DeleteSDFileActivity.this.finish();
                break;
            case R.id.btnChoseFile:
                dialog = onCreateDialog(openfileDialogId);
                showDialog(onCreateDialog(openfileDialogId));
                break;
            case R.id.btnStartDelete:
                if (checkInSDCard(filePath)) {
//                    progressDialog.setTitle("提示");
//                    progressDialog.setCancelable(false);
//                    progressDialog.setMessage("正在粉碎，请稍后...");
//                    progressDialog.show();
                    toastUtil.toast_short("正在粉碎中");
                    fileOperation = new FileOperation(DeleteSDFileActivity.this, filePath, fileName);
                    for (int i = 0; i < 2; i++) {

                    }
                    if (fileOperation.deleteFile()) {
                        toastUtil.toast_short("删除成功");
                        if (fileOperation.createFile()) {
                            toastUtil.toast_short("粉碎完成");
                        } else {
                            toastUtil.toast_short("粉碎失败");
                        }
                    } else {
                        toastUtil.toast_short("删除失败");
                    }
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

}