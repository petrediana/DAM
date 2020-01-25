package com.diana.bilet_monitor.Clase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Graficcc extends View {
    private HashMap<String, Integer> hashMap;
    private Paint paint;
    private List<String> diagonale;
    private List<Integer> valori = new ArrayList<>();
    private Random random;

    public Graficcc(Context context, HashMap<String, Integer> date) {
        super(context);
        this.hashMap = date;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.BLACK);
        this.diagonale = new ArrayList<>(this.hashMap.keySet());

        for (String diag : diagonale) {
            this.valori.add(this.hashMap.get(diag));
        }

        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hashMap != null && diagonale.size() > 0 && valori.size() > 0) {
            int canvasWidth = getWidth() - 15;
            int canvasHeight = getHeight() - 15;
            //int grosimeBloc = 25;

            int inaltimeBloc = canvasHeight / Collections.max(valori);
            int latimeBloc = canvasWidth / valori.size();

            for (int i = 0; i < valori.size(); ++i) {
                int bloc = inaltimeBloc * valori.get(i);
                int color = Color.argb(100, 1 + random.nextInt(254), 1 + random.nextInt(254)
                    ,1 + random.nextInt(254));
                paint.setColor(color);

                int left = i * latimeBloc;
                int top = canvasHeight - bloc;
                int right = (i + 1) * latimeBloc;
                int bottom = getMeasuredHeight();

                canvas.drawRect(left, top, right, bottom, paint);
                paint.setTextSize(25);
                paint.setColor(Color.BLACK);
                canvas.drawText(diagonale.get(i) + ": " + valori.get(i), left, top + 50, paint);
            }
        }
    }
}
