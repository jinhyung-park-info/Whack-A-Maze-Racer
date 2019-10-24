package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;
import android.graphics.Rect;

class SizeAndCoordinate {

    public static boolean contains(float x, float y, float f, float g, int p_x, int p_y) {
        if (p_x >= x && p_x <= f) {
            if (p_y >= y && p_y <= g) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Rect rect, int p_x, int p_y) {
        int x1, y1, x2, y2;
        x1 = rect.left;
        y1 = rect.top;
        x2 = rect.right;
        y2 = rect.bottom;
        return contains(x1, y1, x2, y2, p_x, p_y);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, float f, float g) {
        Bitmap n_bitmap = Bitmap.createScaledBitmap(bitmap, (int) f, (int) g, true);
        return n_bitmap;
    }
}
