package com.example.projetandroid;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

public class BrightnessObserver extends ContentObserver {

    private Context context;
    private BrightnessChangeListener listener;

    public BrightnessObserver(Context context, BrightnessChangeListener listener) {
        super(new Handler());
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri.equals(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS))) {
            int brightness = getCurrentBrightness();
            listener.onBrightnessChanged(brightness);
        }
    }

    private int getCurrentBrightness() {
        int currentBrightness = 0;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return currentBrightness;
    }

    public interface BrightnessChangeListener {
        void onBrightnessChanged(int brightness);
    }
}

