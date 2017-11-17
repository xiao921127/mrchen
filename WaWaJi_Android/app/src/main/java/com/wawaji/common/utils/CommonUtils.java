package com.wawaji.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.wawaji.app.AppApplication;
import com.wawaji.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 通用工具类
 *
 * @author admin
 * @version V1.0 <通用工具类>
 * @since 2016/6/18
 */
@SuppressWarnings("unused")
public class CommonUtils {

    /**
     * 计算出该TextView中文字的长度(像素)
     *
     * @param textView 控件
     * @param text     文本
     * @return float
     */
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        return paint.measureText(text);
    }

    /**
     * 显示或隐藏输入法键盘
     *
     * @param hasFocus boolean值
     * @param v        控件
     */
    @SuppressWarnings("SameParameterValue")
    public static void onFocusChange(boolean hasFocus, final View v) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                try {
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (isFocus) {
//                        imm.toggleSoftInput(0,
//                                InputMethodManager.HIDE_NOT_ALWAYS);
                        imm.showSoftInput(v, 0);
                    } else {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    /**
     * 将时间戳转换成日期
     *
     * @param longtime 时间戳
     * @param pattern  时间格式
     * @return str
     */
    @SuppressLint("SimpleDateFormat")
    public static String LongTimetoString(Long longtime, String pattern) {
        if (null == longtime || longtime == 0)
            return "";
        return new SimpleDateFormat(pattern).format(new Date(
                longtime * 1000));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return str
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 替换、过滤特殊字符
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data 字符串
     * @return bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float scale = AppApplication.sResource.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        float scale = AppApplication.sResource.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断网络连接状态
     *
     * @return 返回数据
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) AppApplication.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo i : info) {
                if (i.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否获取权限
     *
     * @param permission 权限
     * @return boolean
     */
    public static boolean isPermissionAvailable(String permission) {
        return ContextCompat.checkSelfPermission(AppApplication.sContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断应用是否真正前台运行
     *
     * @param context 上下文
     * @return 是否正在前台
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return currentPackageName != null && currentPackageName.equals(AppApplication.sContext.getPackageName());
    }

    /**
     * 判断SD卡状态
     */
    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取头像缓存路径
     *
     * @return str
     */
    public static String getHeadCachePath() {
        File dir = new File(AppConfig.DEFAULT_IMAGE_PATH);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        return dir.getAbsolutePath() + File.separator;
    }

    /**
     * 判断应用是否安装
     *
     * @param packageName 应用包名
     * @return boolean
     */
    public static boolean isAppInstalled(String packageName) {
        PackageManager packageManager = AppApplication.sContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 启动APP
     *
     * @param packageName app包名
     * @return boo
     */
    public static boolean launchApp(String packageName) {
        if (CommonUtils.isAppInstalled(packageName)) {
            try {
                PackageManager manager = AppApplication.sContext.getPackageManager();
                AppApplication.sContext.startActivity(manager.getLaunchIntentForPackage(packageName));
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 本次查询的就是针对 相机里面的图片进行搜查,获得最近一排的一张照片,的路径
     */
    public static String getLastPhotoByPath(Context context) {
        Cursor myCursor;
        String pathLast = "";
        // Create a Cursor to obtain the file Path for the large image
        String[] largeFileProjection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        myCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                largeFileProjection, null, null, largeFileSort);
        if (null == myCursor) {
            return pathLast;
        }
        if (myCursor.getCount() < 1) {
            myCursor.close();
            return pathLast;
        }
        while (myCursor.moveToNext()) {
            String data = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            File f = new File(data);
            if (f.exists()) {//第一个图片文件，就是最近一次拍照的文件；
                pathLast = f.getPath();
                System.out.println("f.getPath() = " + pathLast);
                myCursor.close();
                return pathLast;
            }
        }
        myCursor.close();
        return pathLast;

    }

    /**
     * 本次查询的就是针对 相机里面的图片进行搜查,获得最近一排的一张照片,的路径
     */
    public static String[] getLast20PhotoByPath(Context context) {
        Cursor myCursor;
        String[] path = new String[20];
        // Create a Cursor to obtain the file Path for the large image
        String[] largeFileProjection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        myCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                largeFileProjection, null, null, largeFileSort);
        if (null == myCursor) {
            return path;
        }
        if (myCursor.getCount() < 1) {
            myCursor.close();
            return path;
        }
        int i = 0;
        while (myCursor.moveToNext() && i < 20) {
            String data = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            File f = new File(data);
            if (f.exists()) {//第一个图片文件，就是最近一次拍照的文件；
                path[i] = f.getPath();
            }
            i++;
        }
        myCursor.close();
        return path;

    }

    /**
     * 验证申请权限结果
     *
     * @param grantResults 权限申请结果数组
     * @return 权限申请是否通过
     */
    public static boolean checkGrantResults(int[] grantResults) {
        if (null != grantResults) {
            if (grantResults.length <= 0) {
                return false;
            } else {
                for (int i : grantResults) {
                    if (i == -1) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 淘口令去掉链接
     *
     * @param taokey 淘口令
     * @return 去掉链接后的淘口令
     */
    public static String taokeyStrippedUrl(String taokey) {
        // 匹配的条件选项为结束为空格(半角和全角)、换行符、字符串的结尾或者遇到其他格式的文本
        String regexp
                = "(((http|ftp|https|file)://)|((?<!((http|ftp|https|file)://))www\\.))"  // 以http...或www开头
                + ".*?"                                                                   // 中间为任意内容，惰性匹配
                + "(?=(&nbsp;|\\s|　|<br />|$|[<>]))";                                     // 结束条件
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(taokey);
        String newTaokey = "";
        while (matcher.find()) {
            newTaokey = matcher.group().substring(0, 3).equals("www") ? "http://" + matcher.group() : matcher.group();
        }
        return taokey.replace(newTaokey, "");
    }

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     * @author wangyongzheng
     */
    public static Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
        // int scale, int roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
                .floatValue();
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
                .floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 根据文件路径获取bitmap
     *
     * @param pathString 文件本地路径
     * @return bitmap对象
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * YUV420sp
     *
     * @param inputWidth
     * @param inputHeight
     * @param scaled
     * @return
     */
    public static byte[] getYUV420sp(int inputWidth, int inputHeight, Bitmap scaled) {

        int[] argb = new int[inputWidth * inputHeight];

        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);

        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        encodeYUV420SP(yuv, argb, inputWidth, inputHeight);

        scaled.recycle();

        return yuv;
    }

    /**
     * RGB转YUV420sp
     *
     * @param yuv420sp inputWidth * inputHeight * 3 / 2
     * @param argb     inputWidth * inputHeight
     * @param width
     * @param height
     */
    public static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        // 帧图片的像素大小
        final int frameSize = width * height;
        // ---YUV数据---
        int Y, U, V;
        // Y的index从0开始
        int yIndex = 0;
        // UV的index从frameSize开始
        int uvIndex = frameSize;

        // ---颜色数据---
        int a, R, G, B;
        //
        int argbIndex = 0;
        //

        // ---循环所有像素点，RGB转YUV---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // a is not used obviously
                a = (argb[argbIndex] & 0xff000000) >> 24;
                R = (argb[argbIndex] & 0xff0000) >> 16;
                G = (argb[argbIndex] & 0xff00) >> 8;
                B = (argb[argbIndex] & 0xff);
                //
                argbIndex++;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                //
                Y = Math.max(0, Math.min(Y, 255));
                U = Math.max(0, Math.min(U, 255));
                V = Math.max(0, Math.min(V, 255));

                // NV21 has a plane of Y and interleaved planes of VU each
                // sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U. Note the
                // sampling is every other
                // pixel AND every other scanline.
                // ---Y---
                yuv420sp[yIndex++] = (byte) Y;
                // ---UV---
                if ((j % 2 == 0) && (i % 2 == 0)) {
                    //
                    yuv420sp[uvIndex++] = (byte) V;
                    //
                    yuv420sp[uvIndex++] = (byte) U;
                }
            }
        }
    }

    /**
     * 获取淘口令中的商品名
     *
     * @param taokey        淘口令
     * @param pattern_array 匹配的正则表达式
     * @return 商品名
     */
    public static String getNamefromTaokey(String taokey, String pattern_array) {
        if (TextUtils.isEmpty(pattern_array)) {
            return taokey;
        }
        try {
            JSONArray array = new JSONArray(pattern_array);
            String str = "";
            for (int i = 0; i < pattern_array.length(); i++) {
                Pattern p = Pattern.compile(array.getString(i));
                Matcher m = p.matcher(taokey);
                while (m.find()) {
                    str = m.group(1);
                }
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
            }
            return taokey;
        } catch (JSONException e) {
            e.printStackTrace();
            return taokey;
        }
    }

    /**
     * 手机号码格式化
     *
     * @param phone
     * @return
     */
    public static String phoneFormat(String phone) {
        StringBuilder sb = new StringBuilder(phone);
//        int length = phone.length() / 4 + phone.length();
//
//        for (int i = 0; i < length; i++) {
//            if (i % 5 == 0) {
//                sb.insert(i, " ");
//            }
//        }
//        sb.deleteCharAt(0);
        sb.insert(3, " ");
        sb.insert(8, " ");
        return sb.toString();
    }

    /**
     * 获取正确的文件路径(适配7.0系统)
     *
     * @param file 文件
     * @return 文件路径
     */
    public static Uri getFileUri(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            //android升级到7.0后不允许出现以file://的形式调用隐式APP，需要用共享文件的形式：content:// URI
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            uri = AppApplication.sContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}