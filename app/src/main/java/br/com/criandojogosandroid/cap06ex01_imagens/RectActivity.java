package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class RectActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird;
        private int bWidth, bHeight;
        private Rect src = new Rect(), dst = new Rect();

        public Tela(Context context) {
            super(context);
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open("bird.png");
                bird = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bWidth = bird.getWidth();
            bHeight = bird.getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float xLeft = canvas.getWidth() / 4 - bWidth / 2;
            float xRight = (canvas.getWidth() / 4) * 3 - bWidth / 2;
            float yTop = canvas.getHeight() / 4 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 4) * 3 - bHeight / 2;
            canvas.drawBitmap(bird, xLeft, yTop, null);
            src.set(0,
                    0,
                    bird.getWidth() / 2,
                    bird.getHeight() / 2);
            dst.set(
                    (int) xRight,
                    (int) yTop,
                    (int) (xRight + bWidth),
                    (int) (yTop + bHeight)
            );
            canvas.drawBitmap(bird, src, dst, null);
            int parcialWidth = bird.getWidth() / 10;
            src.set(0, 0, parcialWidth, bird.getHeight());
            dst.set(
                    (int) xLeft,
                    (int) yBottom,
                    (int) (xLeft + parcialWidth),
                    (int) (yBottom + bHeight));
            for (int i = 0; i < 10; i++) {
                canvas.drawBitmap(bird, src, dst, null);
                src.offset(parcialWidth, 0);
                dst.offset((int) (parcialWidth * 2.5f), 0);
            }
        }
    }
}