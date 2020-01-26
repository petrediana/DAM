package com.diana.televizor.Clase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Graficcc extends View {
    private static final String TAG = "GraficccClass";
    private List<Integer> dateDiagonale;
    private List<String> infoExtra;
    private Paint paint;
    private Random random;


    public Graficcc(Context context, List<Integer> dateDiagonale, List<String> infoExtra) {
        super(context);

        this.dateDiagonale = dateDiagonale;
        this.infoExtra = infoExtra;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        this.random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (dateDiagonale == null || dateDiagonale.size() <= 0 || infoExtra == null || infoExtra.size() <= 0) {
            return;
        } else {
            int canvasHeight = getHeight() - 15;
            int canvasWidth = getWidth() - 15;

            int inaltimeBloculet = canvasHeight / Collections.max(dateDiagonale);
            int latimeBloc = canvasWidth / dateDiagonale.size();

            for (int i = 0; i < dateDiagonale.size(); ++i) {
                Log.i(TAG, dateDiagonale.get(i) + " " + infoExtra.get(i));

                int inaltimeBloc = inaltimeBloculet * dateDiagonale.get(i);
                int color = Color.argb(100, 1 + random.nextInt(254),
                        1 + random.nextInt(254), 1 + random.nextInt(254));

                paint.setColor(color);

                int left = i * latimeBloc;
                int top = canvasHeight - inaltimeBloc;
                int right = (i + i) * latimeBloc;
                int bottom = getMeasuredHeight();

                canvas.drawRect(left, top, right, bottom, paint);
                Log.i(TAG, "left: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);

                paint.setTextSize(25);
                paint.setColor(Color.BLACK);
                canvas.drawText(infoExtra.get(i) + " => " + dateDiagonale.get(i), left, top + 50, paint);
            }
        }
    }
}
