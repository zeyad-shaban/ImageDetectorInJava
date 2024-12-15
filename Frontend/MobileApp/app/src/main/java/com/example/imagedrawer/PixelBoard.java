package com.example.imagedrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PixelBoard extends View {
    private int[][] pixels = new int[28][28]; // Initialize with 0s (black)

    public int[][] getPixels() {
        int[][] pixelsTrans = new int[28][28];

        int rows = pixels.length;
        int cols = pixels[0].length;

        pixelsTrans = new int[cols][];
        for (int i = 0; i < cols; i++)
            pixelsTrans[i] = new int[rows];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                pixelsTrans[j][i] = pixels[i][j];
        return pixelsTrans;
    }

    public void clearPixels() {
        for (int i = 0; i < pixels.length; ++i)
            for (int j = 0; j < pixels[0].length; ++j)
                pixels[i][j] = 0;

        invalidate();
    }

    private Paint paint = new Paint();

    public PixelBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                pixels[i][j] = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float pixelSize = getWidth() / 28f;

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                paint.setColor(pixels[i][j] == 1 ? Color.WHITE : Color.BLACK);
                canvas.drawRect(i * pixelSize, j * pixelSize, (i + 1) * pixelSize, (j + 1) * pixelSize, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            int x = (int) (event.getX() / (getWidth() / 28f));
            int y = (int) (event.getY() / (getHeight() / 28f));

            if (x >= 0 && x < 28 && y >= 0 && y < 28) {
                pixels[x][y] = 1;

                if (y > 0) pixels[x][y - 1] = 1;
                if (x < 27) pixels[x + 1][y] = 1;
                if (y < 27) pixels[x][y + 1] = 1;
                if (x > 0) pixels[x - 1][y] = 1;

                invalidate();
            }
        }
        return true;
    }
}