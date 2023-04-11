package com.rovaindu.homeservice.manager.chat_DB;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mohamed El Sayed
 */
public class DB_Utils {
    private Context context;
    public DB_Utils(Context context) {
        this.context = context;
    }
    public  void copyDatabase( String DATABASE_NAME) {
        String databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        if (f.exists()) {
            try {
                File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "FutureAcademy"
                        + File.separator + "Databases");
                System.out.println("directory.exists(): " + directory.exists());
                if (!directory.exists())
                    directory.mkdir();
                myOutput = new FileOutputStream(directory.getAbsolutePath() + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

}
