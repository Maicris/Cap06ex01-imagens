package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class FormatsActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird, bird8888, bird4444, bird565, bird8;
        private int bWidth, bHeight;

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
            bird8888 = bird.copy(Bitmap.Config.ARGB_8888, false);
            bird4444 = bird.copy(Bitmap.Config.ARGB_4444, false);
            bird565 = bird.copy(Bitmap.Config.RGB_565, false);
            bird8 = bird.copy(Bitmap.Config.ALPHA_8, false);
            bWidth = bird.getWidth();
            bHeight = bird.getHeight();
            bird.recycle();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float xLeft = canvas.getWidth() / 4 - bWidth / 2;
            float xRight = (canvas.getWidth() / 4) * 3 - bWidth / 2;
            float yTop = canvas.getHeight() / 4 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 4) * 3 - bHeight / 2;
            canvas.drawBitmap(bird8888, xLeft, yTop, null);
            canvas.drawBitmap(bird4444, xRight, yTop, null);
            canvas.drawBitmap(bird565, xLeft, yBottom, null);
            canvas.drawBitmap(bird8, xRight, yBottom, null);
        }
    }
}