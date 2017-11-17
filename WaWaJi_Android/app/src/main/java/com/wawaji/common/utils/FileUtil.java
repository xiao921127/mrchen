package com.wawaji.common.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * 文本处理工具类
 *
 * @version V1.0 <文本处理>
 * @since  2016/08/02 10:37
 * @author admin
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess", "unused"})
public class FileUtil {

    private static final long B = 1;
    private static final long KB = B * 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;

    /**
     * 保留两位小数
     * @param d 小数位
     * @return str
     */
    private static String twoDot(double d) {
        return String.format(Locale.getDefault(), "%.2f", d);

    }

    /**
     * 得到文件转换后的大小
     *
     * @param size 文件大小
     * @param u 单位
     * @return doublie
     */
    private static double getSize(long size, long u) {
        return (double) size / (double) u;
    }

    /**
     * 递归创建文件目录
     *
     * @param path 路径
     */
    public static void createDir(String path) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)){
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * 格式化文件大小带有单位
     *
     * @param size 文件大小
     * @return 文件大小
     */
    public static String formatFileSize(long size) {
        String u;
        StringBuilder sb = new StringBuilder();
        double tmpSize;
        if (size < KB) {
            sb.append(size).append("B");
            return sb.toString();
        } else if (size < MB) {
            u = "KB";
            tmpSize = getSize(size, KB);
        } else if (size < GB) {
            u = "MB";
            tmpSize = getSize(size, MB);
        } else {
            u = "GB";
            tmpSize = getSize(size, GB);
        }
        return sb.append(twoDot(tmpSize)).append(u).toString();
    }

    /**
     * 得到一个文件大小
     *
     * @param file 文件
     * @return long
     */
    private static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 得到所有文件大小
     *
     * @param f 文件
     * @return long
     */
    public static long getAllFileSizes(File f) {
        long size = 0;
        File flist[] = f.listFiles(); // 获取全部文件
        if(null == flist){
            return size;
        }
        // 遍历所有文件
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                size = size + getAllFileSizes(aFlist);
            } else {
                size = size + getFileSize(aFlist);
            }
        }
        return size;
    }

    /**
     * 删除文件
     *
     * @param file 文件
     */
    public static void delete(File file) {
        File flist[] = file.listFiles();
        // 遍历所有文件
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                delete(aFlist);
            } else {
                aFlist.delete();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param file 文件
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[]
                for (File file1 : files) { // 遍历目录下所有的文件
                    deleteFile(file1); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param path 路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思
            }
        }
    }

    /**
     * 删除缓存历史文件
     *
     * @param cacheFile 缓存文件
     */
    public static void clearCache(File cacheFile) {
        if(!cacheFile.exists()){
            return;
        }
        if (cacheFile.isFile()) {
            cacheFile.delete();
        } else if (cacheFile.isDirectory()) {
            File[] childFiles = cacheFile.listFiles();
            if(null == childFiles){
                return;
            }
            for (File childFile : childFiles) {
                clearCache(childFile);
            }
        }
    }

}