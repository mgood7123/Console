package com.example.broadcastConsole.grid;

import android.graphics.Paint;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class d {
    public static Paint adjustTextSize(Paint paint, String text10, int numCharacters, int widthPixels, int heightPixels) {
        int len = numCharacters;
        if (len == 0 || numCharacters == 0 || widthPixels == 0 || heightPixels == 0) return null;
        Log.e(TAG, "text10: \"" + text10 + "\"");
        float width = paint.measureText(text10)*numCharacters/len;
        Log.e(TAG, "width: " + String.valueOf(width));
        float newSize = (int)((widthPixels/width)*paint.getTextSize());
        Log.e(TAG, "newSize: " + String.valueOf(newSize));
        if (newSize == 0.0f) return null;
        paint.setTextSize(newSize);

        // remeasure with font size near our desired result
        width = paint.measureText(text10)*numCharacters/len;
        Log.e(TAG, "width: " + String.valueOf(width));
        newSize = (int)((widthPixels/width)*paint.getTextSize());
        Log.e(TAG, "newSize: " + String.valueOf(newSize));
        if (newSize == 0.0f) return null;
        paint.setTextSize(newSize);

        // Check height constraints
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        Log.e(TAG, "metrics.descent: " + String.valueOf(metrics.descent));
        Log.e(TAG, "metrics.ascent: " + String.valueOf(metrics.ascent));
        float textHeight = metrics.descent - metrics.ascent;
        Log.e(TAG, "textHeight: " + String.valueOf(textHeight));
        Log.e(TAG, "heightPixels: " + String.valueOf(heightPixels));
        if (textHeight > heightPixels) {
            newSize = (int)(newSize * (heightPixels/textHeight));
            Log.e(TAG, "newSize: " + String.valueOf(newSize));
            if (newSize == 0.0f) return null;
            paint.setTextSize(newSize);
        }
        return paint;
    }
}
