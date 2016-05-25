package com.example.chennuo.sdcardfilecompletelydeleted.File;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by chennuo on 2016/5/25.
 */
public class FileOperation {

    private String filePath;
    private String fileName;
    private Context mContext;
    private File file;

    public FileOperation(Context context, String filepath, String filename) {
        this.filePath = filepath;
        this.mContext = context;
        this.fileName = filename;
    }

    /**
     * 普通删除文件
     *
     * @return
     */
    public boolean deleteFile() {
        file = new File(filePath);
        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @return
     */
    public boolean createFile() {
        int index = filePath.lastIndexOf("/");
        String FilePath = filePath.substring(0, index + 1);
        file = new File(FilePath, fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 写数据
     */
    public boolean writeFile(int length) {
        byte[] write_bytes = new byte[length];
        if (length ==0 ){
            return true;
        }
        for (int i = 0; i < length; i++) {
            write_bytes[i] = '0';
        }
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(write_bytes);
            fout.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读数据
     */
    public int readFileLength() {
        int length = 0;
        try {
            FileInputStream fin = new FileInputStream(filePath);
            length = fin.available();
            Log.d("TAG", String.valueOf(length));
//            byte[] buffer = new byte[length];
//            fin.read(buffer);
            fin.close();
            return length;
        } catch (FileNotFoundException e) {
            Log.d("TAG", String.valueOf(length));
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            Log.d("TAG", String.valueOf(length));
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 清空数据
     */
    public boolean cleanFile() {
        try {
            FileWriter fwrite = new FileWriter(filePath);
            fwrite.write("");
            fwrite.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
