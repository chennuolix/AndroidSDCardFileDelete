package com.example.chennuo.sdcardfilecompletelydeleted.File;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
     * @return
     */
    public boolean createFile() {
        int index = filePath.lastIndexOf("/");
        String FilePath = filePath.substring(0,index+1);
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
        }else {
            return false;
        }
    }


}
