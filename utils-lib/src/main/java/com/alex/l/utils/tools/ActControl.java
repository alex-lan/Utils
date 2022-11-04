package com.alex.l.utils.tools;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
  -
  * Created by [Alex Y. Lan] on [2022-05-26]
  * Power by AndroidStudio™ IDE
 */

/**
 * * in base Activity -> 'onResume()' add 'ActControl.onResume(this)'
 * * in base Activity -> 'onDestroy()' add 'ActControl.onDestroy(this)'
 * * use 'ActControl.appExit()' where you want exit the app Completely
 */
public class ActControl {

    private static final String TAG = "ActControl";

    private final List<SoftReference<Activity>> list;

    private ActControl() {
        this.list = new ArrayList<>();
    }

    private void resume(Activity activity) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            SoftReference<Activity> softReference = list.get(i);
            Activity act = softReference.get();
            if (act == null) {
                list.remove(softReference);
                i--;
                size--;
            } else if (act == activity) {
                if (i != size - 1) {
                    Collections.swap(list, i, size - 1);
                }
                return;
            }
        }
        list.add(new SoftReference<>(activity));
    }


    private void destroy(Activity activity) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            SoftReference<Activity> softReference = list.get(i);
            Activity act = softReference.get();
            if (act != null && act == activity) {
                list.remove(softReference);
                act.finish();
                return;
            }
        }
    }

    private Activity top() {
        int index = list.size() - 1;
        while (index >= 0) {
            SoftReference<Activity> softReference = list.get(index);
            if (softReference.get() != null) {
                return softReference.get();
            } else {
                index--;
            }
        }
        Log.e(TAG, "error,top is null");
        return null;
    }

    private List<SoftReference<Activity>> getList() {
        return list;
    }

    private static ActControl CONTROL = new ActControl();

    public static void onResume(Activity activity) {
        CONTROL.resume(activity);
        Log.d(TAG, "onResume size = " + CONTROL.getList().size());
    }

    public static void onDestroy(Activity activity) {
        CONTROL.destroy(activity);
        Log.d(TAG, "onDestroy size = " + CONTROL.getList().size());
    }

    public static Activity findExist(Class<?> cls) {
        return CONTROL.findExistInner(cls);
    }

    public static Activity getTop() {
        return CONTROL.top();
    }

    public Activity findExistInner(Class<?> cls) {
        for (SoftReference<Activity> ref : list) {
            final Activity activity = ref.get();
            if (activity != null && activity.getClass() == cls)
                return activity;
        }
        return null;
    }

    /**
     * 结束所有activity
     */
    private static void finishAllActivity() {
        try {
            for (int i = 0; i < CONTROL.getList().size(); i++) {
                if (null != CONTROL.getList().get(i)) {
                    CONTROL.getList().get(i).get().finish();
                }
            }
            CONTROL.getList().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归的finish 并退出应用程序
     */
    public static void appExit() {
        appExit(0);
    }

    /**
     * 0 正常退出 finish All Activity<br/>
     * 2 闪退
     * @param code
     */
    public static void appExit(int code) {
        try {
            finishAllActivity();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            if (code == 2) {
                System.exit(0);
                //从操作系统中结束掉当前程序的进程
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}