package cn.edu.gdmec.android.mobileguard.m5virusscan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by asus on 2017/11/16.
 */

public class MD5Utils {
    /*获取文件的md5的值
    path 文件的路径
    null 文件不存在
    * */
    public static String getFilesMd5(String path) {

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = fis.read(buffer))!= -1){
                digest.update(buffer,0,len);
            }
            byte [] result = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b:result){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length()==1){
                    sb.append("0"+hex);

                }else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
