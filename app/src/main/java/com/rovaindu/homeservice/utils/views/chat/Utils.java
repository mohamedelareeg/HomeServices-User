package com.rovaindu.homeservice.utils.views.chat;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.utils.helpers.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.ranges.RangesKt;

public class Utils {

    private static final String TAG = "Utils";
    public static float dpToPx(Context context, float valueInDp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = valueInDp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    public static float dpToPixel(float dp, Resources resources) {
        float density = resources.getDisplayMetrics().density;
        float pixel = dp * density;
        return pixel;
    }
    public static boolean isDarkMode(Context context)
    {
        int nightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightMode== Configuration.UI_MODE_NIGHT_YES)
            return true;
        else
            return false;
    }
    public static final float softTransition(float $this$softTransition, float compareWith, float allowedDiff, float scaleFactor) {
        if (scaleFactor == 0.0F) {
            return $this$softTransition;
        } else {
            float result = $this$softTransition;
            float diff;
            if (compareWith > $this$softTransition) {
                if (compareWith / $this$softTransition > allowedDiff) {
                    diff = RangesKt.coerceAtLeast($this$softTransition, compareWith) - RangesKt.coerceAtMost($this$softTransition, compareWith);
                    result = $this$softTransition + diff / scaleFactor;
                }
            } else if ($this$softTransition > compareWith && $this$softTransition / compareWith > allowedDiff) {
                diff = RangesKt.coerceAtLeast($this$softTransition, compareWith) - RangesKt.coerceAtMost($this$softTransition, compareWith);
                result = $this$softTransition - diff / scaleFactor;
            }

            return result;
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null
                && permissions != null) {
            for (String permission : permissions) {
                Logger.error(TAG, " hasPermissions() : Permission : " + permission
                        + "checkSelfPermission : " + ActivityCompat.checkSelfPermission(context, permission));
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private static String getImagePath(Uri aUri, String aSelection, Context context) {
        try {
            String path = null;
            Cursor cursor = context.getContentResolver().query(aUri, null, aSelection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String cFolders(Context context) {
        File folder = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/Media"
                + File.separator + "Records"
        );
        String  mFileName = context.getExternalFilesDir(null).getAbsolutePath()
                + "/Media"
                +"/Records";
        if(!folder.exists()){
            folder.mkdirs();
        }
        return mFileName + "/" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".3gp";

    }
    public static String getOutputMediaFile(Context context) {
        File var0 = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
        if (!var0.exists() && !var0.mkdirs()) {
            return null;
        } else {
            String var1 = Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.app_name) + "/"
                    + "audio/";
            createDirectory(var1);
            return var1 + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".3gp";
        }
    }
    public static void createDirectory(String var0) {
        if (!(new File(var0)).exists()) {
            (new File(var0)).mkdirs();
        }

    }

    public static File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Log.w(TAG, e);
            return null;
        }

        return file;
    }

    public static File getDocumentCacheDir(@NonNull Context context) {
        File dir = new File(context.getCacheDir(), "documents");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public static String getFileName(String mediaFile) {
        String t1[] = mediaFile.substring(mediaFile.lastIndexOf("/")).split("_");
        return t1[2];
    }

    public static String getFileName(@NonNull Context context, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        String filename = null;

        if (mimeType == null && context != null) {
            String path = getPath(context, uri);
            if (path == null) {
                filename = getName(uri.toString());
            } else {
                File file = new File(path);
                filename = file.getName();
            }
        } else {
            Cursor returnCursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                filename = returnCursor.getString(nameIndex);
                returnCursor.close();
            }
        }

        return filename;
    }
    public static String getPath(final Context context, final Uri uri) {
        String absolutePath = getImagePathFromUri(context, uri);
        return absolutePath != null ? absolutePath : uri.toString();
    }

    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }

    public static String getImagePathFromUri(Context context, @Nullable Uri aUri) {
        String imagePath = null;
        if (aUri == null) {
            return imagePath;
        }
        if (DocumentsContract.isDocumentUri(context, aUri)) {
            String documentId = DocumentsContract.getDocumentId(aUri);
            if ("com.android.providers.media.documents".equals(aUri.getAuthority())) {
                final String id = DocumentsContract.getDocumentId(aUri);

                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4);
                }

                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };

                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                            String path = getDataColumn(context, contentUri, null, null);
                        if (path != null) {
                            return path;
                        }
                    } catch (Exception e) {
                    }
                }

                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(context, aUri);
                File cacheDir = getDocumentCacheDir(context);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(context, aUri, destinationPath);
                }
                imagePath = destinationPath;
            } else if ("com.android.providers.downloads.documents".equals(aUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null, context);
            }
        } else if ("content".equalsIgnoreCase(aUri.getScheme())) {
            imagePath = getImagePath(aUri, null, context);
        } else if ("file".equalsIgnoreCase(aUri.getScheme())) {
            imagePath = aUri.getPath();
        }
        return imagePath;
    }

    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Files.FileColumns.DATA;
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getFileSize(int fileSize) {
        if (fileSize > 1024) {
            if (fileSize > (1024 * 1024)) {
                return fileSize / (1024 * 1024) + " MB";
            } else {
                return fileSize / 1024 + " KB";
            }
        } else {
            return fileSize + " B";
        }
    }

    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * 0.6f);
        int height = Math.round(image.getHeight() * 0.6f);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        intrinsicBlur.setRadius(15f);
        intrinsicBlur.setInput(tmpIn);
        intrinsicBlur.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
    public static String getHeaderDate(long timestamp) {
        Calendar messageTimestamp = Calendar.getInstance();
        messageTimestamp.setTimeInMillis(timestamp);
        Calendar now = Calendar.getInstance();
//        if (now.get(5) == messageTimestamp.get(5)) {
        return DateFormat.format("hh:mm a", messageTimestamp).toString();
//        } else {
//            return now.get(5) - messageTimestamp.get(5) == 1 ? "Yesterday " + DateFormat.format("hh:mm a", messageTimestamp).toString() : DateFormat.format("d MMMM", messageTimestamp).toString() + " " + DateFormat.format("hh:mm a", messageTimestamp).toString();
//        }
    }

    public static String removeEmojiAndSymbol(String content) {
        String utf8tweet = "";
        try {
            byte[] utf8Bytes = content.getBytes("UTF-8");
            utf8tweet = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Pattern unicodeOutliers = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE |
                        Pattern.CASE_INSENSITIVE);
        Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
        utf8tweet = unicodeOutlierMatcher.replaceAll(" ");
        return utf8tweet;
    }

    public static String getDateId(long var0) {
        Calendar var2 = Calendar.getInstance(Locale.ENGLISH);
        var2.setTimeInMillis(var0);
        return DateFormat.format("ddMMyyyy", var2).toString();
    }

    public static String getDate(long var0) {
        Calendar var2 = Calendar.getInstance(Locale.ENGLISH);
        var2.setTimeInMillis(var0);
        return DateFormat.format("dd/MM/yyyy", var2).toString();
    }
    public static boolean isEmpty(String value) {
        if (value == null) {
            return false;
        } else {
            return value.equalsIgnoreCase("") || value.length() == 0;
        }
    }
    public static String getLastMessageDate(long timestamp) {
        String lastMessageTime = new SimpleDateFormat("h:mm a").format(new java.util.Date(timestamp * 1000));
        String lastMessageDate = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(timestamp * 1000));
        String lastMessageWeek = new SimpleDateFormat("EEE").format(new java.util.Date(timestamp * 1000));
        long currentTimeStamp = System.currentTimeMillis();

        long diffTimeStamp = currentTimeStamp - timestamp * 1000;

        Log.e(TAG, "getLastMessageDate: " + 24 * 60 * 60 * 1000);
        if (diffTimeStamp < 24 * 60 * 60 * 1000) {
            return lastMessageTime;

        } else if (diffTimeStamp < 48 * 60 * 60 * 1000) {

            return "Yesterday";
        } else if (diffTimeStamp < 7 * 24 * 60 * 60 * 1000) {
            return lastMessageWeek;
        } else {
            return lastMessageDate;
        }

    }
}
