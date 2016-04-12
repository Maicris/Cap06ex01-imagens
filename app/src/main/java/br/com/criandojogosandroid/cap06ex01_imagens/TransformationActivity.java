package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class TransformationActivity extends AppCompatActivity {

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
            Matrix matrix = new Matrix();
            float xLeft = canvas.getWidth() / 4 - bWidth / 2;
            float xRight = (canvas.getWidth() / 4) * 3 - bWidth / 2;
            float yTop = canvas.getHeight() / 4 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 4) * 3 - bHeight / 2;
            matrix.preTranslate(xLeft, yTop);
            canvas.drawBitmap(bird, matrix, null);
            matrix.reset();
            matrix.preTranslate(xRight, yTop);
            matrix.preRotate(45, bWidth / 2, bHeight / 2);
            canvas.drawBitmap(bird, matrix, null);
            matrix.reset();
            matrix.preTranslate(xLeft, yBottom);
            matrix.preScale(-0.5f, 0.5f, bWidth / 2, bHeight / 2);
            canvas.drawBitmap(bird, matrix, null);
            matrix.reset();
            matrix.preTranslate(xRight, yBottom);
            matrix.preSkew(0.2f, 0.2f, bWidth / 2, bHeight / 2);
            canvas.drawBitmap(bird, matrix, null);
        }
    }
}
