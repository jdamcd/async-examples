package com.jdamcd.threads;

import android.graphics.Color;
import android.os.Looper;

import java.util.Random;

public final class Util {

    public static String getThreadInfo() {
        return "Name: " + Thread.currentThread().getName() + "\n"
                + (isMainThread() ? "Main thread" : "Background thread") + "\n"
                + "ID: " + Thread.currentThread().getId();
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /*
     * Based on:
     * http://stackoverflow.com/questions/30703652/how-to-generate-light-and-pastel-colors-randomly-in-android
     */
    public static int generateRandomColor() {
        final Random random = new Random();

        final int red = (Color.red(Color.WHITE) + random.nextInt(256)) / 2;
        final int green = (Color.green(Color.WHITE) + random.nextInt(256)) / 2;
        final int blue = (Color.blue(Color.WHITE) + random.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }

}
