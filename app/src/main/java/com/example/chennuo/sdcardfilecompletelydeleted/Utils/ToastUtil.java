package com.example.chennuo.sdcardfilecompletelydeleted.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chennuo on 2016/5/24.
 */
public class ToastUtil {

	/*public static void toastDataError(Context mContext) {
		//toast(mContext, mContext.getString(R.string.data_error));
	}

	public static void toastNetError(Context mContext) {
		toast(mContext, mContext.getString(R.string.net_error));
	}*/


    private Toast mToast;
    private Context mContext;

    public ToastUtil(Context context){
        mContext = context;
    }

    public void toast_short(String text) {
        if (mToast == null){
            mToast = Toast.makeText(mContext,text,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void toast_long(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext,text,Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void cancelToast(){
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void toast(Context mContext , String text) {
        Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
    }
}
