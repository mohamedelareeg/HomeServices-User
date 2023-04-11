package com.rovaindu.homeservice.utils;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rovaindu.homeservice.interfaces.WorkFinish;

import java.util.ArrayList;
import java.util.List;


public abstract class PermUtil {

    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 9921;
/*
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkForPermissions(final FragmentActivity activity) {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, activity))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, activity))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION, activity))
            permissionsNeeded.add("ACCESS_FINE_LOCATION");
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_COARSE_LOCATION, activity))
            permissionsNeeded.add("ACCESS_COARSE_LOCATION");
        if (permissionsList.size() > 0) {
            activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean addPermission(List<String> permissionsList, String permission, Activity ac) {
        if (ac.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            return ac.shouldShowRequestPermissionRationale(permission);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkForCamara_WritePermissions(final FragmentActivity activity, WorkFinish workFinish) {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, activity))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO, activity))
            permissionsNeeded.add("RECORD_AUDIO");
        if (!addPermission(permissionsList, "android.permission.FLASHLIGHT", activity))
            permissionsNeeded.add("FLASHLIGHT");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE, activity))
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (permissionsList.size() > 0) {
            activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
            workFinish.onWorkFinish(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkForCamara_WritePermissions(final Fragment fragment, WorkFinish workFinish) {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO, fragment.getActivity()))
            permissionsNeeded.add("RECORD_AUDIO");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, fragment.getActivity()))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, "android.permission.FLASHLIGHT", fragment.getActivity()))
            permissionsNeeded.add("FLASHLIGHT");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, fragment.getActivity()))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE, fragment.getActivity()))
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        if (permissionsList.size() > 0) {
            fragment.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
            workFinish.onWorkFinish(true);
        }
    }

}
